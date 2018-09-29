package com.zqf.talkpoint.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.zqf.talkpoint.BuildConfig;

/**
 * Author：zqf
 * Time：2018/9/12 11:50
 * desc：Application入口
 */
public class MyApp extends Application {

    private static SPUtils spUtils;
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        initLogger();

        Utils.init(this);
        spUtils = SPUtils.getInstance("sp_talk_point");
        CrashUtils.init();
        /**
         * buildConfigField的boolean值来判断是否开启
         * 这两行必须写在init之前，否则这些配置在init过程中将无效
         * ARouter.openLog(): 打印日志
         * ARouter.openDebug(): 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
         */
        if (BuildConfig.API_ENV) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        //尽可能早，推荐在Application中初始化
        ARouter.init(this);

    }

    private void initLogger() {
        if (BuildConfig.API_ENV) {
            //默认日志输出方式
        }
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static Context getContext() {
        return context;
    }

    public static SPUtils getSp() {
        return spUtils;
    }
}
