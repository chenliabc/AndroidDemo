package com.chenli.commonlib.util.themvp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

/**
 * Created by Lenovo on 2018/4/10.
 * 视图层代理的接口协议
 */

public interface IDelegate {
    void create(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    int getOptionsMenuId();
    Toolbar getToolbar();
    View getRootView();
    void initWidget();
    void initToolbar();
}
