package com.lapak.lapak;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lapak.lapak.api.ApiService;
import com.lapak.lapak.api.Service;
import com.lapak.lapak.model.ResponseData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TagihanDetailActivity extends AppCompatActivity {

    TextView nama_pedagang, kode_lapak, kode_pedagang, harga_lapak, lokasi_lapak;
    Button btn_bayar;
    String nama, kd_lapak, kd_pedagang, harga, lokasi, message;
    ProgressDialog pDialog;
    ApiService API;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagihan_detail);

        nama_pedagang = findViewById(R.id.nama);
        kode_lapak = findViewById(R.id.kode_lapak);
        kode_pedagang = findViewById(R.id.kode_pedagang);
        harga_lapak = findViewById(R.id.harga_lapak);
        lokasi_lapak = findViewById(R.id.lokasi_lapak);
        btn_bayar = findViewById(R.id.bayar);

        API = Service.getAPIService();


        nama = getIntent().getStringExtra("nama_pedagang");
        kd_lapak = getIntent().getStringExtra("kode_lapak");
        kd_pedagang = getIntent().getStringExtra("kode_pedagang");
        harga = getIntent().getStringExtra("harga_lapak");
        lokasi = getIntent().getStringExtra("lokasi_lapak");

        nama_pedagang.setText(nama);
        kode_lapak.setText(kd_lapak);
        lokasi_lapak.setText(lokasi);
        kode_pedagang.setText(kd_pedagang);
        harga_lapak.setText(harga);

        btn_bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bayar(kd_lapak, harga, kd_pedagang);
            }
        });

    }

    private void bayar(String kd_lapak, String harga, String kd_pedagang) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(true);
        pDialog.setMessage("Tunggu");
        pDialog.show();

        API.bayar(kd_lapak, harga, kd_pedagang).enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {

                try{
                    if (response.isSuccessful()){
                        ResponseData responseData = response.body();

                        assert responseData != null;

                        if (responseData.getSuccess().equals("1")){
                            pDialog.cancel();

                            Toast.makeText(TagihanDetailActivity.this, responseData.getMessage(), Toast.LENGTH_LONG).show();

                            message = responseData.getMessage();
                            //IntentExtra untuk membawa data ke halaman hasil scan
                            Intent i = new Intent (TagihanDetailActivity.this, HasilActivity.class);
                            i.putExtra("message", message);

                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);

                        }else{
                            pDialog.cancel();
                            Toast.makeText(TagihanDetailActivity.this, responseData.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }else {
//                        ResponseData responseData = response.body();
                        pDialog.cancel();
                        Toast.makeText(TagihanDetailActivity.this, "Gagal", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pDialog.cancel();
                Toast.makeText(TagihanDetailActivity.this, "FAILURE"+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }
}
