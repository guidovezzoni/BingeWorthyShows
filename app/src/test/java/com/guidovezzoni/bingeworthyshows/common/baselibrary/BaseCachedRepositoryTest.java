package com.guidovezzoni.bingeworthyshows.common.baselibrary;

import com.guidovezzoni.bingeworthyshows.common.base.Perishable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Maybe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaseCachedRepositoryTest {

    @Mock
    private DataSource<String, Void> networkDataSource;
    @Mock
    private CacheDataSource<String, Void> cacheDataSource;

    private BaseCachedRepository<String, Void> sut;

    @Before
    public void setUp() {
        sut = new BaseCachedRepository<>(networkDataSource, cacheDataSource);
    }

    @Test
    public void whenGetWithCacheAvailableThenReturnCache() {
        TestObserver<String> testObserver = TestObserver.create();
        when(cacheDataSource.get(null)).thenReturn(Maybe.just(Perishable.of("cache")));
        when(networkDataSource.getAndUpdate(null, cacheDataSource)).thenReturn(Maybe.just(Perishable.of("Network")));

        sut.get(null).subscribe(testObserver);

        testObserver.assertResult("cache");  // includes .assertComplete().assertNoErrors()
        verify(cacheDataSource).get(null);
        verify(networkDataSource).getAndUpdate(null, cacheDataSource);
    }

    @Test
    public void whenGetWithCacheNotAvailableThenReturnFromNetwork() {
        TestObserver<String> testObserver = TestObserver.create();
        when(cacheDataSource.get(null)).thenReturn(Maybe.empty());
        when(networkDataSource.getAndUpdate(null, cacheDataSource)).thenReturn(Maybe.just(Perishable.of("Network")));

        sut.get(null)
                .subscribe(testObserver);

        testObserver.assertResult("Network"); // includes .assertComplete().assertNoErrors()
        verify(cacheDataSource).get(null);
        verify(cacheDataSource, never()).set(any());
        verify(networkDataSource).getAndUpdate(null, cacheDataSource);
    }

    @Test
    public void whenGetWithNetworkFailureThenReturnHandleIt() {
        TestObserver<String> testObserver = TestObserver.create();
        when(cacheDataSource.get(null)).thenReturn(Maybe.empty());
        when(networkDataSource.getAndUpdate(null, cacheDataSource)).thenReturn(Maybe.error(new Exception("generic network error")));

        sut.get(null)
                .subscribe(testObserver);

        testObserver.assertNotComplete()
                .assertError(throwable -> throwable instanceof Exception)
                .assertNoValues();
    }

    @Test
    public void whenGetLatestThenReturnNetwork() {
        TestObserver<String> testObserver = TestObserver.create();
        when(networkDataSource.get(null)).thenReturn(Maybe.just(Perishable.of("Network")));

        sut.getLatest(null)
                .subscribe(testObserver);

        testObserver.assertResult("Network"); // includes .assertComplete().assertNoErrors()

        verify(networkDataSource).get(null);
        verify(cacheDataSource, never()).get(null);
        verify(cacheDataSource).set(any());
    }


    /**
     * It transform a block of code that can throw an exception to a Predicate that returns a boolean.
     * If the code throws an exception the test fails.
     * https://medium.com/@fabioCollini/testing-asynchronous-rxjava-code-using-mockito-8ad831a16877
     *
     * @param consumer
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> check(Consumer<T> consumer) {
        return t -> {
            consumer.accept(t);
            return true;
        };
    }
}