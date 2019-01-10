package com.guidovezzoni.bingeworthyshows.tvshow.repository.source;

import com.guidovezzoni.bingeworthyshows.common.api.ApiHandler;
import com.guidovezzoni.bingeworthyshows.common.api.MovieDbServiceApi;
import com.guidovezzoni.bingeworthyshows.common.base.Perishable;
import com.guidovezzoni.bingeworthyshows.common.extracttolibrary.BaseNetworkDataSource;
import com.guidovezzoni.bingeworthyshows.common.model.datalayer.ResultsReponse;

import io.reactivex.Single;
import retrofit2.Response;

/**
 * This class fetches the info from the retrofit service and onSuccess returns the result
 * on stream - or returns an errors if that is the case
 */
public class TvShowsNetworkSource extends BaseNetworkDataSource<ResultsReponse, Integer> {
    private final MovieDbServiceApi movieDbServiceApi;
    private final String apiKey;

    public TvShowsNetworkSource(ApiHandler<MovieDbServiceApi> apiHandler, String apiKey) {
        this.movieDbServiceApi = apiHandler.getService();
        this.apiKey = apiKey;
    }

    @Override
    protected Single<Response<ResultsReponse>> getFromEndPoint(Integer page) {
        return movieDbServiceApi.getTvPopular(apiKey, page);
    }

    @Override
    public void set(Perishable<ResultsReponse> model) {
        // not required in this API
    }
}
