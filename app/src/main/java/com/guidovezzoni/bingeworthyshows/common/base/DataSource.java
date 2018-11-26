package com.guidovezzoni.bingeworthyshows.common.base;


import com.fernandocejas.arrow.optional.Optional;

import io.reactivex.Single;

// TODO Single<Optional<M>> should be replaced with a {@code Maybe}
public interface DataSource<M> {
    Single<Optional<M>> get();

    Single<Optional<M>> getAndUpdate(DataSource<M> cacheSource);

    void set(M model);
}
