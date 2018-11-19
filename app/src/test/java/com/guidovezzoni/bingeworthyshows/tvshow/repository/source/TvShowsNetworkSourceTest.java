package com.guidovezzoni.bingeworthyshows.tvshow.repository.source;

import com.guidovezzoni.bingeworthyshows.common.api.ApiHandler;
import com.guidovezzoni.bingeworthyshows.common.api.MovieDbServiceApi;
import com.guidovezzoni.bingeworthyshows.common.model.datalayer.ResultsReponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import retrofit2.Response;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TvShowsNetworkSourceTest {

    @Mock
    private MovieDbServiceApi movieDbServiceApi;
    @Mock
    private ApiHandler<MovieDbServiceApi> apiHandler;

    private TvShowsNetworkSource sut;

    /**
     * Although this method is not really needed in this case, the init code is separate as it'd be
     * used for any additional {@link #sut}'s method
     */
    @Before
    public void setUp() {
        when(apiHandler.getService()).thenReturn(movieDbServiceApi);
        sut = new TvShowsNetworkSource(apiHandler, "mock key");
    }


    @Test
    public void whenGetThenApiHandlerInvoked() {
        ResultsReponse resultsReponse = new ResultsReponse();

        TestObserver<ResultsReponse> testObserver = TestObserver.create();
        final Response<ResultsReponse> success = Response.success(resultsReponse);
        when(movieDbServiceApi.getTvPopular("mock key", 3))
                .thenReturn(Single.just(success));

        sut.get(3)
                .subscribe(testObserver);

        testObserver.assertComplete()
                .assertNoErrors();
        verify(movieDbServiceApi).getTvPopular("mock key", 3);
    }
}