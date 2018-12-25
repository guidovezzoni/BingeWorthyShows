package com.guidovezzoni.bingeworthyshows.common.baselibrary;

public interface CacheDataSource<M, P> extends DataSource<M, P> {
    void invalidateCache();

    @Deprecated
    void setCacheValiditySeconds(Long newCacheValiditySeconds);

    void setCacheValidity(Long newCacheValidity);
}
