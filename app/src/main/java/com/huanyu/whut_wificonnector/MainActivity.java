package com.huanyu.whut_wificonnector;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;


import java.io.File;
import java.io.InputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private EditText EditUser,EditPass,Editcode;
    //登录按钮
    private Button BtLogin;
    private CheckBox Cboxmemory;
    private ImageView imgcode;
    int nasId = 0;
    //用户名
    String name = null;
    //密码
    String pass = null;
    //储存POST信息
    String data = null;
    String code = null;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditUser = findViewById(R.id.ed_user);
        EditPass = findViewById(R.id.ed_pass);
        Editcode = findViewById(R.id.ed_code);
        BtLogin = findViewById(R.id.bt_login);
        preferences = getSharedPreferences("user",MODE_PRIVATE);
        Cboxmemory = findViewById(R.id.cbox_memory);
        imgcode = findViewById(R.id.img_code);
        reload();
        setOnClick();
        Random random = new Random();
        nasId = random.nextInt(999);
        Cboxmemory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("name",name);
                    editor.putString("pass",pass);
                    editor.putBoolean("ischecked",true);
                    editor.commit();
                    Toast.makeText(MainActivity.this,"已为您保存账号密码！",Toast.LENGTH_SHORT).show();
                }else {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.remove("name");
                    editor.remove("pass");
                    editor.remove("ischecked");
                    editor.commit();
                    Toast.makeText(MainActivity.this,"已放弃保存账号密码！",Toast.LENGTH_SHORT).show();
                }
            }
        });
        EditUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        EditPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pass = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Editcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                code = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //登录按钮的功能
//                        new Thread(new Runnable() {
//                            String data1 = data;
//
//                            @Override
//                            public void run() {
//                            try {
//                                HttpRequest h = new HttpRequest();
//                                //向121.41.111.94/show发起POST请求，并传入name参数
//                                String content = h.sendPost("http://172.30.21.100/api/account/login", data1);
//                                content.toString();
//                                System.out.println(content);
//                                JSONObject jsonObject = JSONObject.parseObject(content);
//                                //        String jsonObject = JSON.toJSONString(content);
//                                System.out.println(jsonObject);
//                                System.out.println(jsonObject.getString("msg"));
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                            }
//                        }).start();


//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
    }

    private void reload() {
        String name = preferences.getString("name",null);
        if(name!=null){
            EditUser.setText(name);
        }
        String pass = preferences.getString("pass",null);
        if(pass!=null){
            EditPass.setText(pass);
        }
        Boolean ischecked = preferences.getBoolean("ischecked",false);
        if(ischecked){
            Cboxmemory.setChecked(true);
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("iscode",false);
    }

    public void setOnClick(){
        Onclick onClick = new Onclick();
        BtLogin.setOnClickListener(new Onclick());
    }
    private class Onclick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_login:
                    preferences = getSharedPreferences("user",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    if(preferences.getBoolean("iscode",false)){
                        String captchaid = preferences.getString("captchaid",null);
                        data = "username=" + name + "&password=" + pass + "&captcha=" + code + "&captchaId=" + captchaid + "&nasId=" + nasId;
                    }else{
                        data = "username=" + name + "&password=" + pass + "&nasId=" + nasId;
                    }
                    Toast.makeText(MainActivity.this,"正在尝试链接...",Toast.LENGTH_SHORT).show();
                    String url = "http://172.30.21.100/api/account/login";

                    new Thread(new Runnable() {

                        File cacheFile = new File(getExternalCacheDir().toString(), "tmp.jpg");
                        String data1 = data;
                            @Override
                            public void run() {
                            try {
                                System.out.println(getExternalCacheDir());
                                HttpRequest h = new HttpRequest();
                                //向121.41.111.94/show发起POST请求，并传入name参数
                                InputStream inputStream = h.sendPost("http://172.30.21.100/api/account/login", data1);
                                String content = h.uncompressToString(inputStream);
                                content.substring(content.indexOf("auth", 1) + 1, content.length());

                                System.out.println(content);

                                JSONObject jsonObject = JSONObject.parseObject(content);
                                //        String jsonObject = JSON.toJSONString(content);
                                System.out.println(jsonObject);
                                System.out.println(jsonObject.getString("msg"));
                                if(content.indexOf("captchaId")>-1){
                                    String captchaId = content.substring(content.indexOf("captchaId")+12,content.indexOf("\",\""));
                                    System.out.println(captchaId);

                                    editor.putString("captchaid",captchaId);
                                    editor.putBoolean("iscode",true);
                                    editor.commit();
                                }

                                if(jsonObject.getString("msg").indexOf("成功")>-1){
                                    editor.putString("captchaid",null);
                                    editor.putBoolean("iscode",false);
                                    editor.commit();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(MainActivity.this,"连接成功！",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }else{
                                    runOnUiThread(new Runnable() {
                                        @RequiresApi(api = Build.VERSION_CODES.O)
                                        @Override
                                        public void run() {
                                            h.generateImage(content,cacheFile);

                                            imgcode.setImageURI(Uri.fromFile(cacheFile));
                                            Toast.makeText(MainActivity.this,"连接失败，可能需要输入验证码，可多次尝试来取消验证码",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            }

                        }).start();
                    break;
            }
        }
    }
}