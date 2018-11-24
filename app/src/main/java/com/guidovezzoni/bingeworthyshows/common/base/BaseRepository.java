package com.guidovezzoni.bingeworthyshows.common.base;


import com.fernandocejas.arrow.optional.Optional;
import com.guidovezzoni.bingeworthyshows.common.utils.RxUtils;

import io.reactivex.Single;

@SuppressWarnings("WeakerAccess")
public class BaseRepository<M> implements Repository<M> {
    protected final DataSource<M> networkDataSource;
    protected final CacheDataSource<M> cacheDataSource;

    public BaseRepository(DataSource<M> networkDataSource, CacheDataSource<M> cacheDataSource) {
        this.networkDataSource = networkDataSource;
        this.cacheDataSource = cacheDataSource;
    }

    @Override
    public Single<M> get() {
        return Single.concat(cacheDataSource.get(), networkDataSource.getAndUpdate(cacheDataSource))
                .filter(Optional::isPresent)
                .firstOrError()
                .compose(RxUtils.getOptionalWithErrorOnStream());
    }

    @Override
    public Single<M> getLatest() {
        return networkDataSource.get()
                .compose(RxUtils.getOptionalWithErrorOnStream())
                .doOnSuccess(cacheDataSource::set);
    }

    public void setCacheValiditySeconds(Integer newCacheValiditySeconds) {
        cacheDataSource.setCacheValiditySeconds(newCacheValiditySeconds);
    }

    public void invalidateCache() {
        cacheDataSource.invalidateCache();
    }
}
