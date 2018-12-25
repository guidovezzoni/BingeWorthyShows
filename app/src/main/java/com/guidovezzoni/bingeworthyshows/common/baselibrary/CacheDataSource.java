package com.guidovezzoni.bingeworthyshows.common.baselibrary;

public interface CacheDataSource<M, P> extends DataSource<M, P> {
    void invalidateCache();

    void setCacheValiditySeconds(Integer newCacheValiditySeconds);
}
