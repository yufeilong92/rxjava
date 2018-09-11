package com.xuechuan.myapplication.view;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: rxjava
 * @Package com.xuechuan.myapplication.view
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/9/11 16:08
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface RequestResultView<T> {
    public void Error(String  msg);

    public void Success(T success);

    public void ShowLoading();
}
