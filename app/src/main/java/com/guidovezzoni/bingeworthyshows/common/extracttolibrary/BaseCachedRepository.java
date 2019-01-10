package com.guidovezzoni.bingeworthyshows.common.extracttolibrary;


import com.guidovezzoni.bingeworthyshows.common.base.Perishable;

import io.reactivex.Single;

@SuppressWarnings({"WeakerAccess", "unused"})
public class BaseCachedRepository<M, P> implements Repository<M, P> {
    protected final DataSource<M, P> networkDataSource;
    protected final CacheDataSource<M, P> cacheDataSource;

    public BaseCachedRepository(DataSource<M, P> networkDataSource, CacheDataSource<M, P> cacheDataSource) {
        this.networkDataSource = networkDataSource;
        this.cacheDataSource = cacheDataSource;
    }

    @Override
    public Single<M> get(P params) {
        return cacheDataSource.get(params)
                .switchIfEmpty(networkDataSource.getAndUpdate(params, cacheDataSource))
                .map(Perishable::getModel)
                .toSingle();
    }

    @Override
    public Single<M> getLatest(P params) {
        return networkDataSource.get(params)
                .doOnSuccess(cacheDataSource::set)
                .map(Perishable::getModel)
                .toSingle();
    }

    public void setCacheValiditySeconds(Long newCacheValiditySeconds) {
        cacheDataSource.setCacheValiditySeconds(newCacheValiditySeconds);
    }

    public void invalidateCache() {
        cacheDataSource.invalidateCache();
    }
}
