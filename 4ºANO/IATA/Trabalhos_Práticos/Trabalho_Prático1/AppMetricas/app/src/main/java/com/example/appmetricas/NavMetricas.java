package com.example.appmetricas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class NavMetricas extends AppCompatActivity {

    ImageButton read_teclado;
    ImageButton read_rato;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_metricas);

        read_teclado = (ImageButton) findViewById(R.id.buttonKey);
        read_rato = (ImageButton) findViewById(R.id.buttonMouse);

        findViewById(R.id.buttonKey).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), KeyActivity.class));
            }
        });

        findViewById(R.id.buttonMouse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MouseActivity.class));
            }
        });
    }
}