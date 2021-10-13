package com.example.dollarexe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class RateListActivity extends AppCompatActivity {
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_list);

        ListView my_list=findViewById(R.id.my_list);

//        handler=new Handler(Looper.myLooper()){
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                Log.d("Main", "handleMessage: 收到消息");
//                if(msg.what==0){
//                    ListAdapter adapter=new ArrayAdapter<String>(MyListActivity.this, android.R.layout.simple_list_item_1,list);
//                    setListAdapter(adapter);
//                }
//                super.handleMessage(msg);
//            }
//        };

//        Thread thread=new Thread(MyListActivity.this);
//        thread.start();

        MainActivity mainActivity=new MainActivity();
        mainActivity.setHandler(handler);
        Thread thread=new Thread();
        thread.start();

    }


}