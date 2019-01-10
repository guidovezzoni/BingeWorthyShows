package com.guidovezzoni.bingeworthyshows.common.extracttolibrary;

/**
 * The main reason for this class is to extract some internal logic and unit test it separately.
 * Also {@link System#currentTimeMillis()} is static so it needs to be wrapped to be mocked.
 */
@SuppressWarnings("WeakerAccess")
public class CacheHelper {

    public long getCurrentTimeStamp() {
        return System.currentTimeMillis();
    }

    public boolean isTimeStampValid(long timestamp, long cacheValidity) {
        return isTimeStampValid(getCurrentTimeStamp(), timestamp, cacheValidity);
    }

    public boolean isTimeStampValid(long currentTimeStamp, long timeStamp, long cacheValidity) {
        return currentTimeStamp - timeStamp <= cacheValidity;
    }
}
