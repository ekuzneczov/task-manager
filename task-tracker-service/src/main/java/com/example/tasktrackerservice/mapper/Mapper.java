package com.example.tasktrackerservice.mapper;

public interface Mapper<F, T> {

    default T map(F object) {
        return null;
    }

    default T map(F fromObject, T toObject) {
        return toObject;
    }
}
