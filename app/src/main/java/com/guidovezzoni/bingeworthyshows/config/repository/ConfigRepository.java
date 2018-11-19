package com.guidovezzoni.bingeworthyshows.config.repository;

import com.guidovezzoni.bingeworthyshows.common.model.datalayer.ConfigurationResponse;
import com.guidovezzoni.bingeworthyshows.config.repository.source.ConfigNetworkSource;

import io.reactivex.Single;

/**
 * Repository class is in charge to fetch the data from the network, can be extended to use a cache,
 * either in memory or persistent or both.
 */
public class ConfigRepository {
    private final ConfigNetworkSource configNetworkSource;

    public ConfigRepository(ConfigNetworkSource configNetworkSource) {
        this.configNetworkSource = configNetworkSource;
    }

    public Single<ConfigurationResponse> get() {
        return configNetworkSource.get();
    }
}
