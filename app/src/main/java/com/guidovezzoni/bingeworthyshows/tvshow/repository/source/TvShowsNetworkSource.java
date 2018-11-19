package com.guidovezzoni.bingeworthyshows.tvshow.repository.source;

import com.guidovezzoni.bingeworthyshows.common.api.ApiHandler;
import com.guidovezzoni.bingeworthyshows.common.api.MovieDbServiceApi;
import com.guidovezzoni.bingeworthyshows.common.model.datalayer.ResultsReponse;
import com.guidovezzoni.bingeworthyshows.common.utils.RxUtils;

import io.reactivex.Single;

/**
 * This class fetches the info from the retrofit service and onSuccess returns the result
 * on stream - or returns an errors if that is the case
 */
public class TvShowsNetworkSource {
    private final MovieDbServiceApi movieDbServiceApi;
    private final String apiKey;

    public TvShowsNetworkSource(ApiHandler<MovieDbServiceApi> apiHandler, String apiKey) {
        this.movieDbServiceApi = apiHandler.getService();
        this.apiKey = apiKey;
    }

    public Single<ResultsReponse> get(int page) {
        return movieDbServiceApi.getTvPopular(apiKey, page)
                .compose(RxUtils.unWrapResponseWithErrorOnStream());
    }
}
