package com.guidovezzoni.bingeworthyshows.tvshow;


import com.guidovezzoni.architecture.repository.Repository;
import com.guidovezzoni.bingeworthyshows.common.baselibrary.Service;
import com.guidovezzoni.bingeworthyshows.common.model.datalayer.ResultsReponse;
import com.guidovezzoni.bingeworthyshows.common.model.presentationlayer.TvShow;
import com.guidovezzoni.bingeworthyshows.common.utils.TvShowUtilsKt;

import java.util.List;

import io.reactivex.Single;

/**
 * This class forwards data to the app from the repository, providing any logic if required.
 */
public class TvShowService implements Service<List<TvShow>, Integer> {
    private final Repository<ResultsReponse, Integer> tvShowRepository;

    public TvShowService(Repository<ResultsReponse, Integer> tvShowRepository) {
        this.tvShowRepository = tvShowRepository;
    }

    public Single<List<TvShow>> get(Integer page) {
        return tvShowRepository.get(page)
                .map(ResultsReponse::getResults)
                .flattenAsObservable(results -> results)
                .map(TvShowUtilsKt::createFromResult)
                .toList();
    }
}
