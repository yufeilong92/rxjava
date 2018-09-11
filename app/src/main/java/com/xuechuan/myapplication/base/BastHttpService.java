package com.xuechuan.myapplication.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.xuechuan.myapplication.Application.MyApplication;
import com.xuechuan.myapplication.view.RequestResultView;

import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: rxjava
 * @Package com.xuechuan.myapplication.base
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/9/11 15:11
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class BastHttpService {
    private static Context mContext;

    private BastHttpService() {
    }

    private static class SingletonInstance {
        private static final BastHttpService INSTANCE = new BastHttpService();
    }

    public static BastHttpService getInstance(Context context) {
        mContext = context;
        return SingletonInstance.INSTANCE;
    }

    public <Result> void reqeustBitmapPost(final String url, final Class<Result> c, final RequestResultView<Result> view) {
        MyApplication application = MyApplication.getInstance();
        final OkHttpClient client = application.getHttpClient();
        Observer<Result> observer = new Observer<Result>() {
            @Override
            public void onSubscribe(Disposable d) {
                view.ShowLoading();
            }

            @Override
            public void onNext(Result s) {
                view.Success(s);
            }


            @Override
            public void onError(Throwable e) {
                view.Error(e.getMessage());
            }

            @Override
            public void onComplete() {
                view.ShowLoading();
            }
        };

        Observable.create(new ObservableOnSubscribe<Result>() {
            @Override
            public void subscribe(final ObservableEmitter<Result> emitter) throws Exception {
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        emitter.onError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            InputStream inputStream = response.body().byteStream();
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            if (c != null) {
                                emitter.onNext(c.cast(bitmap));
                            } else {
                                emitter.onNext((Result) bitmap);
                            }
                            emitter.onComplete();
                        } else {
                            throw new IllegalArgumentException(response.message() + "结果异常");
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
    public <Result> void reqeustStringPost(final String url, final Class<Result> c, final RequestResultView<Result> view) {
        MyApplication application = MyApplication.getInstance();
        final OkHttpClient client = application.getHttpClient();
        Observer<Result> observer = new Observer<Result>() {
            @Override
            public void onSubscribe(Disposable d) {
                view.ShowLoading();
            }

            @Override
            public void onNext(Result s) {
                view.Success(s);
            }


            @Override
            public void onError(Throwable e) {
                view.Error(e.getMessage());
            }

            @Override
            public void onComplete() {
                view.ShowLoading();
            }
        };

        Observable.create(new ObservableOnSubscribe<Result>() {
            @Override
            public void subscribe(final ObservableEmitter<Result> emitter) throws Exception {
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        emitter.onError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String s = response.body().toString();
                            if (c != null) {
                                emitter.onNext(c.cast(s));
                            } else {
                                emitter.onNext((Result) s);
                            }
                            emitter.onComplete();
                        } else {
                            throw new IllegalArgumentException(response.message() + "结果异常");
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

}
