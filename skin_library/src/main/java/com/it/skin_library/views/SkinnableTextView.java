package com.it.skin_library.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.it.skin_library.R;
import com.it.skin_library.SkinManager;
import com.it.skin_library.bean.AttrsBean;
import com.it.skin_library.core.IGeneralSkin;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

/**
 * 继承TextView兼容包，9.0源码中也是如此
 * 参考：AppCompatViewInflater.java
 */
public class SkinnableTextView extends AppCompatTextView implements IGeneralSkin {

    private AttrsBean attrsBean;

    public SkinnableTextView(Context context) {
        this(context, null);
    }

    public SkinnableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public SkinnableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 根据自定义属性，匹配控件属性的类型集合，如：background + textColor
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.SkinnableTextView,
                defStyleAttr, 0);
        // 存储到临时JavaBean对象
        attrsBean = new AttrsBean();
        attrsBean.saveViewResource(typedArray, R.styleable.SkinnableTextView);
        typedArray.recycle();
    }

    @Override
    public void onSkinChange() {
        // 根据自定义属性，获取styleable中的background属性
        int key = R.styleable.SkinnableTextView[R.styleable.SkinnableTextView_android_background];
        // 根据styleable获取控件某属性的resourceId
        int backgroundResourceId = attrsBean.getViewResource(key);
        if (backgroundResourceId > 0) {
            // 是否默认皮肤
            if (SkinManager.getInstance().isDefaultSkin()) {
                // 兼容包转换 这里不用setBackgroundColor()因为在9.0测试不通过
                Drawable drawable = ContextCompat.getDrawable(getContext(), backgroundResourceId);
                setBackgroundDrawable(drawable);
            } else {
                // 获取皮肤包资源
                Object skinResourceId = SkinManager.getInstance().getBackgroundOrSrc(backgroundResourceId);
                // 兼容包转换
                if (skinResourceId instanceof Integer) {
                    int color = (int) skinResourceId;
                    setBackgroundColor(color);
                    // setBackgroundResource(color); // 未做兼容测试
                } else {
                    Drawable drawable = (Drawable) skinResourceId;
                    setBackgroundDrawable(drawable);
                }
            }
        }

        // 根据自定义属性，获取styleable中的textColor属性
        key = R.styleable.SkinnableTextView[R.styleable.SkinnableTextView_android_textColor];
        int textColorResourceId = attrsBean.getViewResource(key);
        if (textColorResourceId > 0) {
            if (SkinManager.getInstance().isDefaultSkin()) {
                ColorStateList color = ContextCompat.getColorStateList(getContext(), textColorResourceId);
                setTextColor(color);
            } else {
                ColorStateList color = SkinManager.getInstance().getColorStateList(textColorResourceId);
                setTextColor(color);
            }
        }

        // 根据自定义属性，获取styleable中的字体 custom_typeface 属性
        key = R.styleable.SkinnableTextView[R.styleable.SkinnableTextView_custom_typeface];
        int textTypefaceResourceId = attrsBean.getViewResource(key);
        if (textTypefaceResourceId > 0) {
            if (SkinManager.getInstance().isDefaultSkin()) {
                setTypeface(Typeface.DEFAULT);
            } else {
                setTypeface(SkinManager.getInstance().getTypeface(textTypefaceResourceId));
            }
        }
    }
}
