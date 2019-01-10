package com.guidovezzoni.bingeworthyshows.tvshow;


import com.guidovezzoni.bingeworthyshows.common.extracttolibrary.Service;
import com.guidovezzoni.bingeworthyshows.common.model.datalayer.ResultsReponse;
import com.guidovezzoni.bingeworthyshows.common.model.presentationlayer.TvShow;
import com.guidovezzoni.bingeworthyshows.common.utils.TvShowUtilsKt;
import com.guidovezzoni.bingeworthyshows.tvshow.repository.TvShowRepository;

import java.util.List;

import io.reactivex.Single;

/**
 * This class forwards data to the app from the repository, providing any logic if required.
 */
public class TvShowService implements Service<List<TvShow>, Integer> {
    private final TvShowRepository tvShowRepository;

    public TvShowService(TvShowRepository tvShowRepository) {
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
