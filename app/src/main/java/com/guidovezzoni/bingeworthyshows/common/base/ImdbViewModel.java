package com.guidovezzoni.bingeworthyshows.common.base;

import com.guidovezzoni.bingeworthyshows.common.baselibrary.BaseViewModel;
import com.guidovezzoni.bingeworthyshows.common.baselibrary.Service;
import com.guidovezzoni.bingeworthyshows.config.ConfigService;

public class ImdbViewModel<M, P> extends BaseViewModel<M, P> {
    private final ConfigService configService;

    public ImdbViewModel(Service<M, P> service, ConfigService configService) {
        super(service);
        this.configService = configService;
    }

    public ConfigService getConfigService() {
        return configService;
    }
}
