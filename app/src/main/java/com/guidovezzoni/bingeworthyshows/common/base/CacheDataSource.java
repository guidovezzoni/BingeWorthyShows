package com.guidovezzoni.bingeworthyshows.common.base;

public interface CacheDataSource<M> extends DataSource<M> {
    void invalidateCache();

    void setCacheValiditySeconds(Integer newCacheValiditySeconds);
}
