package com.guidovezzoni.bingeworthyshows.common.di;

import com.guidovezzoni.bingeworthyshows.common.api.ApiHandler;
import com.guidovezzoni.bingeworthyshows.common.api.MovieDbServiceApi;
import com.guidovezzoni.bingeworthyshows.common.repofactory.CustomRepoFactory;
import com.guidovezzoni.bingeworthyshows.config.ConfigService;
import com.guidovezzoni.bingeworthyshows.tvshow.TvShowService;
import com.guidovezzoni.repofactory.RepoType;

/**
 * Simple dependency injection system. Could easily be replaced by Dagger if required.
 */
public class DiManager {

    private final ViewModelFactory viewModelFactory;

    public DiManager(String baseUrl, String apiKey) {
        ApiHandler<MovieDbServiceApi> apiHandler = new ApiHandler<>(MovieDbServiceApi.class, baseUrl);

        CustomRepoFactory repoFactory = new CustomRepoFactory();

        ConfigService configService = new ConfigService(
                repoFactory.createRepo(RepoType.SINGLE_LEVEL_CACHE, object -> apiHandler.getService().getConfiguration(apiKey)));

        TvShowService tvShowService = new TvShowService(
                repoFactory.createRepo(RepoType.NO_CACHE, integer -> apiHandler.getService().getTvPopular(apiKey, integer)));

        viewModelFactory = new ViewModelFactory(configService, tvShowService);
    }

    public ViewModelFactory getViewModelFactory() {
        return viewModelFactory;
    }
}
