package com.guidovezzoni.bingeworthyshows.common.base;

import io.reactivex.Single;

public interface Repository<M> {
    Single<M> get();

    Single<M> getLatest();
}
