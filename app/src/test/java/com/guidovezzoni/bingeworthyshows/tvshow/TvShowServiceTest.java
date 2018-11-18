package com.guidovezzoni.bingeworthyshows.tvshow;

import com.guidovezzoni.bingeworthyshows.common.model.datalayer.PopularResult;
import com.guidovezzoni.bingeworthyshows.common.model.datalayer.Result;
import com.guidovezzoni.bingeworthyshows.common.model.presentationlayer.TvShow;
import com.guidovezzoni.bingeworthyshows.tvshow.repository.TvShowRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TvShowServiceTest {

    @Mock
    private TvShowRepository tvShowRepository;
    @InjectMocks
    private TvShowService sut;

    @Test
    public void whenGetThenRepositoryInvoked() {
        PopularResult popularResult = new PopularResult();

        Result result = new Result();
        result.setName("Agents of Shield");
        popularResult.getResults().add(result);

        result = new Result();
        result.setName("Gotham");
        popularResult.getResults().add(result);

        TestObserver<List<TvShow>> testObserver = TestObserver.create();
        when(tvShowRepository.get(4))
                .thenReturn(Single.just(popularResult));

        sut.get(4)
                .subscribe(testObserver);

        testObserver.assertComplete()
                .assertNoErrors()
                .assertValue(list -> list.size() == 2);
        verify(tvShowRepository).get(4);
    }

}