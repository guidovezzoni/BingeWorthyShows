package com.guidovezzoni.bingeworthyshows.tvshow;

import com.guidovezzoni.bingeworthyshows.common.base.BaseViewModel;
import com.guidovezzoni.bingeworthyshows.common.model.presentationlayer.MovieDbSettings;
import com.guidovezzoni.bingeworthyshows.common.model.presentationlayer.TvShow;
import com.guidovezzoni.bingeworthyshows.config.ConfigService;

import java.util.List;

import io.reactivex.Single;

public class TvShowViewModel extends BaseViewModel {
    private final TvShowService tvShowService;

    public TvShowViewModel(ConfigService configService, TvShowService tvShowService) {
        super(configService);
        this.tvShowService = tvShowService;
    }

    public Single<List<TvShow>> get(int page) {
        return tvShowService.get(page)
                .flattenAsObservable(tvShows -> tvShows)
                .flatMap(tvShow -> configService.get()
                        .map(movieDbSettings -> populatePoster(tvShow,movieDbSettings))
                        .toObservable())
                .toList();
    }

    private TvShow populatePoster(TvShow tvShow, MovieDbSettings movieDbSettings) {
        return tvShow;
    }
}
