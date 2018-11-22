package com.guidovezzoni.bingeworthyshows.common.di;

import com.guidovezzoni.bingeworthyshows.config.ConfigService;
import com.guidovezzoni.bingeworthyshows.tvshow.TvShowService;
import com.guidovezzoni.bingeworthyshows.tvshow.TvShowViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private final ConfigService configService;
    private final TvShowService tvShowService;

    @SuppressWarnings("WeakerAccess")
    public ViewModelFactory(ConfigService configService, TvShowService tvShowService) {
        this.configService = configService;
        this.tvShowService = tvShowService;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TvShowViewModel.class)) {
            return (T) new TvShowViewModel(configService, tvShowService);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
