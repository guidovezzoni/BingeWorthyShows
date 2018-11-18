package com.guidovezzoni.bingeworthyshows.common.factory;

import com.guidovezzoni.bingeworthyshows.common.model.datalayer.Result;
import com.guidovezzoni.bingeworthyshows.common.model.presentationlayer.TvShow;

public final class TvShowFactory {
    private TvShowFactory() {
    }

    public static TvShow createTvShow(Result result) {
        return new TvShow(result.getName(),
                result.getOverview(),
                result.getPosterPath(), // TODO
                result.getVoteAverage());
    }
}
