package com.xuechuan.myapplication.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.xuechuan.myapplication.MainActivity;
import com.xuechuan.myapplication.base.BastHttpService;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: rxjava
 * @Package com.xuechuan.myapplication.utils
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/9/11 15:34
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class StringUtls {
    private static Context mContext;

    private StringUtls() {
    }

    private static class SingletonInstance {
        private static final StringUtls INSTANCE = new StringUtls();
    }

    public static StringUtls getInstance(Context context) {
        mContext = context;
        return SingletonInstance.INSTANCE;
    }


    public AlertDialog ShowDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("加载中....");
        builder.setMessage("你看看");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(mContext, "确定了", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create();
        return builder.show();
    }

}
