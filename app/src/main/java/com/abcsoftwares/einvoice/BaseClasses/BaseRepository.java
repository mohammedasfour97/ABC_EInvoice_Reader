package com.abcsoftwares.einvoice.BaseClasses;

import android.util.Log;

import com.abcsoftwares.einvoice.MyApplication;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class BaseRepository {

    @Inject
    public Retrofit retrofit;

    public BaseRepository(){

        MyApplication.getmApiComponent().inject(this);
    }

}
