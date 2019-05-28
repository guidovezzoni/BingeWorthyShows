package com.guidovezzoni.bingeworthyshows.common.baselibrary;

import com.guidovezzoni.bingeworthyshows.common.base.Perishable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Maybe;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaseRepositoryTest {
    private static final String NETWORK = "Network";
    private static final Perishable<String> NETWORK_PERISHABLE = new Perishable<>(NETWORK);

    @Mock
    private DataSource<String, Void> networkDataSource;

    private BaseRepository<String, Void> sut;

    @Before
    public void setUp() {
        sut = new BaseRepository<>(networkDataSource);
    }

    @Test
    public void whenGetThenReturnFromNetwork() {
        TestObserver<String> testObserver = TestObserver.create();
        when(networkDataSource.get(null)).thenReturn(Maybe.just(NETWORK_PERISHABLE));

        sut.get(null)
                .subscribe(testObserver);

        testObserver.assertResult(NETWORK);   // includes .assertComplete().assertNoErrors()
        verify(networkDataSource).get(null);
    }

    @Test
    public void whenGetLatestThenReturnFromNetwork() {
        TestObserver<String> testObserver = TestObserver.create();
        when(networkDataSource.get(null)).thenReturn(Maybe.just(NETWORK_PERISHABLE));

        sut.getLatest(null)
                .subscribe(testObserver);

        testObserver.assertResult(NETWORK);   // includes .assertComplete().assertNoErrors()
        verify(networkDataSource).get(null);
    }
}
