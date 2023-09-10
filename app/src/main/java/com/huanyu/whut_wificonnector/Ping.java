package com.huanyu.whut_wificonnector;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
/**
 * 向指定URL发送ping方法的请求
 * 该类继承Thread因为安卓不允许在主程序使用网络
 *timeout
 * 超时时间
 * ipAddress
 *   发送请求的URL
 * result
 *   true表示ping通
 * @return
 */
public class Ping extends Thread{
    public boolean result = false;
    public String ipAddress = null;
    public int timeout = 500;

    public Ping(String ipAddress){
        this.ipAddress = ipAddress;
    }
    public Ping(String ipAddress,boolean result,int timeout){
        this.ipAddress = ipAddress;
        this.result = result;
        this.timeout = timeout;
    }
    public Ping(String ipAddress,int timeout){
        this.ipAddress = ipAddress;
        this.timeout = timeout;
    }
    //线程体
    @Override
    public void run() {
        InetAddress geek = null;
        try {
            geek = InetAddress.getByName(ipAddress);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println("Sending Ping Request to " + ipAddress);
        try {
            if (geek.isReachable(timeout)) {
                System.out.println("Host is reachable");
                this.result = true;
            } else {
                System.out.println("Sorry ! We can't reach to this host");
                this.result = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}


