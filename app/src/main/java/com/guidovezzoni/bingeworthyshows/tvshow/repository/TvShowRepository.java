package com.guidovezzoni.bingeworthyshows.tvshow.repository;

import com.guidovezzoni.bingeworthyshows.common.model.datalayer.ResultsReponse;
import com.guidovezzoni.bingeworthyshows.tvshow.repository.source.TvShowsNetworkSource;

import io.reactivex.Single;

/**
 * Repository class is in charge to fetch the data from the network, can be extended to use a cache,
 * either in memory or persistent or both.
 */
public class TvShowRepository {
    private final TvShowsNetworkSource tvShowsNetworkSource;

    public TvShowRepository(TvShowsNetworkSource tvShowsNetworkSource) {
        this.tvShowsNetworkSource = tvShowsNetworkSource;
    }

    public Single<ResultsReponse> get(int page) {
        return tvShowsNetworkSource.get(page);
    }
}
