package com.guidovezzoni.bingeworthyshows.common.utils;

import com.fernandocejas.arrow.optional.Optional;

import java.io.IOException;

import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import retrofit2.Response;

public final class RxUtils {
    private static final String NETWORK_ERROR = "Network error";

    private RxUtils() {
    }

    public static <T> SingleTransformer<Response<T>, T> unWrapResponseWithErrorOnStream() {
        return single -> single.flatMap(response -> {
            if (response.body() != null) {
                return Single.just(response.body());
            }

            try {
                return Single.error(
                        new Exception(
                                response.errorBody() != null ? response.errorBody().string() : NETWORK_ERROR));
            } catch (IOException e) {
                return Single.error(e);
            }
        });
    }

    public static <T> SingleTransformer<Optional<T>, T> getOptionalWithErrorOnStream() {
        return single -> single.flatMap(
                tOptional -> tOptional.isPresent() ?
                        Single.just(tOptional.get()) :
                        Single.error(new Exception("Data could not be retrieved"))
        );
    }
}
