package com.guidovezzoni.bingeworthyshows.tvshow.repository.source;

import com.guidovezzoni.bingeworthyshows.common.api.ApiHandler;
import com.guidovezzoni.bingeworthyshows.common.api.MovieDbServiceApi;
import com.guidovezzoni.bingeworthyshows.common.model.datalayer.PopularResult;
import com.guidovezzoni.bingeworthyshows.common.utils.RxUtils;

import io.reactivex.Single;

/**
 * This class fetches the info from the retrofit service and onSuccess returns the result
 * on stream - or errors if it's the case
 */
public class TvShowsNetworkSource {
    private final MovieDbServiceApi movieDbServiceApi;
    private final String apyKey;

    public TvShowsNetworkSource(ApiHandler<MovieDbServiceApi> apiHandler, String apyKey) {
        this.movieDbServiceApi = apiHandler.getService();
        this.apyKey = apyKey;
    }

    public Single<PopularResult> get(int page) {
        return movieDbServiceApi.getTvPopular(apyKey, page)
                .compose(RxUtils.unWrapResponseWithErrorOnStream());
    }
}
