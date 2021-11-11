package com.abcsoftwares.einvoice.Repositories;

import com.abcsoftwares.einvoice.BaseClasses.BaseRepository;
import com.abcsoftwares.einvoice.Models.TLV;
import com.abcsoftwares.einvoice.api.RestApiService;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanResultRepository extends BaseRepository {

    public ScanResultRepository(){

        super();
    }

    public MutableLiveData<List<TLV>> tlvListMutableLiveData(String qrBarcode){

        MutableLiveData<List<TLV>> mutableLiveData = new MutableLiveData<>();
        final List<TLV>[] tlvList = new ArrayList[]{null};

        RestApiService restApiService = retrofit.create(RestApiService.class);
        Call<List<TLV>> tlvListResponse = restApiService.getTLVList(qrBarcode);

        tlvListResponse.enqueue(new Callback<List<TLV>>() {
            @Override
            public void onResponse(Call<List<TLV>> call, Response<List<TLV>> response) {

                tlvList[0] = new ArrayList<>();

                if (response.body() != null && !response.body().isEmpty()){

                    tlvList[0].addAll(response.body());
                }

                mutableLiveData.postValue(tlvList[0]);
            }

            @Override
            public void onFailure(Call<List<TLV>> call, Throwable t) {

                tlvList[0] = new ArrayList<>();

                mutableLiveData.postValue(tlvList[0]);
            }
        });


        return mutableLiveData;
    }
}
