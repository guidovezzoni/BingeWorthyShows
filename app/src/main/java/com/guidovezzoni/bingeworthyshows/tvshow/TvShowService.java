package com.guidovezzoni.bingeworthyshows.tvshow;

import com.guidovezzoni.bingeworthyshows.common.factory.TvShowFactory;
import com.guidovezzoni.bingeworthyshows.common.model.datalayer.PopularResult;
import com.guidovezzoni.bingeworthyshows.common.model.presentationlayer.TvShow;
import com.guidovezzoni.bingeworthyshows.tvshow.repository.TvShowRepository;

import java.util.List;

import io.reactivex.Single;

public class TvShowService {
    private final TvShowRepository tvShowRepository;

    public TvShowService(TvShowRepository tvShowRepository) {
        this.tvShowRepository = tvShowRepository;
    }

    public Single<List<TvShow>> get(int page) {
        return tvShowRepository.get(page)
                .map(PopularResult::getResults)
                .flattenAsObservable(results -> results)
                .map(TvShowFactory::createTvShow)
                .toList();
    }
}
