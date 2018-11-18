package com.guidovezzoni.bingeworthyshows;

import android.app.Application;

import com.guidovezzoni.bingeworthyshows.common.di.DiManager;

import static com.guidovezzoni.bingeworthyshows.common.api.Api.BASE_URL;

public class MainApplication extends Application {
    private DiManager diManager;

    @Override
    public void onCreate() {
        super.onCreate();
        diManager = new DiManager(BASE_URL, getString(R.string.movie_db_api_key));
    }

    public DiManager getDiManager() {
        return diManager;
    }
}
