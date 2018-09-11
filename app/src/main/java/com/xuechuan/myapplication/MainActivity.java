package com.xuechuan.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xuechuan.myapplication.base.BastHttpService;
import com.xuechuan.myapplication.utils.StringUtls;
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

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "【" + MainActivity.class.getSimpleName() + "】==";
    private TextView mTv1;
    private ImageView mIvImage;

    private String httpUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536657980548&di=a26bf2482111c8c0cc57d7e5c6b78d2d&imgtype=0&src=http%3A%2F%2Fww2.sinaimg.cn%2Flarge%2Fbd759d6djw1euqebjcx3sj20go0cijry.jpg";
    private String httpUrl1 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536667627536&di=4510f5070a4d427fb9116bea8bfa3124&imgtype=0&src=http%3A%2F%2Fimg4.pengfu.com%2Fbig%2F56%2F602056.jpg";
    private OkHttpClient mClient;
    private Context mContext;
    private BastHttpService mService;
    private ImageView mIvImage2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initHttp();
        initRxjava();
//        initImager();
        requestBitmap();
        requestBitmaps();


    }

    private void initData() {
        mService = BastHttpService.getInstance(mContext);
    }

    private void requestBitmaps() {
        mService.reqeustBitmapPost(httpUrl1, Bitmap.class, new RequestResultView<Bitmap>() {
            @Override
            public void Error(String msg) {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void Success(Bitmap success) {
                mIvImage2.setImageBitmap(success);
            }

            @Override
            public void ShowLoading() {

            }
        });
    }

    private void requestBitmap() {

        mService.reqeustBitmapPost(httpUrl, Bitmap.class, new RequestResultView<Bitmap>() {
                    @Override
                    public void Error(String msg) {
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void Success(Bitmap success) {
                        mIvImage.setImageBitmap(success);
                    }

                    @Override
                    public void ShowLoading() {

                    }
                }
        );
    }

    private void initHttp() {


    }

    private void initImager() {
        Observer<Bitmap> observer = new Observer<Bitmap>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(Bitmap bitmap) {
                mIvImage.setImageBitmap(bitmap);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.toString());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: ");
            }
        };
        Observable.create(new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(final ObservableEmitter<Bitmap> emitter) throws Exception {
                Request request = new Request.Builder()
                        .url(httpUrl)
                        .build();
                mClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        emitter.onError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            InputStream inputStream = response.body().byteStream();
                            emitter.onNext(BitmapFactory.decodeStream(inputStream));
                        } else {
                            throw new IllegalArgumentException("参数异常");
                        }

                    }
                });

            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @SuppressLint("CheckResult")
    private void initRxjava() {
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe: " + d.toString());
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "onNext: " + s);
            }


            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: ");
            }
        };
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("1234567");
                emitter.onComplete();
            }
        }).observeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(observer);


    }

    private void initView() {
        mContext = this;
        mTv1 = (TextView) findViewById(R.id.tv_1);
        mIvImage = (ImageView) findViewById(R.id.iv_image);
        mIvImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringUtls utls = StringUtls.getInstance(mContext);
                utls.ShowDialog();
            }
        });
        mIvImage2 = (ImageView) findViewById(R.id.iv_image2);
    }
}
