package com.chenli.commonlib.util.themvp.databind;

import android.os.Bundle;

import com.chenli.commonlib.util.mainutil.LogUtils;
import com.chenli.commonlib.util.themvp.model.IModel;
import com.chenli.commonlib.util.themvp.presenter.ActivityPresenter;
import com.chenli.commonlib.util.themvp.view.IDelegate;

/**
 * Created by Lenovo on 2018/4/10.
 */

public abstract class DataBindActivity<T extends IDelegate> extends ActivityPresenter<T> {
    protected DataBinder binder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = getDataBinder();
        bindLocalData();
    }

    /**
     * 绑定本地数据
     */
    protected void bindLocalData(){

    }

    protected abstract DataBinder getDataBinder();

    public <D extends IModel> void notifyModelChanged(D data){
        if (binder != null){
            binder.viewBindModel(viewDelegate,data);
        }else {
            LogUtils.e("chenli"," binder is null ");
        }
    }

    @Override
    public void finish() {
        super.finish();
    }
}
