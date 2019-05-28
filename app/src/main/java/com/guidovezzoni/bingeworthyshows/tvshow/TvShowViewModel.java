package com.guidovezzoni.bingeworthyshows.tvshow;

import com.guidovezzoni.bingeworthyshows.common.base.ImdbViewModel;
import com.guidovezzoni.bingeworthyshows.common.model.presentationlayer.TvShow;
import com.guidovezzoni.bingeworthyshows.common.utils.TvShowUtilsKt;
import com.guidovezzoni.bingeworthyshows.config.ConfigService;

import java.util.List;

import io.reactivex.Single;

public class TvShowViewModel extends ImdbViewModel<List<TvShow>, Integer> {
    private int paginationPage;

    public TvShowViewModel(TvShowService tvShowService, ConfigService configService) {
        super(tvShowService, configService);
        resetPagination();
    }

    @Override
    public Single<List<TvShow>> get(Integer params) {
        return Single.error(new Exception("TvShowViewModel.get(Integer params) cannot be called"));
    }

    public void resetPagination() {
        paginationPage = 1;
    }

    // TODO change of config will trigger a new repository request, should it let through and cached at repo level or cached here?
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
