package com.lapak.lapak.api;

import com.lapak.lapak.model.ResponseData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("tagihan.php")
    Call<ResponseData> bayarTagihan(@Field("kode")String qrcode);

}
