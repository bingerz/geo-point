package cn.bingerz.android.geopointsample;

import android.app.Application;

import com.mapbox.mapboxsdk.Mapbox;

/**
 * Created by hanson on 15/12/2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        String token = "pk.eyJ1Ijoieml6YWkiLCJhIjoiY2piN3p3Z3M4MTI1ejMzcXEzbm1mNHEwYSJ9.m3rpntehuaOdQpysVt5upA";
        Mapbox.getInstance(getApplicationContext(), token);
    }
}
