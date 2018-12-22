package com.guidovezzoni.bingeworthyshows.common.base;

public interface CacheDataSource<M, P> extends DataSource<M, P> {
    void invalidateCache();

    void setCacheValiditySeconds(Integer newCacheValiditySeconds);
}
