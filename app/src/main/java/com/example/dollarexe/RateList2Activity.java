package com.example.dollarexe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RateList2Activity extends AppCompatActivity implements Runnable, AdapterView.OnItemClickListener, View.OnLongClickListener {
    public static final String TAG="RateList2Activity";
    List<HashMap<String,String>> list=new ArrayList<>();
    ListView listView;
    Handler handler;
    ProgressBar progressBar;
    private MyDatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_list2);

        listView=findViewById(R.id.listview);
//        progressBar=findViewById(R.id.progressbar);


        databaseHelper=new MyDatabaseHelper(this,"Country.db",null,4);
        db=databaseHelper.getWritableDatabase();


//        使用本例的runnable
        handler=new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.d("Main", "handleMessage: 收到消息");
                if(msg.what==0){
//                    ListAdapter adapter=new SimpleAdapter(RateList2Activity.this,list,R.layout.item_listview,new String[]{"name","value"},new int[]{R.id.item_name,R.id.item_detail});
//                    listView.setAdapter(adapter);
                    Cursor cursor=db.query("Country",null,null,null,null,null,null);
                    if(cursor.moveToFirst()){
                        do{
                            HashMap<String,String> hashMap=new HashMap<>();
                            hashMap.put("name", cursor.getString(cursor.getColumnIndex("name")));
                            hashMap.put("value",cursor.getString(cursor.getColumnIndex("value")));
                            list.add(hashMap);
                        }while (cursor.moveToNext());
                    }
                    cursor.close();

                    MyAdapter adapter=new MyAdapter(RateList2Activity.this,R.layout.item_listview,list);
                    listView.setAdapter(adapter);
                    listView.setEmptyView(findViewById(R.id.nodata));
                    listView.setOnItemClickListener(RateList2Activity.this);
                    listView.setOnLongClickListener(RateList2Activity.this);

//                    progressBar.setVisibility(View.GONE);
                }
                super.handleMessage(msg);
            }
        };

        Thread thread=new Thread(RateList2Activity.this);
        thread.start();


    }

    @Override
    public void run() {
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

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

            ContentValues values=new ContentValues();
            values.put("name",td.get(0).text());
            values.put("value",td.get(4).text());
            db.insert("Country",null,values);


//            HashMap<String,String> hashMap=new HashMap<>();
//            hashMap.put("name",td.get(0).text());
//            hashMap.put("value",td.get(4).text());
//            list.add(hashMap);
        }
        Message message=handler.obtainMessage(0);
        handler.sendMessage(message);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object item= listView.getItemAtPosition(position);
        HashMap<String,String> hashMap= (HashMap<String, String>) item;
        String name=hashMap.get("name");
        String value=hashMap.get("value");
        Intent intent=new Intent(RateList2Activity.this,ShowActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("value",value);
        startActivity(intent);
    }

    @Override
    public boolean onLongClick(View v) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Warnning").setMessage("go on ?").setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(RateList2Activity.this," a joke",Toast.LENGTH_LONG).show();
            }
        }).setNegativeButton("no",null);

        builder.create().show();
//        返回false  执行longClick之后会执行Itemclick
//        返回true   只执行longClick
        return true;
    }
}