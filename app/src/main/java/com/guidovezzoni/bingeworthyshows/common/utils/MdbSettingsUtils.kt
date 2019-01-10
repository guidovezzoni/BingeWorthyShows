package com.guidovezzoni.bingeworthyshows.common.utils

import com.guidovezzoni.bingeworthyshows.common.model.datalayer.ConfigurationResponse
import com.guidovezzoni.bingeworthyshows.common.model.presentationlayer.MdbSettings

fun createFromConfigurationResponse(configurationResponse: ConfigurationResponse): MdbSettings {
    return MdbSettings(configurationResponse.images.secureBaseUrl,
            configurationResponse.images.backdropSizes,
            configurationResponse.images.posterSizes)
}
