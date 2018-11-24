package com.guidovezzoni.bingeworthyshows.config.repository.source;

import com.fernandocejas.arrow.optional.Optional;
import com.guidovezzoni.bingeworthyshows.common.api.ApiHandler;
import com.guidovezzoni.bingeworthyshows.common.api.MovieDbServiceApi;
import com.guidovezzoni.bingeworthyshows.common.base.DataSource;
import com.guidovezzoni.bingeworthyshows.common.model.datalayer.ConfigurationResponse;
import com.guidovezzoni.bingeworthyshows.common.utils.RxUtils;

import io.reactivex.Single;

/**
 * This class fetches the info from the retrofit service and onSuccess returns the result
 * on stream - or returns an errors if that is the case
 */
public class ConfigNetworkSource implements DataSource<ConfigurationResponse> {
    private final MovieDbServiceApi movieDbServiceApi;
    private final String apiKey;

    public ConfigNetworkSource(ApiHandler<MovieDbServiceApi> apiHandler, String apiKey) {
        this.movieDbServiceApi = apiHandler.getService();
        this.apiKey = apiKey;
    }


    private Single<ConfigurationResponse> internalGet() {
        return movieDbServiceApi.getConfiguration(apiKey)
                .compose(RxUtils.unWrapResponseWithErrorOnStream());
    }

    @Override
    public Single<Optional<ConfigurationResponse>> get() {
        return internalGet()
                .map(Optional::of);
    }

    @Override
    public Single<Optional<ConfigurationResponse>> getAndUpdate(DataSource<ConfigurationResponse> cacheSource) {
        return internalGet()
                .doAfterSuccess(cacheSource::set)
                .map(Optional::of);
    }

    @Override
    public void set(ConfigurationResponse model) {
        // not required in this API
    }
}
