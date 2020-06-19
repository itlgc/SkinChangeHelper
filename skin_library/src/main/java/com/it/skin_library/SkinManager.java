package com.it.skin_library;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.it.skin_library.bean.SkinCache;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author lgc
 * @Description 皮肤管理器
 * 加载应用资源  app内置 or 皮肤包资源
 */
public class SkinManager {
    public static final String TAG = SkinManager.class.getSimpleName();
    private static SkinManager instance;
    private Application application;
    private Resources appResources; // 用于加载app内置资源
    private Resources skinResources; // 用于加载皮肤包资源
    private String skinPackageName; // 皮肤包资源所在包名（注：皮肤包不在app内，也不限包名）
    private boolean isDefaultSkin = true; // 应用默认皮肤（app内置）
    private Map<String, SkinCache> cacheSkin;

    public boolean isDefaultSkin() {
        return isDefaultSkin;
    }

    public static SkinManager getInstance() {
        return instance;
    }

    private SkinManager(Application application) {
        this.application = application;
        appResources = application.getResources();
        cacheSkin = new WeakHashMap<>();
    }

    /**
     * 单例方法，目的是初始化app内置资源
     */
    public static void init(Application application) {
        if (instance == null) {
            synchronized (SkinManager.class) {
                if (instance == null) {
                    instance = new SkinManager(application);
                }
            }
        }
    }

    /**
     * 加载皮肤包资源
     * @param skinPath 皮肤包路径，为空则加载app内置资源
     */
    public void loaderSkin(String skinPath) {
        // 如果没有皮肤包或者没做换肤动作，方法不执行直接返回！
        if (TextUtils.isEmpty(skinPath)) {
            isDefaultSkin = true;
            return;
        }

        // 优化：app冷启动、热启动可以取缓存对象
        if (cacheSkin.containsKey(skinPath)) {
            isDefaultSkin = false;
            SkinCache skinCache = cacheSkin.get(skinPath);
            if (null != skinCache) {
                skinResources = skinCache.getSkinResources();
                skinPackageName = skinCache.getSkinPackageName();
                Log.e("TAG", "读取对应皮肤包的缓存资源！");
                return;
            }
        }

        try {
            //创建资源管理器
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPathMethod = assetManager.getClass().getDeclaredMethod("addAssetPath",
                    String.class);
            addAssetPathMethod.setAccessible(true);
            addAssetPathMethod.invoke(assetManager, skinPath);

            //创建用于加载皮肤包资源的Resources
            skinResources = new Resources(assetManager, appResources.getDisplayMetrics(),
                    appResources.getConfiguration());

            //获取皮肤包包名
            PackageInfo packageInfo = application.getPackageManager()
                    .getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES);
            if (packageInfo != null) {
                skinPackageName = packageInfo.packageName;
                Log.e("TAG", "skinPackageName >>> " + skinPackageName);
            }else {
                Log.e("TAG", "根据apk文件路径获取包信息失败！");
            }

            //无法获取皮肤包应用的包名，则加载app内置资源
            isDefaultSkin  = TextUtils.isEmpty(skinPackageName);
            if (!isDefaultSkin) {
                cacheSkin.put(skinPath, new SkinCache(skinResources, skinPackageName));
            }

        } catch (Exception e) {
            e.printStackTrace();
            isDefaultSkin = true;
        }
    }

    /**
     * 通过ID值获取资源 Name 和 Type   ——参考：resources.arsc资源映射表
     *
     * @param resourceId 资源ID值
     * @return 如果没有皮肤包则加载app内置资源ID，反之加载皮肤包指定资源ID
     */
    private int getSkinResourceIds(int resourceId) {
        // 优化：如果没有皮肤包或者没做换肤动作，直接返回app内置资源！
        if (isDefaultSkin) return resourceId;


        // 使用app内置资源加载，是因为内置资源与皮肤包资源一一对应（“netease_bg”, “drawable”）
        String resourceName = appResources.getResourceEntryName(resourceId);
        String resourceType = appResources.getResourceTypeName(resourceId);

        // 动态获取皮肤包内的指定资源ID
        // getResources().getIdentifier(“netease_bg”, “drawable”, “com.it.skin.packages”);
        int skinResourceId = skinResources.getIdentifier(resourceName, resourceType,
                skinPackageName);

        if (skinResourceId == 0) {
            isDefaultSkin = true;
            return resourceId;
        }else {
            isDefaultSkin = false;
            return skinResourceId;
        }
    }



    //=======================获取资源的方法  使用内置资源还是使用皮肤包的资源=======================
    public int getColor(int resourceId) {
        int ids = getSkinResourceIds(resourceId);
        return isDefaultSkin ? appResources.getColor(ids) : skinResources.getColor(ids);
    }

    public ColorStateList getColorStateList(int resourceId) {
        int ids = getSkinResourceIds(resourceId);
        return isDefaultSkin ? appResources.getColorStateList(ids) : skinResources.getColorStateList(ids);
    }

    public Drawable getDrawableOrMipMap(int resourceId) {
        int ids = getSkinResourceIds(resourceId);
        return isDefaultSkin ? appResources.getDrawable(ids) : skinResources.getDrawable(ids);
    }

    public String getString(int resourceId) {
        int ids = getSkinResourceIds(resourceId);
        return isDefaultSkin ? appResources.getString(ids) : skinResources.getString(ids);
    }

    // 返回值特殊情况：可能是color / drawable / mipmap
    public Object getBackgroundOrSrc(int resourceId) {
        // 需要获取当前属性的类型名Resources.getResourceTypeName(resourceId)再判断
        String resourceTypeName = appResources.getResourceTypeName(resourceId);

        switch (resourceTypeName) {
            case "color":
                return getColor(resourceId);
            case "mipmap": // drawable / mipmap
            case "drawable":
                return getDrawableOrMipMap(resourceId);
        }
        return null;
    }

    // 获得字体
    public Typeface getTypeface(int resourceId) {
        // 通过资源ID获取资源path，参考：resources.arsc资源映射表
        String skinTypefacePath = getString(resourceId);
        // 路径为空，使用系统默认字体
        if (TextUtils.isEmpty(skinTypefacePath)) return Typeface.DEFAULT;
        return isDefaultSkin ? Typeface.createFromAsset(appResources.getAssets(), skinTypefacePath)
                : Typeface.createFromAsset(skinResources.getAssets(), skinTypefacePath);
    }

}
