package com.guidovezzoni.bingeworthyshows.common.extracttolibrary;

import com.guidovezzoni.bingeworthyshows.common.base.Perishable;

import io.reactivex.Single;

@SuppressWarnings("WeakerAccess")
public class BaseRepository<M, P> implements Repository<M, P> {
    protected final DataSource<M, P> networkDataSource;

    public BaseRepository(DataSource<M, P> networkDataSource) {
        this.networkDataSource = networkDataSource;
    }

    @Override
    public Single<M> get(P params) {
        return getLatest(params);
    }

    @Override
    public Single<M> getLatest(P params) {
        return networkDataSource.get(params)
                .map(Perishable::getModel)
                .toSingle();
    }
}
