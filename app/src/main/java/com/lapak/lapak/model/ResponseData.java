package com.lapak.lapak.model;

import com.google.gson.annotations.SerializedName;

public class ResponseData {
    @SerializedName("success")
    String success;
    @SerializedName("message")
    String message;
    @SerializedName("kode_lapak")
    String kode_lapak;
    @SerializedName("kode_pedagang")
    String kode_pedagang;
    @SerializedName("harga_lapak")
    String harga_lapak;
    @SerializedName("nama_pedagang")
    String nama_pedagang;
    @SerializedName("lokasi_lapak")
    String lokasi_lapak;


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getKode_lapak() {
        return kode_lapak;
    }

    public void setKode_lapak(String kode_lapak) {
        this.kode_lapak = kode_lapak;
    }

    public String getKode_pedagang() {
        return kode_pedagang;
    }

    public void setKode_pedagang(String kode_pedagang) {
        this.kode_pedagang = kode_pedagang;
    }

    public String getHarga_lapak() {
        return harga_lapak;
    }

    public void setHarga_lapak(String harga_lapak) {
        this.harga_lapak = harga_lapak;
    }

    public String getNama_pedagang() {
        return nama_pedagang;
    }

    public void setNama_pedagang(String nama_pedagang) {
        this.nama_pedagang = nama_pedagang;
    }

    public String getLokasi_lapak() {
        return lokasi_lapak;
    }

    public void setLokasi_lapak(String lokasi_lapak) {
        this.lokasi_lapak = lokasi_lapak;
    }
}
