package com.chenli.commonlib.util.themvp.presenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import com.chenli.commonlib.util.themvp.view.IDelegate;

/**
 * Created by Lenovo on 2018/4/10.
 */

public abstract class ActivityPresenter<T extends IDelegate> extends AppCompatActivity{

    protected T viewDelegate;

    public ActivityPresenter(){
        try {
            viewDelegate = getDelegateClass().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("create IDelegate error");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("create IDelegate error");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDelegate.create(getLayoutInflater(),null,savedInstanceState);
        setContentView(viewDelegate.getRootView());
        viewDelegate.initToolbar();
        viewDelegate.initWidget();
        bindEvenListener();
    }

    protected void bindEvenListener() {
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (viewDelegate != null){
            try {
                viewDelegate = getDelegateClass().newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException("create IDelegate error");
            } catch (IllegalAccessException e) {
                throw new RuntimeException("create IDelegate error");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (viewDelegate.getOptionsMenuId() != 0){
            getMenuInflater().inflate(viewDelegate.getOptionsMenuId(),menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewDelegate = null;
    }

    protected abstract Class<T> getDelegateClass();


    @Override
    public void finish() {
        super.finish();
    }
}
