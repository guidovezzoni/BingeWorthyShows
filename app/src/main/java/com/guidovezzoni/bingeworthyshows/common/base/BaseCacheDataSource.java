package com.guidovezzoni.bingeworthyshows.common.base;

import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;

@SuppressWarnings("WeakerAccess")
public class BaseCacheDataSource<M, P> implements CacheDataSource<M, P> {
    private static final long DEFAULT_CACHE_VALIDITY = TimeUnit.MINUTES.toMillis(5);

    private Perishable<M> cachedValue;

    private final CacheHelper cacheHelper;
    private long cacheValidityMs;

    public BaseCacheDataSource(CacheHelper cacheHelper) {
        this.cacheHelper = cacheHelper;
        cacheValidityMs = DEFAULT_CACHE_VALIDITY;
        invalidateCache();
    }

    @Override
    public Maybe<Perishable<M>> get(P params) {
        //TODO params is currently ignored
        return isCacheValid() ? Maybe.just(cachedValue) : Maybe.empty();
    }

    /**
     * @implNote {@code cacheSource.set(cachedValue);} is inaccurate as {@code cacheSource} won't
     * get the original data timestamp.
     * Should probably be replaced by a {@code Pair<M,timestamp>} or something similar
     */
    @Override
    public Maybe<Perishable<M>> getAndUpdate(P params, DataSource<M, P> cacheSource) {
        //TODO params is currently ignored
        if (isCacheValid()) {
            cacheSource.set(cachedValue);
            return Maybe.just(cachedValue);
        } else {
            return Maybe.empty();
        }
    }

    @Override
    public void set(Perishable<M> model) {
        updateCache(model);
    }

    /**
     * please note the parameter is in seconds while the internal class variables are in milliseconds
     *
     * @param newCacheValiditySeconds cache validity in seconds
     */
    @Override
    public void setCacheValiditySeconds(Integer newCacheValiditySeconds) {
        cacheValidityMs = (newCacheValiditySeconds != null) ?
                TimeUnit.SECONDS.toMillis(newCacheValiditySeconds) :
                DEFAULT_CACHE_VALIDITY;
    }

    protected void updateCache(Perishable<M> newValue) {
        cachedValue = newValue;
    }

    public void invalidateCache() {
        updateCache(null);
    }

    private boolean isCacheValid() {
        return cachedValue != null && cacheHelper.isTimeStampValid(cachedValue.getTimestamp(), cacheValidityMs);
    }
}
