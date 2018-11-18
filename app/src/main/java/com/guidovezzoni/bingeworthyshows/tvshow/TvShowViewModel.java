package com.guidovezzoni.bingeworthyshows.tvshow;

import com.guidovezzoni.bingeworthyshows.common.model.presentationlayer.TvShow;

import java.util.List;

import androidx.lifecycle.ViewModel;
import io.reactivex.Single;

public class TvShowViewModel extends ViewModel {
    private final TvShowService tvShowService;

    public TvShowViewModel(TvShowService tvShowService) {
        this.tvShowService = tvShowService;
    }

    public Single<List<TvShow>> get(int page) {
        return tvShowService.get(page);
    }
}
