package com.guidovezzoni.bingeworthyshows.common.base;

public class Perishable<M> {

    private final M model;
    private final Long timestamp;

    public Perishable(M model, Long timestamp) {
        this.model = model;
        this.timestamp = timestamp;
    }

    public Perishable(M model) {
        this.model = model;
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> Perishable<T> of(T t) {
        return new Perishable<>(t);
    }

    public static <T> Perishable<T> of(T t, Long timestamp) {
        return new Perishable<>(t, timestamp);
    }

    public M getModel() {
        return model;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
