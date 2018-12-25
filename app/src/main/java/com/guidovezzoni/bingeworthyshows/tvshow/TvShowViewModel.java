package com.guidovezzoni.bingeworthyshows.tvshow;

import com.guidovezzoni.bingeworthyshows.common.base.ImdbViewModel;
import com.guidovezzoni.bingeworthyshows.common.model.presentationlayer.TvShow;
import com.guidovezzoni.bingeworthyshows.common.utils.TvShowUtilsKt;
import com.guidovezzoni.bingeworthyshows.config.ConfigService;

import java.util.List;

import io.reactivex.Single;

public class TvShowViewModel extends ImdbViewModel<List<TvShow>, Integer> {
    private int paginationPage = 1;

    public TvShowViewModel(TvShowService tvShowService, ConfigService configService) {
        super(tvShowService, configService);
    }

    public Single<List<TvShow>> get() {
        return super.get(paginationPage)
                .flattenAsObservable(tvShows -> tvShows)
                .flatMap(tvShow -> getConfigService().get(null)
                        .map(movieDbSettings -> TvShowUtilsKt.createWithSettings(tvShow, movieDbSettings))
                        .toObservable())
                .toList()

                // TODO this will go over the 1000 allowed pages
                .doOnSuccess(tvShows -> paginationPage++);
    }
}
