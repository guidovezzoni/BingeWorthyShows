package com.guidovezzoni.bingeworthyshows.config.repository.source;

import com.guidovezzoni.bingeworthyshows.common.api.ApiHandler;
import com.guidovezzoni.bingeworthyshows.common.api.MovieDbServiceApi;
import com.guidovezzoni.bingeworthyshows.common.base.Perishable;
import com.guidovezzoni.bingeworthyshows.common.extracttolibrary.BaseNetworkDataSource;
import com.guidovezzoni.bingeworthyshows.common.model.datalayer.ConfigurationResponse;

import io.reactivex.Single;
import retrofit2.Response;

/**
 * This class fetches the info from the retrofit service and onSuccess returns the result
 * on stream - or returns an errors if that is the case
 */
public class ConfigNetworkSource extends BaseNetworkDataSource<ConfigurationResponse, Void> {
    private final MovieDbServiceApi movieDbServiceApi;
    private final String apiKey;

    public ConfigNetworkSource(ApiHandler<MovieDbServiceApi> apiHandler, String apiKey) {
        this.movieDbServiceApi = apiHandler.getService();
        this.apiKey = apiKey;
    }

    @Override
    protected Single<Response<ConfigurationResponse>> getFromEndPoint(Void params) {
        return movieDbServiceApi.getConfiguration(apiKey);
    }

    @Override
    public void set(Perishable<ConfigurationResponse> model) {
        // not required in this API
    }
}
