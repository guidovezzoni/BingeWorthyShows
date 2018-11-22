package com.guidovezzoni.bingeworthyshows.common.di;

import com.guidovezzoni.bingeworthyshows.common.api.ApiHandler;
import com.guidovezzoni.bingeworthyshows.common.api.MovieDbServiceApi;
import com.guidovezzoni.bingeworthyshows.config.ConfigService;
import com.guidovezzoni.bingeworthyshows.config.repository.ConfigRepository;
import com.guidovezzoni.bingeworthyshows.config.repository.source.ConfigNetworkSource;
import com.guidovezzoni.bingeworthyshows.tvshow.TvShowService;
import com.guidovezzoni.bingeworthyshows.tvshow.repository.TvShowRepository;
import com.guidovezzoni.bingeworthyshows.tvshow.repository.source.TvShowsNetworkSource;

/**
 * Simple dependency injection system. Could easily be replaced by Dagger if required.
 */
public class DiManager {

    private final ViewModelFactory viewModelFactory;

    public DiManager(String baseUrl, String apiKey) {
        ApiHandler<MovieDbServiceApi> apiHandler = new ApiHandler<>(MovieDbServiceApi.class, baseUrl);

        ConfigNetworkSource configNetworkSource = new ConfigNetworkSource(apiHandler, apiKey);
        ConfigRepository configRepository = new ConfigRepository(configNetworkSource);
        ConfigService configService = new ConfigService(configRepository);

        TvShowsNetworkSource tvShowsNetworkSource = new TvShowsNetworkSource(apiHandler, apiKey);
        TvShowRepository tvShowRepository = new TvShowRepository(tvShowsNetworkSource);
        TvShowService tvShowService = new TvShowService(tvShowRepository);

        viewModelFactory = new ViewModelFactory(configService, tvShowService);
    }

    public ViewModelFactory getViewModelFactory() {
        return viewModelFactory;
    }
}
