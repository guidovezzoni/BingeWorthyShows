package com.guidovezzoni.bingeworthyshows.common.baselibrary;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface ViewModel<M, P> {
    Single<M> get(P params);

    Observable<Boolean> getLoadingIndicatorVisibility();
}
