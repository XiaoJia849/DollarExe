package com.example.dollarexe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {
    EditText editText;
    TextView textView;


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

                textView.setText(String.format("%.2f", money/6.4662) +" Dollar");
            }
            if(v.getId()==R.id.euro){
                float money=Float.valueOf(input);
                textView.setText(String.format("%.2f", money/7.5743)+" EURO");

            }
            if(v.getId()==R.id.won){
                float money=Float.valueOf(input);
                textView.setText(String.format("%.2f", money*182.4343)+" WON");

            }


        }


    }
}