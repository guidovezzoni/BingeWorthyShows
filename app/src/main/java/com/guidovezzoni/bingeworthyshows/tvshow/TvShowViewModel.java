package com.guidovezzoni.bingeworthyshows.tvshow;

import com.guidovezzoni.bingeworthyshows.common.base.BaseViewModel;
import com.guidovezzoni.bingeworthyshows.common.model.presentationlayer.TvShow;
import com.guidovezzoni.bingeworthyshows.common.utils.TvShowUtilsKt;
import com.guidovezzoni.bingeworthyshows.config.ConfigService;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;

public class TvShowViewModel extends BaseViewModel {
    private final TvShowService tvShowService;
    private int paginationPage = 1;

    private final BehaviorSubject<Boolean> loadingSubject;

    public TvShowViewModel(ConfigService configService, TvShowService tvShowService) {
        super(configService);
        this.tvShowService = tvShowService;

        loadingSubject = BehaviorSubject.create();
    }

    public Single<List<TvShow>> get() {
        return tvShowService.get(paginationPage)
                .flattenAsObservable(tvShows -> tvShows)
                .flatMap(tvShow -> configService.get()
                        .map(movieDbSettings -> TvShowUtilsKt.createWithSettings(tvShow, movieDbSettings))
                        .toObservable())
                .toList()
                .doOnSubscribe(disposable -> loadingSubject.onNext(true))
                .doOnSuccess(tvShows -> loadingSubject.onNext(false))
                .doOnSuccess(tvShows -> paginationPage++);
    }

    @NonNull
    public Observable<Boolean> getLoadingIndicatorVisibility() {
        return loadingSubject.hide();
    }
}
