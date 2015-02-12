package com.pdworld.client.em.filetrans.trans;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import com.pdworld.client.em.filetrans.TransFileStock;
import com.pdworld.client.em.filetrans.TransTask;
import com.pdworld.client.em.filetrans.ui.FileUIConfig;
import com.pdworld.pub.io.MyObjectInputStream;
import com.pdworld.pub.io.MyObjectOutputStream;
import com.pdworld.pub.pack.PackOper;
import com.pdworld.pub.unit.MessagePack;

/**
 * 接受文件传送
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class ReceiveStreamThread extends Thread {

    private Socket socket;

    /**
     * 接受文件任务
     */
    private TransTask transTask;

    public ReceiveStreamThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        System.out.println("-------------------------start---------------------------");
        System.out.println("建立接收线程");

        BufferedInputStream bufInStream = null;
        BufferedOutputStream bufOutStream = null;
        MyObjectInputStream myObjectIn = null;
        MyObjectOutputStream myObjectOut = null;

        try {
            myObjectIn = new MyObjectInputStream(socket.getInputStream());
            myObjectOut = new MyObjectOutputStream(socket.getOutputStream());
            MessagePack msgPack = (MessagePack) myObjectIn.readMessage();
            System.out.println("接收线程:收到一个任务包");
            if (!msgPack.getType().equals(PackOper.UPFILE)) {
                socket.close();
                return;
            }
            // 寻找是否有此任务
            this.transTask = TransFileStock.getReceiveFileUI(msgPack.getMessage());
            if (transTask == null) {
                socket.close();
                return;
            }
            msgPack.setType(PackOper.UPFILE_FIAT);
            System.out.println("接收线程:发送确认包");
            myObjectOut.writeMessage(msgPack);

            bufInStream = new BufferedInputStream(socket.getInputStream());

            System.out.println("接收线程准备接收");
            bufOutStream = new BufferedOutputStream(new FileOutputStream(
                    transTask.getReceiveFile()));
            System.out.println("接收线程:准备保存到"+transTask.getReceiveFile());
            byte[] bufByte = new byte[2048];
            int readLen = -1;
            int readSum = 0;
            System.out.println("接收线程:开始收数据");

            transTask.getFileUI().setStart();
            while ((readLen = bufInStream.read(bufByte, 0, bufByte.length)) > 0 && !transTask.getFileUI().isCancel()) {
                bufOutStream.write(bufByte, 0, readLen);
                readSum += readLen;
                transTask.getFileUI().setPlan(readSum);
            }
            bufOutStream.flush();
            if(transTask.getFileUI().isCancel()){
                //是取消的话
                transTask.getFileUI().setClose();
                return;
            }

            System.out.println("接收线程:收完数据");

            if(readSum < transTask.getFileLength()){
                //文件传送不完整
                transTask.getFileUI().setTransFail(FileUIConfig.FILETRANSBREAK);
                //这里可以做断点传记录
            }else{
                //传送完毕
                transTask.getFileUI().setTransFinish();
            }
            TransFileStock.removeReceiveTrans(transTask.toString());
            System.out.println("-----------------------over-----------------------------");
            System.out.println("=============================================================");
        } catch (UnsupportedEncodingException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        } catch (IOException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        } finally {
            if (bufInStream != null)
                try {
                    bufInStream.close();
                } catch (IOException e) {
                    // e.printStackTrace();
                }
            if (bufOutStream != null)
                try {
                    bufOutStream.close();
                } catch (IOException e) {
                    // e.printStackTrace();
                }
            if (myObjectIn != null)
                try {
                    myObjectIn.close();
                } catch (IOException e) {
                    // e.printStackTrace();
                }
            if (myObjectOut != null)
                try {
                    myObjectOut.close();
                } catch (IOException e) {
                    // e.printStackTrace();
                }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    //	e.printStackTrace();
                }
            }
        }

    }
}
