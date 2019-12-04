package com.lapak.lapak;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.lapak.lapak.api.ApiService;
import com.lapak.lapak.api.Service;
import com.lapak.lapak.model.ResponseData;
import com.lapak.lapak.pref.PrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ProgressDialog pDialog;
    IntentIntegrator intentIntegrator;

    ApiService API;

    Dialog myDialog;

    CardView btnScan, btnAbout, btnPetunjuk, btnTentang;

    String message, kode_lapak, lokasi_lapak, harga_lapak, kode_pedagang, nama_pedagang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnScan = findViewById(R.id.btnScan);
        btnAbout = findViewById(R.id.btnAbout);
        btnPetunjuk = findViewById(R.id.btnPetunjuk);
        btnTentang = findViewById(R.id.btnTentang);

        API = Service.getAPIService();


        myDialog = new Dialog(this);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.initiateScan();
            }
        });


        btnTentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup();
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, LapakActivity.class);
                startActivity(intent);

            }
        });

        btnPetunjuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Petunjuk

                PrefManager prefManager = new PrefManager(getApplicationContext());
                prefManager.setFirstTimeLaunch(true);
                startActivity(new Intent(MainActivity.this, PetunjukActivity.class));
                finish();

            }
        });

    }

    private void ShowPopup() {

        TextView txtclose;
        myDialog.setContentView(R.layout.popup_about);
        txtclose = myDialog.findViewById(R.id.txtclose);
        txtclose.setText("x");

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null){

            if (result.getContents() == null){
                 Toast.makeText(this, "TIDAK ADA DATA", Toast.LENGTH_SHORT).show();
            }else {

                try{


                    String kode = result.getContents();

                    Cek(kode);

//                    Toast.makeText(MainActivity.this, kode, Toast.LENGTH_LONG).show();


                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void Cek(String kode) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(true);
        pDialog.setMessage("Tunggu");
        pDialog.show();

        API.bayarTagihan(kode).enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                try{
                    if (response.isSuccessful()){
                        ResponseData responseData = response.body();

                        assert responseData != null;

                        if (responseData.getSuccess().equals("1")){
                            pDialog.cancel();

                            Toast.makeText(MainActivity.this, responseData.getMessage(), Toast.LENGTH_LONG).show();

                            message = responseData.getMessage();
                            nama_pedagang = responseData.getNama_pedagang();
                            kode_lapak = responseData.getKode_lapak();
                            kode_pedagang = responseData.getKode_pedagang();
                            harga_lapak = responseData.getHarga_lapak();
                            lokasi_lapak = responseData.getLokasi_lapak();


                            //IntentExtra untuk membawa data ke halaman hasil scan
                            Intent i = new Intent (MainActivity.this, TagihanDetailActivity.class);
                            i.putExtra("message", message);
                            i.putExtra("nama_pedagang", nama_pedagang);
                            i.putExtra("kode_lapak", kode_lapak);
                            i.putExtra("harga_lapak", harga_lapak);
                            i.putExtra("kode_pedagang", kode_pedagang);
                            i.putExtra("lokasi_lapak", lokasi_lapak);


                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }else {
                            pDialog.cancel();
                            Toast.makeText(MainActivity.this, responseData.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }else {
//                        ResponseData responseData = response.body();
                        pDialog.cancel();
                        Toast.makeText(MainActivity.this, "Gagal", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pDialog.cancel();
                Toast.makeText(MainActivity.this, "FAILURE"+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }


}
