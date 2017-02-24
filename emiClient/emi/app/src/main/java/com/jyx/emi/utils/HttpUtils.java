package com.jyx.emi.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Http请求的工具类
 * Created by Administrator on 2016/4/30.
 */
public class HttpUtils {

    private static final int TIMEOUT_IN_MILLIONS = 5000;
    public interface RequestCallBack
    {
        void onSuccess(String result);
    }
    public interface CallBackImg
    {
        void onRequestComplete(Bitmap bitmap);
    }
    /**
     * 异步的Get请求
     * @param urlStr
     * @param requestCallBack
     */
    public static void doGetAsyn(final String urlStr,final RequestCallBack requestCallBack)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result=doGet(urlStr);
                if(requestCallBack!=null)
                {
                    requestCallBack.onSuccess(result);
                }
            }
        }).start();
    }

    /**
     * 异步的GetImg请求
     * @param urlStr
     * @param callBackImg
     */
    public static void doGetImgAsyn(final String urlStr,final CallBackImg callBackImg)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap result=doGetImg(urlStr);
                if(callBackImg!=null)
                {
                    callBackImg.onRequestComplete(result);
                }
            }
        }).start();
    }
    /**
     * 异步的post请求
     * @param urlStr
     * @param requestCallBack
     * @throws Exception
     */
    public static void doPostAsyn(final String urlStr,final String params, final RequestCallBack requestCallBack)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = doPost(urlStr, params);
                if (requestCallBack != null)
                {
                    requestCallBack.onSuccess(result);
                }
            }
        }).start();

    }


    /**
     * Get请求，获得返回数据
     */
    public static String doGet(String urlStr)
    {
        URL url=null;
        HttpURLConnection conn = null;
        InputStream is=null;
        try {
            url = new URL(urlStr);
            conn=(HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);//设置读取超时时间
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);//设置连接超时时间
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            if(conn.getResponseCode()==200)
            {
                is=conn.getInputStream();
                return StreamUtils.getTextFromStream(is);
            } else
            {
                throw new RuntimeException(" responseCode is not 200 ... ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (is != null)
                    is.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            conn.disconnect();
        }
        return null;
    }

    /**
     * 向指定URL发送POST方法的请求
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception
     */
    public static String doPost(String url, String param)
    {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try
        {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);

            if (param != null && !param.trim().equals(""))
            {
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null)
            {
                result += line;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally
        {
            try
            {
                if (out != null)
                {
                    out.close();
                }
                if (in != null)
                {
                    in.close();
                }
            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Get请求，获得图片字节流
     */
    public static Bitmap doGetImg(String imgUrl)
    {
        URL url=null;
        HttpURLConnection conn = null;
        InputStream is=null;
        try {
            url = new URL(imgUrl);
            conn=(HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);//设置读取超时时间
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);//设置连接超时时间
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");

            conn.connect();
            int code=conn.getResponseCode();
            if(conn.getResponseCode()==200)
            {
                is=conn.getInputStream();
                return BitmapFactory.decodeStream(is);
            } else
            {
                throw new RuntimeException(" responseCode is not 200 ... ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (is != null)
                    is.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            conn.disconnect();
        }
        return null;
    }

}