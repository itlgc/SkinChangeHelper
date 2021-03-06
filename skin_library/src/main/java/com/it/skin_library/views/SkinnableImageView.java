package com.it.skin_library.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.it.skin_library.R;
import com.it.skin_library.SkinManager;
import com.it.skin_library.bean.AttrsBean;
import com.it.skin_library.core.IGeneralSkin;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

/**
 * 继承TextView兼容包，9.0源码中也是如此
 * 参考：AppCompatViewInflater.java
 */
public class SkinnableImageView extends AppCompatImageView implements IGeneralSkin {

    private AttrsBean attrsBean;

    public SkinnableImageView(Context context) {
        this(context, null);
    }

    public SkinnableImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkinnableImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 根据自定义属性，匹配控件属性的类型集合，如：src
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.SkinnableImageView,
                defStyleAttr, 0);
        // 存储到临时JavaBean对象
        attrsBean = new AttrsBean();
        attrsBean.saveViewResource(typedArray, R.styleable.SkinnableImageView);
        typedArray.recycle();
    }

    @Override
    public void onSkinChange() {
        // 根据自定义属性，获取styleable中的src属性
        int key = R.styleable.SkinnableImageView[R.styleable.SkinnableImageView_android_src];
        // 根据styleable获取控件某属性的resourceId
        int backgroundResourceId = attrsBean.getViewResource(key);
        if (backgroundResourceId > 0) {
            // 是否默认皮肤
            if (SkinManager.getInstance().isDefaultSkin()) {
                // 兼容包转换
                Drawable drawable = ContextCompat.getDrawable(getContext(), backgroundResourceId);
                setImageDrawable(drawable);
            } else {
                // 获取皮肤包资源
                Object skinResourceId = SkinManager.getInstance().getBackgroundOrSrc(backgroundResourceId);
                // 兼容包转换
                if (skinResourceId instanceof Integer) {
                    int color = (int) skinResourceId;
                    setImageResource(color);
                    // setImageBitmap(); // Bitmap未添加
                } else {
                    Drawable drawable = (Drawable) skinResourceId;
                    setImageDrawable(drawable);
                }
            }
        }
    }
}
