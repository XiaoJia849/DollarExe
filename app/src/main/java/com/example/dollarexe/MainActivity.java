package com.example.dollarexe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener,Runnable {

    protected String TAG="MainActivity";
    EditText editText;
    TextView textView;
    float dollarRate;
    float euroRate;
    float wonRate;

    Handler handler;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editText=findViewById(R.id.input);
        textView=findViewById(R.id.output);


//        新版本SDK 需要looper对象
        handler=new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.d("Main", "handleMessage: 收到消息");
                if(msg.what==0){
                    Log.d("Main", "handleMessage: 0");
                    String me= (String) msg.obj;
                    Log.d("Main",me);
                    textView.setText(textView.getText().toString()+me);
                }
                super.handleMessage(msg);
            }
        };

//        执行对象是this
        Thread thread=new Thread(MainActivity.this);
//        自动调用run
        thread.start();

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
//        true表示调用菜单
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.setting){
            SharedPreferences sharedPreferences=getSharedPreferences("myRate", Activity.MODE_PRIVATE);
            dollarRate=sharedPreferences.getFloat("dollarRate",6.8f);
            euroRate=sharedPreferences.getFloat("euroRate",6.8f);
            wonRate=sharedPreferences.getFloat("wonRate",6.8f);
//            Log.d("MainActivity","okkkkkkkkkkkkkkkkkk");

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

    @Override
    public void run() {
//        Log.d("Main","run start");
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        URL url=null;
//        try {
//            url=new URL("http://www.usd-cny.com/");
//            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
//            InputStream inputStream=httpURLConnection.getInputStream();
//
//            String html=inputStream2String(inputStream);
//            Log.d("MainActivity",html);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


//        okhttp
//        OkHttpClient client=new OkHttpClient();
//        Request request=new Request.Builder().get().url("http://www.usd-cny.com/").addHeader("Content-Type", "text/html; charset=gb2312").build();
//
//        try {
//            Response response = client.newCall(request).execute();
//            if(response.isSuccessful() ){
//                String responseData = new String(response.body().bytes(), "gb2312");
////                String responseData=response.body().string();
////                Log.d("Main",responseData);
//                getDataByJsoup(responseData);
//            }else {
//                throw new IOException("Response  :    ---------------------- " + response);
//            }
////            Message message=handler.obtainMessage(0);
////            message.obj=responseData;
////            handler.sendMessage(message);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//        jsoup
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd ");
        Date date = new Date(System.currentTimeMillis());
        String dateSS=formatter.format(date);
        SharedPreferences sp= getSharedPreferences("myRate", Activity.MODE_PRIVATE);
        String dateS= sp.getString("Date","");
        if(!dateS.equals(dateSS)){
            org.jsoup.nodes.Document document = null;
            try {
                document=Jsoup.connect("http://www.usd-cny.com/").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
//        document= Jsoup.parse(html);
            Elements elements= document.getElementsByTag("tr");
            elements.remove(0);
//        elements.get() 定位获取element
            for(Element element:elements){
                Elements td= element.select("td");
                if("美元".equals(td.get(0).text())){
                    dollarRate=100/Float.valueOf(td.get(4).text());
                    Log.d("Main","dollarRate: "+td.get(4).text() );
                }else if("欧元".equals(td.get(0).text())){
                    euroRate=100/Float.valueOf(td.get(4).text());
                    Log.d("Main","euroRate: "+td.get(4).text() );

                }else if("韩币".equals(td.get(0).text())){
                    wonRate=100/Float.valueOf(td.get(4).text());
                    Log.d("Main","wonRate: "+td.get(4).text() );
                    //         这里因为线程和Activity在一个类内部，所以可以直接修改全局变量
//                但是更大的项目希望用bundle解决
                }
            }
            SharedPreferences.Editor editor=  sp.edit();
            editor.putFloat("dollarRate",dollarRate);
            editor.putFloat("euroRate",euroRate);
            editor.putFloat("wonRate",wonRate);
            editor.putString("Date",formatter.format(date));
            editor.apply();
        }




    }


//    jsoup解析string的html
    private void getDataByJsoup(String html){
        org.jsoup.nodes.Document document = null;
//        document=Jsoup.connect(url).get();
        document= Jsoup.parse(html);
        Elements elements= document.select("tr");
        for(Element element:elements){
            String td= element.select("td").text();
            String[] tds=td.split("\\s+");
            float avg=0.0f;
            float num=0.0f;
            for(String ss:tds){
                if(!ss.equals("-") && !ss.equals(tds[0])){
                    avg+=Float.valueOf(ss);
                    num++;
                }
            }
            avg/=num;
            if(num!=0){
                Log.d("Main",tds[0]+"平均值"+avg);




                Log.d("Main","_________________________");

            }


        }

    }








    private String inputStream2String(InputStream inputStream) throws Exception{
        final  int bufferSize=1024;
        final  char[] buffer=new char[bufferSize];
        final  StringBuilder out=new StringBuilder();
        Reader in=new InputStreamReader(inputStream,"gb2312");
        while (true){
            int rsz=in.read(buffer,0,buffer.length);
            if(rsz<0){
                break;
            }
            out.append(buffer,0,rsz);
        }
        return  out.toString();

    }
}