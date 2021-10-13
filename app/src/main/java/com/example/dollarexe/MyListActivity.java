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

public class MyListActivity extends ListActivity implements Runnable{

    List<String> list=new ArrayList<>();
    public static final String TAG="MyListActivity";
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
                    ListAdapter adapter=new ArrayAdapter<String>(MyListActivity.this, android.R.layout.simple_list_item_1,list);
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };

        Thread thread=new Thread(MyListActivity.this);
        thread.start();


//        使用MyTask的runnable
//        handler=new Handler(Looper.myLooper()){
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                Log.d("Main", "handleMessage: 收到消息");
//                if(msg.what==0){
//                    list= (List<String>) msg.obj;
//                    ListAdapter adapter=new ArrayAdapter<String>(MyListActivity.this, android.R.layout.simple_list_item_1,list);
//                    setListAdapter(adapter);
//
//
//                }
//                super.handleMessage(msg);
//            }
//        };
//
//        MyTask task=new MyTask();
//        task.setHandler(handler);
//        Thread thread1=new Thread(task);
//        thread1.start();


//这个是最简单的例子
//        for(int i=0;i<100;i++){
//            list.add("item"+i);
//        }
//        ListAdapter adapter=new ArrayAdapter<String>(MyListActivity.this, android.R.layout.simple_list_item_1,list);
//        setListAdapter(adapter);




//        这个是加强美化版本
//        不放在这里了


    }

//    @Override
//    public void run() {
//        org.jsoup.nodes.Document document = null;
//        try {
//            document= Jsoup.connect("http://www.usd-cny.com/").get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////        document= Jsoup.parse(html);
//        Elements elements= document.getElementsByTag("tr");
//        elements.remove(0);
////        elements.get() 定位获取element
//        for(Element element:elements){
//            Elements td= element.select("td");
//            list.add(td.get(0).text()+"-->"+td.get(4).text());
//            Log.d(TAG, "run: "+td.get(0).text()+td.get(4).text());
//        }
//        Message message=handler.obtainMessage(0);
//        handler.sendMessage(message);
//    }

    @Override
    public void run() {
        org.jsoup.nodes.Document document = null;
        try {
            document= Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        document= Jsoup.parse(html);
        Elements elements=document.getElementsByTag("table");
        Element table= elements.get(1);
        Elements trs=table.getElementsByTag("tr");
        trs.remove(0);
        //        elements.get() 定位获取element
        for(Element element:trs){
            Elements td= element.select("td");
            list.add(td.get(0).text()+"-->"+td.get(4).text());
            Log.d(TAG, "run: "+td.get(0).text()+td.get(4).text());
        }
        Message message=handler.obtainMessage(0);
        handler.sendMessage(message);
    }

}