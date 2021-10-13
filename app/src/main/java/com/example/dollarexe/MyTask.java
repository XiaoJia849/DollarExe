package com.example.dollarexe;

import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.os.Handler;

public class MyTask implements Runnable{
    public static final String TAG="MyTask";
    Handler handler;
    List<String> list=new ArrayList<>();


    public void setHandler(Handler handler) {
        this.handler = handler;
    }


    @Override
    public void run() {
        Document document = null;
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
            list.add(td.get(0).text()+"-->"+td.get(4).text());
        }
        Message message=handler.obtainMessage(0);
        message.obj=list;
        handler.sendMessage(message);
    }

}
