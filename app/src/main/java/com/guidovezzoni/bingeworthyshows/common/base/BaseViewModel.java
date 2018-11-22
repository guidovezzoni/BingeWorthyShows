package com.guidovezzoni.bingeworthyshows.common.base;

import com.guidovezzoni.bingeworthyshows.config.ConfigService;

import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {
    protected final ConfigService configService;

    public BaseViewModel(ConfigService configService) {
        this.configService = configService;
    }
}
