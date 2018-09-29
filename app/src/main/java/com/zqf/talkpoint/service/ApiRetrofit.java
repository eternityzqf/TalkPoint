package com.zqf.talkpoint.service;

import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;
import com.zqf.talkpoint.BuildConfig;
import com.zqf.talkpoint.app.MyApp;
import com.zqf.talkpoint.util.NetWorkUtil;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Author：zqf
 * Time：2018/9/12 11:50
 * desc：Retrofit
 */
public class ApiRetrofit {

    private static ApiRetrofit mApiRetrofit;
    private final Retrofit mRetrofit;
    private OkHttpClient mClient;
    private ApiService mApiService;

    //缓存配置
    private Interceptor mCacheInterceptor = chain -> {

        CacheControl.Builder cacheBuilder = new CacheControl.Builder();
        cacheBuilder.maxAge(0, TimeUnit.SECONDS);
        cacheBuilder.maxStale(365, TimeUnit.DAYS);
        CacheControl cacheControl = cacheBuilder.build();

        Request request = chain.request();
        if (!NetWorkUtil.isNetworkConnected(MyApp.getContext())) {
            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build();
        }
        Response originalResponse = chain.proceed(request);
        if (NetWorkUtil.isNetworkConnected(MyApp.getContext())) {
            int maxAge = 0; // read from cache
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public ,max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    };

    /**
     * 请求访问quest和response拦截器
     */
    private Interceptor mLogInterceptor = chain -> {
        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        okhttp3.Response response = chain.proceed(chain.request());
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        Logger.e("----------Request Start----------------");
        Logger.e("| " + request.toString());
        Logger.e("| Response:" + content);
        Logger.e("----------Request End:" + duration + "毫秒----------");
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    };

    /**
     * 梨视频-->增加头部信息的拦截器
     * X-Channel-Code:official
     * X-Client-Agent:Xiaomi
     * X-Client-Hash:2f3d6ffkda95dlz2fhju8d3s6dfges3t
     * X-Client-ID:123456789123456
     * X-Client-Version:2.3.2
     * X-Long-Token:
     * X-Platform-Type:0
     * X-Platform-Version:5.0
     * X-Serial-Num:1492140134
     * X-User-ID:
     */
    private Interceptor mHeaderInterceptor = chain -> {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("X-Channel-Code", "official");
        builder.addHeader("X-Client-Agent", "Xiaomi");
        builder.addHeader("X-Client-Hash", "2f3d6ffkda95dlz2fhju8d3s6dfges3t");
        builder.addHeader("X-Client-ID", "123456789123456");
        builder.addHeader("X-Client-Version", "2.3.2");
        builder.addHeader("X-Long-Token", "");
        builder.addHeader("X-Platform-Type", "0");
        builder.addHeader("X-Platform-Version", "5.0");
        builder.addHeader("X-Serial-Num", "1538206356378");
        builder.addHeader("X-User-ID", "");
        return chain.proceed(builder.build());
    };


    public ApiRetrofit() {
        //cache url
        File httpCacheDirectory = new File(MyApp.getContext().getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        //HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT);
        //loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//请求/响应行 + 头 + 体

        mClient = new OkHttpClient.Builder()
                .addInterceptor(mHeaderInterceptor)//添加头部信息拦截器
                .addInterceptor(mLogInterceptor)//添加log拦截器
                .cache(cache)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.Host_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .client(mClient)
                .build();

        mApiService = mRetrofit.create(ApiService.class);
    }

    public static ApiRetrofit getInstance() {
        if (mApiRetrofit == null) {
            synchronized (Object.class) {
                if (mApiRetrofit == null) {
                    mApiRetrofit = new ApiRetrofit();
                }
            }
        }
        return mApiRetrofit;
    }

    public ApiService getApiService() {
        return mApiService;
    }
}
