package com.example.whut_wificonnector;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

public class MainActivity extends AppCompatActivity {
    private EditText EditUser,EditPass;
    //登录按钮
    private Button BtLogin;
    //用户名
    String name = null;
    //密码
    String pass = null;
    //储存POST信息
    String data = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditUser = findViewById(R.id.ed_user);
        EditPass = findViewById(R.id.ed_pass);
        BtLogin = findViewById(R.id.bt_login);
        setOnClick();
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
    public void setOnClick(){
        Onclick onClick = new Onclick();
        BtLogin.setOnClickListener(new Onclick());
    }
    private class Onclick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_login:
                    Toast.makeText(MainActivity.this,"我被点击了",Toast.LENGTH_SHORT).show();
                    String url = "http://172.30.21.100/api/account/login";
                    data = "username=" + name + "&password=" + pass + "&nasId=52";
                    new Thread(new Runnable() {

                        String data1 = data;
                            @Override
                            public void run() {
                            try {
                                HttpRequest h = new HttpRequest();
                                //向121.41.111.94/show发起POST请求，并传入name参数
                                String content = h.sendPost("http://172.30.21.100/api/account/login", data1);
                                System.out.println(content);
                                JSONObject jsonObject = JSONObject.parseObject(content);
                                //        String jsonObject = JSON.toJSONString(content);
                                System.out.println(jsonObject);
                                System.out.println(jsonObject.getString("msg"));

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