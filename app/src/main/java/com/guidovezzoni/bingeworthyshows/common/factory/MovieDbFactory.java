package com.guidovezzoni.bingeworthyshows.common.factory;

import com.guidovezzoni.bingeworthyshows.common.model.datalayer.ConfigurationResponse;
import com.guidovezzoni.bingeworthyshows.common.model.presentationlayer.MovieDbSettings;

public final class MovieDbFactory {

    private MovieDbFactory() {
    }

    public static MovieDbSettings createMovieDbFactory(ConfigurationResponse configurationResponse){
        return new MovieDbSettings(configurationResponse.getImages().getBaseUrl());
    }
}
