package com.pdworld.client.em.filetrans;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.pdworld.client.em.filetrans.ui.FileUIInterface;
import com.pdworld.pub.io.MyObjectInputStream;
import com.pdworld.pub.io.MyObjectOutputStream;
import com.pdworld.pub.pack.PackOper;
import com.pdworld.pub.unit.MessagePack;



public class SendStreamThread extends Thread {

    private String receiveIP;

    private int port;

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
        MyObjectInputStream myObjectIn = null;
        MyObjectOutputStream myObjectOut = null;
        BufferedOutputStream bufOut = null;
        BufferedInputStream bufIn = null;
        FileInputStream fileIn = null;
        try {
            // 连接
            socket = new Socket(receiveIP, port);
            myObjectOut = new MyObjectOutputStream(socket.getOutputStream());
            myObjectIn = new MyObjectInputStream(socket.getInputStream());
            // 发送任务包
            MessagePack msgPack = new MessagePack();
            msgPack.setType(PackOper.UPFILE);
            // 包内容:发送ID+对方ID+文件名
            msgPack.setMessage(transTask.toString());
            myObjectOut.writeMessage(msgPack);
            // 收确认包
            Object object = myObjectIn.readMessage();
            if (!(object instanceof MessagePack)) {
                transTask.getFileUI().setTransFail(FileUIInterface.EXCEPTIONERROR);
                return;
            }
            msgPack = (MessagePack) object;
            if (msgPack.getType().equals(PackOper.UPFILE_DEFUSE)) {
                transTask.getFileUI().setTransFail(FileUIInterface.RECEIVEDEFUSE);
                return;
            }
            // 开始发送
            fileIn = new FileInputStream(transTask.getSendFile());
            bufIn = new BufferedInputStream(fileIn);
            bufOut = new BufferedOutputStream(socket.getOutputStream());

            byte[] sendBuf = new byte[1024];
            int len = -1;
            int sendSize = 0;
            while ((len = bufIn.read(sendBuf, 0, sendBuf.length)) >= 0 && !transTask.getFileUI().isCancel()) {
                bufOut.write(sendBuf, 0, len);
                sendSize += len;
                transTask.getFileUI().setPlan(sendSize);
            }
            bufOut.flush();
            transTask.getFileUI().setTransFinish();
            TransFileStock.removeSendTrans(transTask.toString());

            System.out.println("传送文件名:"+transTask.getSendFile()+"传送数据大小:"+transTask.getFileLength());
        } catch (UnknownHostException e) {
            // e.printStackTrace();
            transTask.getFileUI().setTransFail(FileUIInterface.EXCEPTIONERROR);

        } catch (IOException e) {
            // e.printStackTrace();
            transTask.getFileUI().setTransFail(FileUIInterface.EXCEPTIONERROR);

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
