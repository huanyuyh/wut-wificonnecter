package com.huanyu.whut_wificonnector;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.GZIPInputStream;



public class HttpRequest extends Thread{
    private String url;
    private String param;
    private String result;
    public boolean ifgzip = false;
    public HttpRequest(){
    }
    public HttpRequest(String url,String param){
        this.url = url;
        this.param = param;
    }
    public String getResult(){
        return result;
    }
    @Override
    public void run() {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("Accept-Encoding","gzip, deflate");
            conn.setRequestProperty("Accept-Language",
                    "zh-CN,zh;q=0.9,en-GB;q=0.8,en-US;q=0.7,en;q=0.6");
            conn.setRequestProperty("Cache-Control","no-cache");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Host","172.30.21.100");
            conn.setRequestProperty("Pragma","no-cache");
            conn.setRequestProperty("user-agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
            conn.setRequestProperty("X-Requested-With","XMLHttpRequest");

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        this.result = result;
    }
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *   发送请求的URL
     * @param param
     *   请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *   发送请求的 URL
     * @param param
     *   请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public InputStream sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        InputStream inputStream = null;
        byte[] data = new byte[1024];
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("Accept-Encoding","gzip, deflate");
            conn.setRequestProperty("Accept-Language",
                    "zh-CN,zh;q=0.9,en-GB;q=0.8,en-US;q=0.7,en;q=0.6");
            conn.setRequestProperty("Cache-Control","no-cache");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Host","172.30.21.100");
            conn.setRequestProperty("Pragma","no-cache");
            conn.setRequestProperty("user-agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
            conn.setRequestProperty("X-Requested-With","XMLHttpRequest");

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            //储存响应头
            Map<String, List<String>> map = conn.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
                if(key!=null){
                    if(key.indexOf("coding")>-1)ifgzip = true;
                    System.out.println(ifgzip);
                }

            }
            //返回inputStream
            inputStream = conn.getInputStream();
            // 定义BufferedReader输入流来读取URL的响应
//            in = new BufferedReader(
//                    new InputStreamReader(conn.getInputStream(),"utf-8"));
//            String line;
//            while ((line = in.readLine()) != null) {
//                result += line;
//            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return inputStream;
    }

    public String uncompressToString(InputStream inputStream, String encoding) {
        byte[] bytesin = new byte[1024];
        int len = 0;
        byte[] bytesout = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String result=null;
        try {
            while ((len = inputStream.read(bytesin)) != -1) {
                outputStream.write(bytesin, 0, len);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bytesout = outputStream.toByteArray();
        if (bytesout == null || bytesout.length == 0) {
            return null;
        }
        if(this.ifgzip){
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayInputStream in = new ByteArrayInputStream(bytesout);
            try {
                GZIPInputStream ungzip = new GZIPInputStream(in);
                byte[] buffer = new byte[256];
                int n;
                while ((n = ungzip.read(buffer)) >= 0) {
                    out.write(buffer, 0, n);
                }
                return out.toString(encoding);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            try {
                result = new String(outputStream.toByteArray(), encoding);
                return result;
            } catch (UnsupportedEncodingException ex) {
                throw new RuntimeException(ex);
            }
        }
        return null;

    }
    public String uncompressToString(InputStream inputStream) {
        return uncompressToString(inputStream, "UTF-8");
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void generateImage(String file,File imgfile) {
        // 解密
        try {
            // 项目绝对路径
            // 图片分类路径+图片名+图片后缀
            // 解密
            Base64.Decoder decoder = Base64.getMimeDecoder();
            file = file.substring(file.indexOf(",", 1) + 1, file.length());
            System.out.println(file);
            // 去掉base64前缀 data:image/jpeg;base64,
            file = file.substring(file.indexOf(",", 1) + 1, file.length());
            file = file.substring(0, file.indexOf(",", 1) - 2);
            System.out.println(file);
            byte[] b = decoder.decode(file);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            // 保存图片
            OutputStream out = new FileOutputStream(imgfile);
            out.write(b);
            out.flush();
            out.close();
            // 返回图片的相对路径 = 图片分类路径+图片名+图片后缀

        } catch (IOException e) {

        }
    }

}

