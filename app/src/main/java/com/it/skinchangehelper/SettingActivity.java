package com.it.skinchangehelper;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.it.skin_library.utils.PreferencesUtils;

import java.io.File;
import java.util.Objects;

public class SettingActivity extends BaseActivity {

    public static final String TAG = SettingActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().setTitle("设置");
        // 运行时权限申请（6.0+）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (checkSelfPermission(perms[0]) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(perms, 200);
            }
        }

        //换肤按钮
        findViewById(R.id.btn_changeskin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 真实项目中：需要先判断当前皮肤，避免重复加载同一套皮肤！
                if (!("net163").equals(PreferencesUtils.getString(getApplicationContext(), "currentSkin"))) {
                    Log.e(TAG, "-------------start-------------");
                    long start = System.currentTimeMillis();

                    //参数1：皮肤包路径     参数2：主题颜色 标题栏、底部栏之类的
                    skinDynamic(skinPath, R.color.skin_item_color);
                    PreferencesUtils.putString(getApplicationContext(), "currentSkin", "net163");

                    long end = System.currentTimeMillis() - start;
                    Log.e(TAG, "换肤耗时（毫秒）：" + end);
                    Log.e(TAG, "-------------end---------------");
                }
            }
        });

        //默认按钮
        findViewById(R.id.btn_default).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!("default").equals(PreferencesUtils.getString(getApplicationContext(), "currentSkin"))) {
                    Log.e(TAG, "-------------start-------------");
                    long start = System.currentTimeMillis();

                    defaultSkin(R.color.colorPrimary);
                    PreferencesUtils.putString(getApplicationContext(), "currentSkin", "default");

                    long end = System.currentTimeMillis() - start;
                    Log.e(TAG, "还原耗时（毫秒）：" + end);
                    Log.e(TAG, "-------------end---------------");
                }
            }
        });

        //跳转按钮
        findViewById(R.id.btn_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }



}
