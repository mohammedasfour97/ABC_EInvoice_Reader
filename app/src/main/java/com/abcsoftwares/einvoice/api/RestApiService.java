package com.abcsoftwares.einvoice.api;

import com.abcsoftwares.einvoice.Models.TLV;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApiService {
    @GET("VATQR")
    Call<List<TLV>> getTLVList(@Query("QRString") String qrCode );
}
