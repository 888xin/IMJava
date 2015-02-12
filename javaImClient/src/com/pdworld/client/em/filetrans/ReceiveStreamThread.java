package com.pdworld.client.em.filetrans;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import com.pdworld.pub.io.MyObjectInputStream;
import com.pdworld.pub.io.MyObjectOutputStream;
import com.pdworld.pub.pack.PackOper;
import com.pdworld.pub.unit.MessagePack;


public class ReceiveStreamThread extends Thread {

    private Socket socket;

    private TransTask transTask;

    public ReceiveStreamThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        BufferedInputStream bufInStream = null;
        BufferedOutputStream bufOutStream = null;
        MyObjectInputStream myObjectIn = null;
        MyObjectOutputStream myObjectOut = null;

        try {
            myObjectIn = new MyObjectInputStream(socket.getInputStream());
            myObjectOut = new MyObjectOutputStream(socket.getOutputStream());
            MessagePack msgPack = (MessagePack) myObjectIn.readMessage();
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
            myObjectOut.writeMessage(msgPack);

            bufInStream = new BufferedInputStream(socket.getInputStream());
            bufOutStream = new BufferedOutputStream(new FileOutputStream(
                    transTask.getSendFile()));
            byte[] bufByte = new byte[1024];
            int readLen = -1;
            int readSum = 0;
            while ((readLen = bufInStream.read(bufByte, 0, bufByte.length)) > 0 && !transTask.getFileUI().isCancel()) {
                bufOutStream.write(bufByte, 0, readLen);
                readSum += readLen;
                transTask.getFileUI().setPlan(readSum);
            }
            bufOutStream.flush();

            if(readSum < transTask.getFileLength()){
                transTask.getFileUI().setClose();
            }else{
                //传送完毕
                transTask.getFileUI().setTransFinish();
            }
            TransFileStock.removeReceiveTrans(transTask.toString());
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
