package com.example.wristband.bluetooth;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 接收消息的服务
 */
public class ReceiveSocketService {

    public static void receiveMessage(Handler handler) {
        if (BltAppliaction.bluetoothSocket == null || handler == null) return;
        try {
            InputStream inputStream = BltAppliaction.bluetoothSocket.getInputStream();
            // 从客户端获取信息
            BufferedReader bff = new BufferedReader(new InputStreamReader(inputStream));
            String json;

            while (true) {
                while ((json = bff.readLine()) != null) {
                    Message message = new Message();
                    message.obj = json;
                    message.what = 1;
                    //预定3为获取番茄数.那么如何设定为变量
                    message.what=3;
                    handler.sendMessage(message);
                    //判断读取到的内容，如果为 今日的番茄数为






        //说明接下来会接收到一个文件流
                    if ("file".equals(json)) {
                        FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory() + "/test.gif");
                        int length;
                        int fileSzie = 0;
                        byte[] b = new byte[1024];
                        // 2、把socket输入流写到文件输出流中去
                        while ((length = inputStream.read(b)) != -1) {
                            fos.write(b, 0, length);
                            fileSzie += length;
                            System.out.println("当前大小：" + fileSzie);
                            //这里通过先前传递过来的文件大小作为参照，因为该文件流不能自主停止，所以通过判断文件大小来跳出循环
                        }
                        fos.close();
                        message.obj = "文件:保存成功";
                        message.what = 2;
                        handler.sendMessage(message);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
