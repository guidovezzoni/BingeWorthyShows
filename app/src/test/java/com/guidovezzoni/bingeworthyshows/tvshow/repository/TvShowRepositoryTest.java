package com.guidovezzoni.bingeworthyshows.tvshow.repository;

import com.guidovezzoni.bingeworthyshows.common.base.Perishable;
import com.guidovezzoni.bingeworthyshows.common.model.datalayer.ResultsReponse;
import com.guidovezzoni.bingeworthyshows.tvshow.repository.source.TvShowsNetworkSource;

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
public class TvShowRepositoryTest {

    @Mock
    private TvShowsNetworkSource tvShowsNetworkSource;

    private TvShowRepository sut;

    @Before
    public void setUp() {
        sut = new TvShowRepository(tvShowsNetworkSource);
    }

    @Test
    public void whenGetThenSourceInvoked() {
        Perishable<ResultsReponse> responsePerishable = new Perishable<>(new ResultsReponse());

        TestObserver<ResultsReponse> testObserver = TestObserver.create();
        when(tvShowsNetworkSource.get(2)).thenReturn(Maybe.just(responsePerishable));

        sut.get(2)
                .subscribe(testObserver);

        testObserver.assertComplete()
                .assertNoErrors();
        verify(tvShowsNetworkSource).get(2);
    }

}