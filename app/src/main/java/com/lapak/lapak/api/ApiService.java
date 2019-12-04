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
    @POST("tagihan.php")
    Call<ResponseData> tagih(@Field("kode_lapak")String kode_lapak,
                             @Field("harga_lapak")String harga_lapak,
                             @Field("kode_pedagang")String kode_pedagang
                             );



}
