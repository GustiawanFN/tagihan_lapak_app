package com.lapak.lapak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HasilActivity extends AppCompatActivity {

    TextView status;
    String v_stts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);

        status = findViewById(R.id.status);

        //GET Intent Extra
        v_stts = getIntent().getStringExtra("message");

        //Binding intent extra

        status.setText(v_stts);

    }

    public void onBackPressed() {

        startActivity(new Intent(HasilActivity.this,MainActivity.class));

    }
}
