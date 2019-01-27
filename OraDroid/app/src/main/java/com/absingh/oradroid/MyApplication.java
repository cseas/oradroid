package com.absingh.oradroid;

/* MyApplication.java
 * Created by Abhijeet Singh
 * absingh.com
 */

import android.app.Application;

public class MyApplication extends Application {
    public static final String TAG = MyApplication.class.getSimpleName();

    private static MyApplication mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
    public static synchronized MyApplication getInstance() {
        return mInstance;
    }
}
