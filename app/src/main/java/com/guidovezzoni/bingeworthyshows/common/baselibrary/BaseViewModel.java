package com.guidovezzoni.bingeworthyshows.common.baselibrary;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;

public class BaseViewModel<M, P> extends ViewModel implements com.guidovezzoni.bingeworthyshows.common.baselibrary.ViewModel<M, P> {
    protected final Service<M, P> service;

    private final BehaviorSubject<Boolean> loadingSubject;

    public BaseViewModel(Service<M, P> service) {
        this.service = service;

        loadingSubject = BehaviorSubject.create();
    }

    @Override
    public Single<M> get(P params) {
        return service.get(params)
                .doOnSubscribe(disposable -> loadingSubject.onNext(true))
                .doOnSuccess(tvShows -> loadingSubject.onNext(false));
    }

    @NonNull
    public Observable<Boolean> getLoadingIndicatorVisibility() {
        return loadingSubject.hide();
    }
}
