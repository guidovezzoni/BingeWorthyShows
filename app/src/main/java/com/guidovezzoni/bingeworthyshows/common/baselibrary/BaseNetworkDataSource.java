package com.guidovezzoni.bingeworthyshows.common.baselibrary;

import com.guidovezzoni.bingeworthyshows.common.utils.RxUtils;

import io.reactivex.Maybe;
import io.reactivex.Single;
import retrofit2.Response;

public abstract class BaseNetworkDataSource<M, P> implements DataSource<M, P> {
    protected abstract Single<Response<M>> getFromEndPoint(P params);

    @Override
    public Maybe<M> get(P params) {
        return getFromEndPoint(params)
                .compose(RxUtils.unWrapResponseWithErrorOnStream())
                .toMaybe();
    }

    @Override
    public Maybe<M> getAndUpdate(P params, DataSource<M, P> cacheSource) {
        return get(params)
                .doAfterSuccess(cacheSource::set);
    }
}
