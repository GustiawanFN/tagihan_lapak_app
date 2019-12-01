package com.lapak.lapak;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.lapak.lapak.api.ApiService;
import com.lapak.lapak.api.Service;
import com.lapak.lapak.pref.PrefManager;

public class MainActivity extends AppCompatActivity {

    ProgressDialog pDialog;
    IntentIntegrator intentIntegrator;

    CardView btnScan, btnAbout, btnPetunjuk, btnTentang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApiService API;

        btnScan = findViewById(R.id.btnScan);
        btnAbout = findViewById(R.id.btnAbout);
        btnPetunjuk = findViewById(R.id.btnPetunjuk);
        btnTentang = findViewById(R.id.btnTentang);

        API = Service.getAPIService();

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
                //Tentang Aplikasi
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //About

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


    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null){

            if (result.getContents() == null){
                // Toast.makeText(this, "TIDAK ADA DATA", Toast.LENGTH_SHORT).show();
            }else {

                try{


                    String kode = result.getContents();

                    Cek(kode);

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



    }


}
