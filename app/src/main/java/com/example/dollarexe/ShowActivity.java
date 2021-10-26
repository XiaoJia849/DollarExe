package com.example.dollarexe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ShowActivity extends AppCompatActivity  {
    TextView textView_name,textView_value_rate;
    EditText editText;
    String value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        value=intent.getStringExtra("value");


        textView_name=findViewById(R.id.name_country);
        editText=findViewById(R.id.edittext);
        textView_value_rate=findViewById(R.id.show);

        textView_name.setText(name);

        final String[] input = new String[1];
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                input[0] =s.toString();
                if(!s.toString().equals("")){
                    textView_value_rate.setText(""+Float.valueOf(input[0])*100/Float.valueOf(value));
                }else {
                    textView_value_rate.setText("");

                }
            }
            @Override
            public void afterTextChanged(Editable s) {
//               最好把上面的部分写道这里
//                String str=s.toString();


            }
        });

    }

}