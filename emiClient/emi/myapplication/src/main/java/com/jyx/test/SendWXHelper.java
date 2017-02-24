package com.jyx.test;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;
import com.tencent.mm.sdk.openapi.WXWebpageObject;

import java.io.File;
import java.net.URL;

/**
 * Created by Administrator on 2016/5/29.
 */
public class SendWXHelper {

    private static final String APP_ID ="wxaf761bd546df6568";
    private IWXAPI api;

    public SendWXHelper(Context context) {
        api = WXAPIFactory.createWXAPI(context,APP_ID);
        api.registerApp(APP_ID);

    }
    /**
     * 启动微信
     */
    public void startWX() {
        api.openWXApp();
    }

    /**
     *
     * @param text 发送文本
     * @param scene 选择发送至好友或者朋友圈 0：好友 1：朋友圈
     */
    public void sendText(String text,int scene)
    {
        //第1步：创建一个用于封装待分享文本deWXTextObject对象
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;
        //第2步：创建WXMediaMessage对象，该对象用于Android客户端向微信发送朋友圈
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = text;
        //第3步：创建一个用于请求微信客户端的sendMessageToWX.req对象
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        //设置请求的唯一标识
        buildTransaction("text");
        //表示发送给朋友还是朋友圈
        req.scene = scene;

        //第4步：发送给微信客户端
        api.sendReq(req);

    }
    /**
     *
     * @param resources
     * @param resId 图像资源id
     */
    public void sendImg(Resources resources,int resId,int scene) {
        //第1步：获取二进制图像的Bitmap对象
        Bitmap bitmap = BitmapFactory.decodeResource(resources, resId);
        //第2步:创建WXImageObject对象，并包装bitmap
        WXImageObject imgObj = new WXImageObject(bitmap);
        //第3步：创建WXMediaMessage对象，并包装WXImageObject对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        //第4步：压缩图像
        Bitmap thummbBmp = Bitmap.createScaledBitmap(bitmap, 120, 150, true);
        //释放图像所占用的资源
        bitmap.recycle();
        msg.thumbData = BitmapUtils.bmpToByteArray(thummbBmp, true);//设置缩略图
        //第5步：创建SendMessageTo.Req对象，用于发送数据
        SendMessageToWX.Req req= new SendMessageToWX.Req();
        req.transaction=buildTransaction("img");
        req.message=msg;
        req.scene=scene;//是否发送到朋友圈
        api.sendReq(req);

    }

    /**
     * 发送本地图像
     * @param path 图像路径
     * @param scene 是否发送至朋友圈
     * @return 发送结果
     */
    public boolean sendLocalImg(String path,int scene)
    {
        //第1步：判断图像文件是否存在
        File file=new File(path);
        if(!file.exists())
        {
            return false;
        }
        //第2步:创建WXImageObject对象，并包装bitmap
        WXImageObject imgObj = new WXImageObject();
        //设置图像路径
        imgObj.setImagePath(path);
        //第3步：创建WXMediaMessage对象，并包装WXImageObject对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        //第4步：压缩图像
        Bitmap bitmap=BitmapFactory.decodeFile(path);
        Bitmap thummbBmp = Bitmap.createScaledBitmap(bitmap, 120, 150, true);
        //释放图像所占用的资源
        bitmap.recycle();
        msg.thumbData =BitmapUtils.bmpToByteArray(thummbBmp, true);//设置缩略图
        //第5步：创建SendMessageTo.Req对象，用于发送数据
        SendMessageToWX.Req req= new SendMessageToWX.Req();
        req.transaction=buildTransaction("img");
        req.message=msg;
        req.scene=scene;//是否发送到朋友圈
        return api.sendReq(req);
    }

    /**
     * 发送url图像
     * @param url 图像url地址
     * @param scene 是否发送至朋友圈
     */
    public void sendUrlImg(final String url, final int scene,String title)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {

                //第1步:创建WXImageObject对象，并包装bitmap
                WXImageObject imgObj = new WXImageObject();
                imgObj.imageUrl=url;
                //第2步：创建WXMediaMessage对象，并包装WXImageObject对象
                WXMediaMessage msg = new WXMediaMessage();
                msg.mediaObject = imgObj;
                //第3步：压缩图像
                Bitmap bitmap= null;

                try {
                    bitmap = BitmapFactory.decodeStream(new URL(url).openStream());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Bitmap thummbBmp = Bitmap.createScaledBitmap(bitmap, 120, 150, true);
                //释放图像所占用的资源
                bitmap.recycle();
                msg.thumbData =BitmapUtils.bmpToByteArray(thummbBmp,true);//设置缩略图
                //第5步：创建SendMessageTo.Req对象，用于发送数据
                SendMessageToWX.Req req= new SendMessageToWX.Req();
                req.transaction=buildTransaction("img");
                req.message=msg;
                req.scene=scene;//是否发送到朋友圈
                api.sendReq(req);
            }
        }).start();
    }
    /**
     * 发送url
     * @param url
     * @param resources
     * @param resId
     * @param title 标题
     * @param description 描述
     */
    public void sendUrl(String url,Resources resources,int resId,String title,String description,int scene)
    {
        //第1步:创建一个WXWebPageObjext对象，用于封装要发送的url
        WXWebpageObject webpage=new WXWebpageObject();
        webpage.webpageUrl=url;
        //第2步：创建一个WXMediaMessage对象
        WXMediaMessage msg=new WXMediaMessage(webpage);
        msg.title=title;
        msg.description=description;
        //第3步：设置缩略图
        Bitmap thumb=BitmapFactory.decodeResource(resources,resId);
        msg.thumbData=BitmapUtils.bmpToByteArray(thumb, true);
        //第4步：创建SendMessageToWX.Req对象
        SendMessageToWX.Req req= new SendMessageToWX.Req();
        req.transaction=buildTransaction("webpage");
        req.message=msg;
        req.scene=scene;
        api.sendReq(req);
    }
    
    //为请求生成一个唯一的标识
    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

}
