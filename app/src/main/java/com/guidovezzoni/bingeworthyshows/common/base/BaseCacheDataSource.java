package com.guidovezzoni.bingeworthyshows.common.base;

import com.fernandocejas.arrow.optional.Optional;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;

public class BaseCacheDataSource<M> implements CacheDataSource<M> {
    private static final long DEFAULT_CACHE_VALIDITY = TimeUnit.HOURS.toMillis(3);

    private M cachedValue;

    private final CacheHelper cacheHelper;
    private long cacheValidityMs;
    private long timeStampMs;

    public BaseCacheDataSource(CacheHelper cacheHelper) {
        this.cacheHelper = cacheHelper;
        cacheValidityMs = DEFAULT_CACHE_VALIDITY;
        invalidateCache();
    }

    public Single<Optional<M>> get() {
        return Single.just(isCacheValid() ? Optional.of(cachedValue) : Optional.absent());
    }

    /**
     * @implNote {@code cacheSource.set(cachedValue);} is inaccurate as {@code cacheSource} won't
     * get the original data timestamp.
     * Should probably be replaced by a {@code Pair<M,timestamp>} or something similar
     */
    @Override
    public Single<Optional<M>> getAndUpdate(DataSource<M> cacheSource) {
        if (isCacheValid()) {
            cacheSource.set(cachedValue);
            return Single.just(Optional.of(cachedValue));
        } else {
            return Single.just(Optional.absent());
        }
    }

    public void set(M model) {
        updateCache(model);
    }

    /**
     * please note the parameter is in seconds while the internal class variables are in milliseconds
     *
     * @param newCacheValiditySeconds cache validity in seconds
     */
    public void setCacheValiditySeconds(Integer newCacheValiditySeconds) {
        cacheValidityMs = (newCacheValiditySeconds != null) ?
                TimeUnit.SECONDS.toMillis(newCacheValiditySeconds) :
                DEFAULT_CACHE_VALIDITY;
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
