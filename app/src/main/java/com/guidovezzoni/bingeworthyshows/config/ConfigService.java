package com.guidovezzoni.bingeworthyshows.config;

import com.guidovezzoni.bingeworthyshows.common.model.presentationlayer.MovieDbSettings;
import com.guidovezzoni.bingeworthyshows.common.utils.MdbSettingsUtilsKt;
import com.guidovezzoni.bingeworthyshows.config.repository.ConfigRepository;

import io.reactivex.Single;

/**
 * This class forwards data to the app from the repository, providing any logic if required.
 */
public class ConfigService {
    private final ConfigRepository configRepository;

    public ConfigService(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    public Single<MovieDbSettings> get() {
        return configRepository.get()
                .map(MdbSettingsUtilsKt::createFromConfigurationResponse);
    }
}
