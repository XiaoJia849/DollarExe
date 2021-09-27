package com.example.dollarexe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {
    EditText editText;
    TextView textView;
    float dollarRate=6.8f;
    float euroRate=6.8f;
    float wonRate=6.8f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText=findViewById(R.id.input);
        textView=findViewById(R.id.output);
    }

    @Override
    public void onClick(View v) {
        String input=editText.getText().toString();

        if(input.equals("")){
            Toast.makeText(this,"Please fill the ...first",Toast.LENGTH_SHORT).show();
            editText.setText(R.string.hello);
        }else {
            if(v.getId()==R.id.dollar){
                float money=Float.valueOf(input);
                textView.setText(String.format("%.2f", money*dollarRate) +" Dollar");
            }
            if(v.getId()==R.id.euro){
                float money=Float.valueOf(input);
                textView.setText(String.format("%.2f", money*euroRate)+" EURO");

            }
            if(v.getId()==R.id.won){
                float money=Float.valueOf(input);
                textView.setText(String.format("%.2f", money*wonRate)+" WON");

            }

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.setting){
            Log.d("MainActivity","okkkkkkkkkkkkkkkkkk");

        }

        return super.onOptionsItemSelected(item);
    }

    public void open(View v) {
        if(v.getId()==R.id.jump){
            Intent intent=new Intent(this,FirstActivity.class);
            intent.putExtra("dollarRate",dollarRate);

//            Log.d("dollarRate Main  send:",String.valueOf(dollarRate));
            intent.putExtra("euroRate",euroRate);
            intent.putExtra("wonRate",wonRate);

//            setResult(1,intent);
            startActivityForResult(intent,1);
//            startActivity(intent);
//            这样可以选择打开的activity是新创建的还是已有的

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==2){
            dollarRate = data.getFloatExtra("dollarRate", 6.8f);
            euroRate = data.getFloatExtra("euroRate", 6.8f);
            wonRate = data.getFloatExtra("wonRate", 6.8f);

//        Log.d("dollarRate Main get :",String.valueOf(dollarRate));super.onActivityResult(requestCode, resultCode, data);

        }
        if(requestCode==1 && resultCode==3){
            Bundle bundle=data.getExtras();
            dollarRate = bundle.getFloat("dollarRate", 6.8f);
            euroRate = bundle.getFloat("euroRate", 6.8f);
            wonRate = bundle.getFloat("wonRate", 6.8f);

//        Log.d("dollarRate Main get :",String.valueOf(dollarRate));super.onActivityResult(requestCode, resultCode, data);

        }


    }

}