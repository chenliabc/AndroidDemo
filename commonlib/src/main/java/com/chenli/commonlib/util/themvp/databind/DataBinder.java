package com.chenli.commonlib.util.themvp.databind;

import com.chenli.commonlib.util.themvp.model.IModel;
import com.chenli.commonlib.util.themvp.view.IDelegate;

/**
 * Created by Lenovo on 2018/4/10.
 */

public interface DataBinder<T extends IDelegate,D extends IModel> {
    /**
     * 将数据与View绑定，这样当数据改变的时候，框架就知道这个数据是和哪个View绑定在一起的，就可以自动改变ui
     * 当数据改变的时候，会回调本方法。
     * @param viewDelegate
     * @param data
     */
    void viewBindModel(T viewDelegate,D data);
}
