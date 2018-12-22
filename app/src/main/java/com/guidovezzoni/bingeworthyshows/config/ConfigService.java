package com.guidovezzoni.bingeworthyshows.config;

import com.guidovezzoni.bingeworthyshows.common.base.Service;
import com.guidovezzoni.bingeworthyshows.common.model.presentationlayer.MdbSettings;
import com.guidovezzoni.bingeworthyshows.common.utils.MdbSettingsUtilsKt;
import com.guidovezzoni.bingeworthyshows.config.repository.ConfigRepository;

import io.reactivex.Single;

/**
 * This class forwards data to the app from the repository, providing any logic if required.
 */
public class ConfigService implements Service<MdbSettings, Void> {
    private final ConfigRepository configRepository;

    public ConfigService(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    public Single<MdbSettings> get(Void param) {
        return configRepository.get(null)
                .map(MdbSettingsUtilsKt::createFromConfigurationResponse);
    }
}
