package com.guidovezzoni.bingeworthyshows.config;

import com.guidovezzoni.architecture.repository.Repository;
import com.guidovezzoni.bingeworthyshows.common.baselibrary.Service;
import com.guidovezzoni.bingeworthyshows.common.model.datalayer.ConfigurationResponse;
import com.guidovezzoni.bingeworthyshows.common.model.presentationlayer.MdbSettings;
import com.guidovezzoni.bingeworthyshows.common.utils.MdbSettingsUtilsKt;

import io.reactivex.Single;

/**
 * This class forwards data to the app from the repository, providing any logic if required.
 */
public class ConfigService implements Service<MdbSettings, Void> {
    private final Repository<ConfigurationResponse, Void> configRepository;

    public ConfigService(Repository<ConfigurationResponse, Void> configRepository) {
        this.configRepository = configRepository;
    }

    public Single<MdbSettings> get(Void param) {
        return configRepository.get()
                .map(MdbSettingsUtilsKt::createFromConfigurationResponse);
    }
}
