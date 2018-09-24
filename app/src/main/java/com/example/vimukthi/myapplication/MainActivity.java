package com.example.vimukthi.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnDriver;
    private Button btnCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCustomer =(Button)findViewById(R.id.btnCustomer);
        btnDriver =(Button)findViewById(R.id.btnDriver);

        btnDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DriverActivity.class);
                startActivity(intent);
                finish();
                return;

            }
        });
    }
}
