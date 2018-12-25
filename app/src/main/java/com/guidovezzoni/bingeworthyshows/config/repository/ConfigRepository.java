package com.guidovezzoni.bingeworthyshows.config.repository;

import com.guidovezzoni.bingeworthyshows.common.baselibrary.BaseCachedRepository;
import com.guidovezzoni.bingeworthyshows.common.model.datalayer.ConfigurationResponse;
import com.guidovezzoni.bingeworthyshows.config.repository.source.ConfigCacheSource;
import com.guidovezzoni.bingeworthyshows.config.repository.source.ConfigNetworkSource;

/**
 * Repository class is in charge to fetch the data from the network, can be extended to use a cache,
 * either in memory or persistent or both.
 */
public class ConfigRepository extends BaseCachedRepository<ConfigurationResponse, Void> {
    public ConfigRepository(ConfigNetworkSource configNetworkSource, ConfigCacheSource configCacheSource) {
        super(configNetworkSource, configCacheSource);
    }
}
