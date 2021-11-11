package com.abcsoftwares.einvoice;



import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.abcsoftwares.einvoice.Components.ApiComponent;
import com.abcsoftwares.einvoice.Components.DaggerApiComponent;
import com.abcsoftwares.einvoice.Modules.ApiModule;
import com.abcsoftwares.einvoice.Modules.AppModule;

import java.util.Locale;

import io.github.inflationx.calligraphy3.CalligraphyConfig;


public class MyApplication extends Application {

    private static ApiComponent mApiComponent;
    private static Utils utils;
    private static TinyDB tinyDB;

    @Override
    public void onCreate() {
        super.onCreate();

        new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Cairo-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();


        utils = new Utils(this);

        tinyDB = new TinyDB(getApplicationContext());

        mApiComponent = DaggerApiComponent.builder().appModule(new AppModule(this))
                .apiModule(new ApiModule("https://einvoice.abcsoftwares.com/api/"))
                .build();

    }

    public static ApiComponent getmApiComponent() {
        return mApiComponent;
    }

    public static Utils getUtils() {
        return utils;
    }

    public static TinyDB getTinyDB() {
        return tinyDB;
    }
}
