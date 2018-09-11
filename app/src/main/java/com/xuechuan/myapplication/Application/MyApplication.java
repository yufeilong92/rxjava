package com.xuechuan.myapplication.Application;

import android.app.Application;
import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: rxjava
 * @Package com.xuechuan.myapplication.Application
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/9/11 15:14
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MyApplication extends Application {

    private OkHttpClient mClient;
    private MyApplication() {
    }

    private static class SingletonInstance {
        private static final MyApplication INSTANCE = new MyApplication();
    }

    public static MyApplication getInstance() {
        return SingletonInstance.INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initHttp();
    }

    private void initHttp() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);
        builder.connectTimeout(30, TimeUnit.SECONDS);
        mClient = builder.build();
    }

    public OkHttpClient getHttpClient() {
        if (mClient == null) {
            initHttp();
        }
        return mClient;
    }
}
