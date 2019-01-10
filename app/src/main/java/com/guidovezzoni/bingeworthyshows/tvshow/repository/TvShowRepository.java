package com.guidovezzoni.bingeworthyshows.tvshow.repository;

import com.guidovezzoni.bingeworthyshows.common.extracttolibrary.BaseRepository;
import com.guidovezzoni.bingeworthyshows.common.model.datalayer.ResultsReponse;
import com.guidovezzoni.bingeworthyshows.tvshow.repository.source.TvShowsNetworkSource;

/**
 * Repository class is in charge to fetch the data from the network, can be extended to use a cache,
 * either in memory or persistent or both.
 */
public class TvShowRepository extends BaseRepository<ResultsReponse, Integer> {

    public TvShowRepository(TvShowsNetworkSource tvShowsNetworkSource) {
        super(tvShowsNetworkSource);
    }
}
