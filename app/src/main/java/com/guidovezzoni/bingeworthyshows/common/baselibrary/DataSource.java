package com.guidovezzoni.bingeworthyshows.common.baselibrary;


import io.reactivex.Maybe;

public interface DataSource<M, P> {
    Maybe<M> get(P params);

    Maybe<M> getAndUpdate(P params, DataSource<M, P> cacheSource);

    void set(M model);
}
