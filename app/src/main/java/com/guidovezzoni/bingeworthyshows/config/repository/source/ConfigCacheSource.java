package com.guidovezzoni.bingeworthyshows.config.repository.source;

import com.guidovezzoni.bingeworthyshows.common.base.BaseCacheDataSource;
import com.guidovezzoni.bingeworthyshows.common.base.CacheHelper;
import com.guidovezzoni.bingeworthyshows.common.model.datalayer.ConfigurationResponse;

public class ConfigCacheSource extends BaseCacheDataSource<ConfigurationResponse> {

    public ConfigCacheSource(CacheHelper cacheHelper) {
        super(cacheHelper);
    }
}
