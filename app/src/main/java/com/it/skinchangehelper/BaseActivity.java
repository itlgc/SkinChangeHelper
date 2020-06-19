package com.it.skinchangehelper;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import com.it.skin_library.SkinDynamicActivity;
import com.it.skin_library.utils.PreferencesUtils;
import java.io.File;
import androidx.annotation.Nullable;

/**
 * @Author lgc
 * @Description
 */
public class BaseActivity extends SkinDynamicActivity {
    public static final String TAG = BaseActivity.class.getSimpleName();

    protected String skinPath;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skinPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "net163.skin";
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.e(TAG, "-------------start-------------");
        long start = System.currentTimeMillis();
        if (("net163").equals(PreferencesUtils.getString(this, "currentSkin"))) {
            skinDynamic(skinPath, R.color.skin_item_color);
        } else {
            defaultSkin(R.color.colorPrimary);
        }
        long end = System.currentTimeMillis() - start;
        Log.e(TAG, "换肤耗时（毫秒）：" + end);
        Log.e(TAG, "-------------end---------------");

    }

    @Override
    protected boolean openChangeSkin() {
        return true;
    }
}
