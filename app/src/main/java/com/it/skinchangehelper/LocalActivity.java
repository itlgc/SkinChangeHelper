package com.it.skinchangehelper;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.it.skin_library.SkinLocalActivity;
import com.it.skin_library.utils.PreferencesUtils;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

/**
 * @description  APP内置换肤，不涉及皮肤包
 * 缺点：不支持更换字体
 */
public class LocalActivity extends SkinLocalActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_local);
        getSupportActionBar().setTitle(" APP内置换肤测试页");
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        boolean isNight = PreferencesUtils.getBoolean(this, "isNight");
        if (isNight) {
            setDayNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            setDayNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    // 点击事件
    public void onClickDayOrNight(View view) {
        int uiMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        switch (uiMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                setDayNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                PreferencesUtils.putBoolean(this, "isNight", true);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                setDayNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                PreferencesUtils.putBoolean(this, "isNight", false);
                break;
            default:
                break;
        }
    }

    @Override
    protected boolean openChangeSkin() {
        return true;
    }
}