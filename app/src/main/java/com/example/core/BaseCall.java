package com.example.core;

import com.example.exception.ApiException;

public interface BaseCall<T> {
    void loadSuccess(T data);
    void loadError(ApiException throwable);
}
