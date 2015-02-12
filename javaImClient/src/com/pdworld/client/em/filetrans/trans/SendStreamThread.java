package com.pdworld.client.em.filetrans.trans;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.pdworld.client.em.filetrans.TransFileStock;
import com.pdworld.client.em.filetrans.TransTask;
import com.pdworld.client.em.filetrans.ui.FileUIConfig;
import com.pdworld.pub.io.MyObjectInputStream;
import com.pdworld.pub.io.MyObjectOutputStream;
import com.pdworld.pub.pack.PackOper;
import com.pdworld.pub.unit.MessagePack;

/**
 * 发送文件
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class SendStreamThread extends Thread {

    /**
     * 接受文件服务器的IP
     */
    private String receiveIP;

    /**
     * 接受文件服务器的端口
     */
    private int port;

    /**
     * 文件传送任务
     */
    private TransTask transTask;

    private Socket socket;

    public SendStreamThread(
            String receiveIP,
            int port,
            TransTask transTask) {
        this.transTask = transTask;
        this.receiveIP = receiveIP;
        this.port = port;

    }

    public void run() {
        System.out.println("建立发送线程");
        MyObjectInputStream myObjectIn = null;
        MyObjectOutputStream myObjectOut = null;
        BufferedOutputStream bufOut = null;
        BufferedInputStream bufIn = null;
        FileInputStream fileIn = null;
        try {
            // 连接
            System.out.println(receiveIP +"/"+port);
            socket = new Socket(receiveIP, port);
            myObjectOut = new MyObjectOutputStream(socket.getOutputStream());
            myObjectIn = new MyObjectInputStream(socket.getInputStream());
            System.out.println("发送线程连接成功");
            // 发送任务包
            MessagePack msgPack = new MessagePack();
            msgPack.setType(PackOper.UPFILE);

            // 包内容:发送ID+对方ID+文件名
            msgPack.setMessage(transTask.toString());
            myObjectOut.writeMessage(msgPack);
            System.out.println("发送线程:发送任务包");
            // 收确认包
            System.out.println("发送线程:等待确认包");
            Object object = myObjectIn.readMessage();
            System.out.println("发送线程:收到确认包");
            if (!(object instanceof MessagePack)) {
                transTask.getFileUI().setTransFail(FileUIConfig.EXCEPTIONERROR);
                return;
            }
            //
            msgPack = (MessagePack) object;
            if (msgPack.getType().equals(PackOper.UPFILE_DEFUSE)) {
                transTask.getFileUI().setTransFail(FileUIConfig.RECEIVEDEFUSE);
                return;
            }
            System.out.println("开始发送");
            // 开始发送
            fileIn = new FileInputStream(transTask.getSendFile());
            bufIn = new BufferedInputStream(fileIn);
            bufOut = new BufferedOutputStream(socket.getOutputStream());

            byte[] sendBuf = new byte[2048];
            int len = -1;
            int sendSize = 0;
            while ((len = bufIn.read(sendBuf, 0, sendBuf.length)) >= 0 && !transTask.getFileUI().isCancel()) {
                bufOut.write(sendBuf, 0, len);
                sendSize += len;
                transTask.getFileUI().setPlan(sendSize);
            }
            bufOut.flush();
            if(transTask.getFileUI().isCancel())
                transTask.getFileUI().setClose();
            else
                transTask.getFileUI().setTransFinish();
            TransFileStock.removeSendTrans(transTask.toString());

            System.out.println("传送文件名:"+transTask.getSendFile()+"传送数据大小:"+transTask.getFileLength());
        } catch (UnknownHostException e) {
            // e.printStackTrace();
            transTask.getFileUI().setTransFail(FileUIConfig.EXCEPTIONERROR);

        } catch (IOException e) {
            // e.printStackTrace();
            transTask.getFileUI().setTransFail(FileUIConfig.EXCEPTIONERROR);

        } finally {
            if (fileIn != null) {
                try {
                    fileIn.close();
                } catch (IOException e) {
                    // TODO 自动生成 catch 块
                    e.printStackTrace();
                }
            }
            if(bufIn != null){
                try {
                    bufIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bufOut != null){
                try {
                    bufOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(myObjectIn != null){
                try {
                    myObjectIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(myObjectOut != null){
                try {
                    myObjectOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
