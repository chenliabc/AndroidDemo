package com.chenli.commonlib.util.themvp.databind;

import android.os.Bundle;
import android.view.View;

import com.chenli.commonlib.util.themvp.model.IModel;
import com.chenli.commonlib.util.themvp.presenter.FragmentPresenter;
import com.chenli.commonlib.util.themvp.view.IDelegate;

/**
 * Created by Lenovo on 2018/4/10.
 */

public abstract class DataBindFragment<T extends IDelegate> extends FragmentPresenter<T> {

    protected DataBinder binder;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binder = getDataBinder();
        bindLocalData();
    }

    /**
     * 绑定本地数据。
     */
    protected void bindLocalData() {
    }

    public abstract DataBinder getDataBinder();

    public <D extends IModel> void notifyModelChanged(D data){
        if (binder != null){
            binder.viewBindModel(viewDelegate,data);
        }
    }

}
