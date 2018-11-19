package com.guidovezzoni.bingeworthyshows.common.api;

import com.guidovezzoni.bingeworthyshows.common.model.datalayer.ConfigurationResponse;
import com.guidovezzoni.bingeworthyshows.common.model.datalayer.ResultsReponse;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieDbServiceApi {

    // https://api.themoviedb.org/3/configuration?api_key=ab945c2099fd04f5da184470449e5979
    @GET(Api.CONFIGURATION)
    Single<Response<ConfigurationResponse>> getConfiguration(@Query(Api.QUERY_PARAM_API_KEY) String apiKey);


    // https://api.themoviedb.org/3/tv/popular?api_key=ab945c2099fd04f5da184470449e5979&page=1
    @GET(Api.TV_POPULAR)
    Single<Response<ResultsReponse>> getTvPopular(@Query(Api.QUERY_PARAM_API_KEY) String apiKey,
                                                  @Query(Api.QUERY_PARAM_PAGE) int page);


}
