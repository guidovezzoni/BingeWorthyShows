package com.guidovezzoni.bingeworthyshows.common.baselibrary;

import io.reactivex.Single;

public interface Service<M, P> {
    Single<M> get(P params);

}
