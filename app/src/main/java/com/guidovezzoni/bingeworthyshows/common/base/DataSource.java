package com.guidovezzoni.bingeworthyshows.common.base;


import io.reactivex.Maybe;

public interface DataSource<M, P> {
    Maybe<Perishable<M>> get(P params);

    Maybe<Perishable<M>> getAndUpdate(P params, DataSource<M, P> cacheSource);

    void set(Perishable<M> model);
}
