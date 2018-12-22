package com.guidovezzoni.bingeworthyshows.common.base;

import io.reactivex.Single;

public interface Service<M, P> {
    Single<M> get(P params);
}
