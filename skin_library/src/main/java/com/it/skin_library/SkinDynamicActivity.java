package com.it.skin_library;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.it.skin_library.core.IGeneralSkin;
import com.it.skin_library.core.CustomAppCompatViewInflater;
import com.it.skin_library.utils.ActionBarUtils;
import com.it.skin_library.utils.NavigationUtils;
import com.it.skin_library.utils.StatusBarUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.LayoutInflaterCompat;


/**
 * @Author lgc
 * @Description 动态换肤 换肤Activity父类
 *
 * 用法：
 * 1、继承此类
 * 2、覆写openChangeSkin()方法
 */
public class SkinDynamicActivity extends AppCompatActivity {

    private CustomAppCompatViewInflater viewInflater;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //拦截xml中view的创建，实现xml中资源换肤
        LayoutInflater inflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory2(inflater,this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        if (openChangeSkin()) {
            if (viewInflater == null) {
                viewInflater = new CustomAppCompatViewInflater(context);
            }
            return viewInflater.createSkinView(name, attrs);
        }
        return super.onCreateView(name, context, attrs);
    }

    /**
     * @return 是否开启换肤，增加此开关是为了避免开发者误继承此父类，导致未知bug
     */
    protected boolean openChangeSkin() {
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void defaultSkin(int themeColorId) {
        this.skinDynamic(null, themeColorId);
    }
    /**
     * 动态换肤
     * @param skinPath 皮肤包路径
     * @param themeColorId 修改statusBar、navigationBar、ActionBar等的颜色
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void skinDynamic(String skinPath, int themeColorId) {
        SkinManager.getInstance().loaderSkin(skinPath);

        // TODO: 添加对bar的支持
        if (themeColorId != 0) {
            int themeColor = SkinManager.getInstance().getColor(themeColorId);
            StatusBarUtils.forStatusBar(this, themeColor);
            NavigationUtils.forNavigation(this, themeColor);
            ActionBarUtils.forActionBar(this, themeColor);
        }

        applyViews(getWindow().getDecorView());
    }

    protected void applyViews(View view) {
        if (view instanceof IGeneralSkin) {
            IGeneralSkin generalSkin = (IGeneralSkin) view;
            generalSkin.onSkinChange();
        }

        if (view instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) view;
            for (int i = 0; i < parent.getChildCount(); i++) {
                //递归实现 每个实现了换肤接口的View调用到换肤方法
                applyViews(parent.getChildAt(i));
            }
        }
    }
}
