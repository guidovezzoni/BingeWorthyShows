package com.guidovezzoni.bingeworthyshows.common.utils

import com.guidovezzoni.bingeworthyshows.common.model.datalayer.ConfigurationResponse
import com.guidovezzoni.bingeworthyshows.common.model.presentationlayer.MovieDbSettings

fun createFromConfigurationResponse(configurationResponse: ConfigurationResponse): MovieDbSettings {
    return MovieDbSettings(configurationResponse.images.baseUrl,
            configurationResponse.images.posterSizes)
}
