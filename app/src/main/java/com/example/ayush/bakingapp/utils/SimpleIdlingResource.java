package com.example.ayush.bakingapp.utils;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleIdlingResource implements IdlingResource {

    @Nullable private volatile ResourceCallback callback;
    private AtomicBoolean mIsIdleNow = new AtomicBoolean(true);
    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return mIsIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }

public void setIdleState(boolean isIdle){
        mIsIdleNow.set(isIdle);
    if (isIdle && callback != null) {
        callback.onTransitionToIdle();
    }
}
}
