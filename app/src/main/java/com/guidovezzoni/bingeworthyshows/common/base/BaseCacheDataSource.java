package com.guidovezzoni.bingeworthyshows.common.base;

import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;

@SuppressWarnings("WeakerAccess")
public class BaseCacheDataSource<M, P> implements CacheDataSource<M, P> {
    private static final long DEFAULT_CACHE_VALIDITY = TimeUnit.MINUTES.toMillis(5);

    private M cachedValue;

    private final CacheHelper cacheHelper;
    private long cacheValidityMs;
    private long timeStampMs;

    public BaseCacheDataSource(CacheHelper cacheHelper) {
        this.cacheHelper = cacheHelper;
        cacheValidityMs = DEFAULT_CACHE_VALIDITY;
        invalidateCache();
    }

    @Override
    public Maybe<M> get(P params) {
        //TODO params is currently ignored
        return isCacheValid() ? Maybe.just(cachedValue) : Maybe.empty();
    }

    /**
     * @implNote {@code cacheSource.set(cachedValue);} is inaccurate as {@code cacheSource} won't
     * get the original data timestamp.
     * Should probably be replaced by a {@code Pair<M,timestamp>} or something similar
     */
    @Override
    public Maybe<M> getAndUpdate(P params, DataSource<M, P> cacheSource) {
        //TODO params is currently ignored
        if (isCacheValid()) {
            cacheSource.set(cachedValue);
            return Maybe.just(cachedValue);
        } else {
            return Maybe.empty();
        }
    }

    @Override
    public void set(M model) {
        updateCache(model);
    }

    /**
     * please note the parameter is in seconds while the internal class variables are in milliseconds
     *
     * @param newCacheValiditySeconds cache validity in seconds
     * @deprecated use {@link #setCacheValidity(Long)} instead
     */
    @Deprecated
    @Override
    public void setCacheValiditySeconds(Long newCacheValiditySeconds) {
        cacheValidityMs = (newCacheValiditySeconds != null) ?
                TimeUnit.SECONDS.toMillis(newCacheValiditySeconds) :
                DEFAULT_CACHE_VALIDITY;
    }

    @Override
    public void setCacheValidity(Long newCacheValidity) {
        cacheValidityMs = (newCacheValidity != null) ? newCacheValidity : DEFAULT_CACHE_VALIDITY;
    }

    protected void updateCache(M newValue) {
        cachedValue = newValue;
        timeStampMs = cacheHelper.getCurrentTimeStamp();
    }

    public void invalidateCache() {
        updateCache(null);
    }

    private boolean isCacheValid() {
        return cachedValue != null && cacheHelper.isTimeStampValid(timeStampMs, cacheValidityMs);
    }
}
