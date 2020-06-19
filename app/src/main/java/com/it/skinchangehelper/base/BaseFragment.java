package com.it.skinchangehelper.base;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.it.skin_library.utils.PreferencesUtils;
import com.it.skinchangehelper.R;

import java.io.File;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by lgc on 2020-06-19.
 *
 * @Author lgc
 * @Description
 */
public class BaseFragment extends Fragment {

    public static final String TAG = BaseFragment.class.getSimpleName();

    private String skinPath;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //实际开发设为公用变量
        skinPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "net163.skin";
    }


    @Override
    public void onResume() {
        super.onResume();
        long start = System.currentTimeMillis();
        if (("net163").equals(PreferencesUtils.getString(getActivity(), "currentSkin"))) {

            ((BaseActivity) getActivity()).skinDynamic(skinPath, R.color.skin_item_color);
        } else {
            ((BaseActivity) getActivity()).defaultSkin(R.color.colorPrimary);
        }
        long end = System.currentTimeMillis() - start;
        Log.e(TAG, "换肤耗时（毫秒）：" + end);
        Log.e(TAG, "-------------end---------------");
    }

}
