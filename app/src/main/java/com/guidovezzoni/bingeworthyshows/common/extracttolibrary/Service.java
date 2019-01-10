package com.guidovezzoni.bingeworthyshows.common.extracttolibrary;

import io.reactivex.Single;

public interface Service<M, P> {
    Single<M> get(P params);

}
