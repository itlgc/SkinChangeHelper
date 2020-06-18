package com.it.skinchangehelper;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.it.skin_library.SkinDynamicActivity;
import com.it.skin_library.utils.PreferencesUtils;

import java.io.File;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * Created by lgc on 2020-06-18.
 *
 * @Author lgc
 * @Description
 */
public class BaseActivity extends SkinDynamicActivity {

    private String skinPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        skinPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "net163.skin";

        // 运行时权限申请（6.0+）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (checkSelfPermission(perms[0]) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(perms, 200);
            }
        }
    }


    // 换肤按钮（api限制：5.0版本）
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void skinDynamic() {
        //第2个参数，主题颜色 标题栏、底部栏之类的
        skinDynamic(skinPath, R.color.skin_item_color);
    }

    // 默认按钮（api限制：5.0版本）
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void skinDefault() {
        defaultSkin(R.color.colorPrimary);
    }

    @Override
    protected boolean openChangeSkin() {
        return true;
    }
}
