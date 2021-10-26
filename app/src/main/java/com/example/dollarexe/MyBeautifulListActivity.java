package com.example.dollarexe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyBeautifulListActivity extends ListActivity implements  Runnable{

    List<Country> list=new ArrayList<>();
    public static final String TAG="MyBeautifulListActivity";
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        使用本例的runnable
        handler=new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.d("Main", "handleMessage: 收到消息");
                if(msg.what==0){
                    ListAdapter adapter=new StrAdapter(MyBeautifulListActivity.this, R.layout.item_country,list);
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };

        Thread thread=new Thread(MyBeautifulListActivity.this);
        thread.start();

    }


    @Override
    public void run() {
        org.jsoup.nodes.Document document = null;
        try {
            document= Jsoup.connect("http://www.usd-cny.com/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        document= Jsoup.parse(html);
        Elements elements= document.getElementsByTag("tr");
        elements.remove(0);
//        elements.get() 定位获取element
        for(Element element:elements){
            Elements td= element.select("td");
            Country country=new Country();
            country.setName(td.get(0).text());
            country.setRate(td.get(4).text());




            list.add(country);
        }
        Message message=handler.obtainMessage(0);
        handler.sendMessage(message);
    }
}