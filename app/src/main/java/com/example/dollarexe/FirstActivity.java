package com.example.dollarexe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener {
    float dollarRate;
    float euroRate;
    float wonRate;
    EditText dollar;
    EditText euro;
    EditText won;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        dollar=findViewById(R.id.dollarRate);
        euro=findViewById(R.id.euroRate);
        won=findViewById(R.id.wonRate);

        Intent intent =getIntent();
        dollarRate=intent.getFloatExtra("dollarRate",6.8f);
//        Log.d("dollarRate first get:",String.valueOf(dollarRate));
        euroRate=intent.getFloatExtra("euroRate",6.8f);
        wonRate=intent.getFloatExtra("wonRate",6.8f);

        dollar.setText(String.valueOf(dollarRate));
        euro.setText(String.valueOf(euroRate));
        won.setText(String.valueOf(wonRate));




    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.save){
            Intent intent =getIntent();
            dollarRate=Float.valueOf(dollar.getText().toString());
            euroRate=Float.valueOf(euro.getText().toString());
            wonRate=Float.valueOf(won.getText().toString());
            intent.putExtra("dollarRate",dollarRate);
            Log.d("dollarRate  first send:",String.valueOf(dollarRate));
            intent.putExtra("euroRate",euroRate);
            intent.putExtra("wonRate",wonRate);
            startActivity(intent);
            setResult(2,intent);



//            返回类型为budle
//            dollarRate=Float.valueOf(dollar.getText().toString());
//            euroRate=Float.valueOf(euro.getText().toString());
//            wonRate=Float.valueOf(won.getText().toString());
//            Bundle bundle=new Bundle();
//            bundle.putFloat("dollarRate",dollarRate);
////            Log.d("dollarRate  first send:",String.valueOf(dollarRate));
//            bundle.putFloat("euroRate",euroRate);
//            bundle.putFloat("wonRate",wonRate);
//            intent.putExtras(bundle);
//
//            SharedPreferences sp= getSharedPreferences("myRate", Activity.MODE_PRIVATE);
//            SharedPreferences.Editor editor=  sp.edit();
//            editor.putFloat("dollarRate",dollarRate);
//            editor.putFloat("euroRate",euroRate);
//            editor.putFloat("wonRate",wonRate);
////            Log.d("FirstActivity","saved");
////            editor.apply();
//
////            startActivity(intent);
//            setResult(3,intent);
            finish();
        }
    }

}