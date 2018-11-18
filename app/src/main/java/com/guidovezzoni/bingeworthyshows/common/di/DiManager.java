package com.guidovezzoni.bingeworthyshows.common.di;

import com.guidovezzoni.bingeworthyshows.common.api.ApiHandler;
import com.guidovezzoni.bingeworthyshows.common.api.MovieDbServiceApi;
import com.guidovezzoni.bingeworthyshows.tvshow.TvShowService;
import com.guidovezzoni.bingeworthyshows.tvshow.repository.TvShowRepository;
import com.guidovezzoni.bingeworthyshows.tvshow.repository.source.TvShowsNetworkSource;

/**
 * Simple dependency injection system. Could easily be replaced by Dagger if required.
 */
@SuppressWarnings("FieldCanBeLocal")
public class DiManager {
    private final ApiHandler<MovieDbServiceApi> apiHandler;
    private final TvShowsNetworkSource tvShowsNetworkSource;
    private final TvShowRepository tvShowRepository;
    private final TvShowService tvShowService;
    private final ViewModelFactory viewModelFactory;

    public DiManager(String baseUrl, String apyKey) {
        apiHandler = new ApiHandler<>(MovieDbServiceApi.class, baseUrl);
        tvShowsNetworkSource = new TvShowsNetworkSource(apiHandler, apyKey);
        tvShowRepository = new TvShowRepository(tvShowsNetworkSource);
        tvShowService = new TvShowService(tvShowRepository);
        viewModelFactory = new ViewModelFactory(tvShowService);
    }

    public ViewModelFactory getViewModelFactory() {
        return viewModelFactory;
    }
}
