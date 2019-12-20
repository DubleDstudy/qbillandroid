package com.example.qbillproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {
    private Button createQR;
    private Button lookupQR;
    private Button assetManagement;
    private Button lookupCashBill;
    String uno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Intent intent1 = getIntent(); /*데이터 수신*/
        uno = intent1.getExtras().getString("uno");

        createQR = (Button)findViewById(R.id.btn_createQR);
        createQR.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),CreateQR.class);
                intent.putExtra("uno",uno);
                startActivity(intent);
            }
        });
        lookupQR = (Button)findViewById(R.id.btn_lookupQR);
        lookupQR.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),CreateQR.class);
                intent.putExtra("uno",uno);
                startActivity(intent);
            }
        });
        assetManagement = (Button)findViewById(R.id.btn_assetManagement);
        assetManagement.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),AssetManagement.class);
                startActivity(intent);
            }
        });
        lookupCashBill = (Button)findViewById(R.id.btn_lookUpCashBill);
        lookupCashBill.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),LookupCashBill.class);
                startActivity(intent);
            }
        });
    }
}
