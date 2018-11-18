package com.guidovezzoni.bingeworthyshows.common.api;

@SuppressWarnings("WeakerAccess")
public class Api {
    private Api() {
    }

    // base URL
    public static final String BASE_URL = "https://developers.themoviedb.org";

    // API version
    private static final String API_VERSION = "/3";

    // TV endpoint
    private static final String TV_ROOT = "/tv";

    public static final String TV_POPULAR = API_VERSION + TV_ROOT + "popular";


    // query params
    public static final String QUERY_PARAM_API_KEY = "apy_key";
    public static final String QUERY_PARAM_PAGE = "page";

}
