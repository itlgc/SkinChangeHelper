package com.it.skin_library.core;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.it.skin_library.views.SkinnableButton;
import com.it.skin_library.views.SkinnableImageView;
import com.it.skin_library.views.SkinnableLinearLayout;
import com.it.skin_library.views.SkinnableRelativeLayout;
import com.it.skin_library.views.SkinnableTextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatViewInflater;

/**
 * 自定义控件加载器
 * @Author lgc
 */
public final class CustomAppCompatViewInflater extends AppCompatViewInflater {

    private String name; // 控件名
    private Context context; // 上下文
    private AttributeSet attrs; // 某控件对应所有属性

    public CustomAppCompatViewInflater(@NonNull Context context) {
        this.context = context;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttrs(AttributeSet attrs) {
        this.attrs = attrs;
    }


    /**
     * 自动匹配控件名，并初始化控件对象
     * @param name 控件名
     * @param attrs 属性
     * @return
     */
    public View createSkinView(String name, AttributeSet attrs) {
        View view = null;
        switch (name) {
            case "LinearLayout":
                // view = super.createTextView(context, attrs); // 源码写法
                view = new SkinnableLinearLayout(context, attrs);
                this.verifyNotNull(view, name);
                break;
            case "RelativeLayout":
                view = new SkinnableRelativeLayout(context, attrs);
                this.verifyNotNull(view, name);
                break;
            case "TextView":
                view = new SkinnableTextView(context, attrs);
                this.verifyNotNull(view, name);
                break;
            case "ImageView":
                view = new SkinnableImageView(context, attrs);
                this.verifyNotNull(view, name);
                break;
            case "Button":
                view = new SkinnableButton(context, attrs);
                this.verifyNotNull(view, name);
                break;
            //可进行扩展....
        }
        return view;
    }


    /**
     * 校验控件不为空（源码方法，由于private修饰，只能复制过来了。为了代码健壮，可有可无）
     *
     * @param view 被校验控件，如：AppCompatTextView extends TextView（v7兼容包，兼容是重点！！！）
     * @param name 控件名，如："ImageView"
     */
    private void verifyNotNull(View view, String name) {
        if (view == null) {
            throw new IllegalStateException(this.getClass().getName() + " asked to inflate view for <" + name + ">, but returned null");
        }
    }
}
