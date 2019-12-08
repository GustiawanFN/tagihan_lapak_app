package com.lapak.lapak.api;

import com.lapak.lapak.model.ResponseData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("cek_lapak.php")
    Call<ResponseData> bayarTagihan(@Field("kode")String qrcode);

    @FormUrlEncoded
    @POST("bayar.php")
    Call<ResponseData> bayar(@Field("kd_lapak")String kd_lapak,
                             @Field("harga")String harga,
                             @Field("kd_pedagang")String kd_pedagang
                             );
}
