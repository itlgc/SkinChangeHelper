package com.it.skinchangehelper.base;

import android.app.Application;

import com.it.skin_library.SkinManager;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.init(this);
    }
}
