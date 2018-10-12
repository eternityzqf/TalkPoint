package com.zqf.talkpoint.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.zqf.talkpoint.BuildConfig;

import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.NetProvider;
import cn.droidlover.xdroidmvp.net.RequestHandler;
import cn.droidlover.xdroidmvp.net.XApi;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

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
        context = getApplicationContext();
        Utils.init(this);
        spUtils = SPUtils.getInstance("sp_talk_point");
        CrashUtils.init();

        initLogger();

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

        //使用第三方MVP框架
        DroidMvp();
    }

    private void DroidMvp() {
        XApi.registerProvider(new NetProvider() {
            @Override
            public Interceptor[] configInterceptors() {
                return new Interceptor[0];
            }

            @Override
            public void configHttps(OkHttpClient.Builder builder) {

            }

            @Override
            public CookieJar configCookie() {
                return null;
            }

            @Override
            public RequestHandler configHandler() {
                return null;
            }

            @Override
            public long configConnectTimeoutMills() {
                return 0;
            }

            @Override
            public long configReadTimeoutMills() {
                return 0;
            }

            @Override
            public boolean configLogEnable() {
                return true;
            }

            @Override
            public boolean handleError(NetError error) {
                return false;
            }

            @Override
            public boolean dispatchProgressEnable() {
                return false;
            }
        });
    }

    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false) //（可选）是否显示线程信息。默认值true
                .methodCount(2)  //（可选）要显示的方法行数。默认值2
                .methodOffset(7) //（可选）隐藏内部方法调用到偏移量。默认值5
                .tag("Talk")   //（可选）每个日志的全局标记。默认PRETTY_LOGGER
                .build();
        if (BuildConfig.API_ENV) {
            //默认日志输出方式
            Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        }
    }

    public static Context getContext() {
        return context;
    }

    public static SPUtils getSp() {
        return spUtils;
    }
}
