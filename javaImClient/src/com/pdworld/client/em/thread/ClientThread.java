package com.pdworld.client.em.thread;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import com.pdworld.client.em.filetrans.TransFileStock;
import com.pdworld.client.em.filetrans.TransTask;
import com.pdworld.client.em.filetrans.trans.SendStreamThread;
import com.pdworld.client.em.pub.PubValue;
import com.pdworld.client.em.ui.chatui.ChatUI;
import com.pdworld.client.em.ui.mainui.MainUI;
import com.pdworld.pub.pack.PackOper;
import com.pdworld.pub.pub.PubToolkit;
import com.pdworld.pub.unit.Company;
import com.pdworld.pub.unit.Department;
import com.pdworld.pub.unit.Message;
import com.pdworld.pub.unit.MessagePack;
import com.pdworld.pub.unit.User;
import com.pdworld.pub.io.MyObjectInputStream;
import com.pdworld.pub.io.MyObjectOutputStream;

/**
 * 客户端消息线程类 
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class ClientThread extends Thread {

    private Socket client;

    private MyObjectInputStream myObjectIn;

    private MyObjectOutputStream myObjectOut;

    private boolean isStop = false;

    public ClientThread(Socket client) {
        this.client = client;
    }

    public void run() {
        //System.out.println("客户线程运行Running...");
        try {
            myObjectIn = new MyObjectInputStream(this.client.getInputStream());
            myObjectOut = new MyObjectOutputStream(this.client
                    .getOutputStream());
            Message message = null;
            while (!isStop) {
                message = myObjectIn.readMessage();

                if (message instanceof Company) {
                    if (message.getType().equals(PackOper.ADD_COMPANY)) {
                        MainUI.getInstance().getUserTree().setCompany(
                                (Company) message);
                    }
                } else if (message instanceof Department) {
                    Department department = (Department) message;
                    if (message.getType().equals(PackOper.ADD_DEPARTMENT)) {
                        MainUI.getInstance().getUserTree().addDepartment(
                                department);
                    } else if (message.getType().equals(
                            PackOper.UPDATE_DEPARTMENT)) {
                        MainUI.getInstance().getUserTree().updateDepartment(
                                department);
                    } else if (message.getType().equals(PackOper.DELETE_DEPARTMENT)){
                        MainUI.getInstance().getUserTree().deleteDepartment(department);
                    }
                } else if (message instanceof User) {
                    User user = (User) message;
                    if (message.getType().equals(PackOper.ADD_USER)) {
                        // 不进行添加自己信息在树中
                        // if
                        // (!user.getId().equals(MainUI.getInstance().getUser().getId())){
                        MainUI.getInstance().getUserTree().addUser(user);
                        // }
                    } else if (message.getType().equals(PackOper.UPDATE_USER)
                            || message.getType().equals(PackOper.LOGIN_SUCCEED)) {
                        MainUI.getInstance().getUserTree().updateUser(user);
                    } else if (message.getType()
                            .equals(PackOper.FORCEDOWN_LINE)) {
                        this.setStop();
                        this.client.close();
                        MainUI.getInstance().getUserTree().setAllDownLine();
                        PubToolkit
                                .showYes(MainUI.getInstance(), "你已经被服务器强制下线!");
                        return;
                    } else if (message.getType().equals(PackOper.DOWN_LINE)) {
                        MainUI.getInstance().getUserTree().updateUser(
                                (User) message);
                    } else if(message.getType().equals(PackOper.DELETE_USER)){
                        MainUI.getInstance().getUserTree().deleteUser(user);
                    }
                } else if (message instanceof MessagePack) {
                    MessagePack msgPack = (MessagePack) message;
                    // System.out.println("---------------receive
                    // Msg---------------");
                    // System.out.println("Type: "+msgPack.getType());
                    // System.out.println("from :"+msgPack.getFrom());
                    // System.out.println("to :"+msgPack.getTo());
                    // System.out.println("message: "+msgPack.getMessage());
                    // System.out.println("---------------receive
                    // over---------------");
                    // 如果是系统消息
                    if (msgPack.getType().equals(PackOper.MESSAGE)) {
                        MainUI.getInstance().addMessage(msgPack.getMessage());
                        // 如果是聊天消息
                    } else {
                        // 根据聊天对象,找出相应的聊天窗口
                        ChatUI chatUI = null;
                        // 首先找出是谁发的
                        Object object = null;
                        // System.out.println("object="+object+";chatUI="+chatUI);
                        if (msgPack.getType().equals(PackOper.CHAT_ALL)) {
                            object = MainUI.getInstance().getUserTree()
                                    .getCompany();
                            chatUI = PubValue.getChatUIForMsg(
                                    PackOper.CHAT_ALL, "");
                        } else if (msgPack.getType().equals(
                                PackOper.CHAT_DEPARTMENT)) {
                            object = MainUI.getInstance().getUserTree()
                                    .findDepartment(msgPack.getTo());
                            chatUI = PubValue.getChatUIForMsg(
                                    PackOper.CHAT_DEPARTMENT,
                                    ((Department) object).getId());
                            // System.out.println("object="+object+";chatUI="+chatUI);
                        } else if (msgPack.getType().equals(PackOper.CHAT_USER)) {
                            object = MainUI.getInstance().getUserTree()
                                    .findUser(msgPack.getFrom());
                            chatUI = PubValue
                                    .getChatUIForMsg(PackOper.CHAT_USER,
                                            ((User) object).getId());
                        } else if (message.getType().equals(
                                PackOper.SERVERCLOSE)) {
                            this.setStop();
                            this.client.close();
                            MainUI.getInstance().getUserTree().setAllDownLine();
                            PubToolkit.showYes(MainUI.getInstance(), "服务器关闭，您被强迫下线！");
                            return;
                        } else if (message.getType().equals(PackOper.UPPASSWORD_DEFEATED)) {
                            PubToolkit.showInformation("对不起,原密码输入不正确!");
                            continue;
                        } else if (message.getType().equals(PackOper.UPPASSWORD_NEW_NULL)) {
                            PubToolkit.showInformation("对不起,新密码不能为空");
                            continue;
                        } else if (message.getType().equals(PackOper.UPPASSWORD_SUCCEED)) {
                            PubToolkit.showInformation("恭喜,修改密码成功");
                            continue;
                        } else if(message.getType().equals(PackOper.UPFILE)
                                || message.getType().equals(PackOper.UPFILE_DEFUSE)
                                || message.getType().equals(PackOper.UPFILE_FIAT)
                                || message.getType().equals(PackOper.SEND_DEFUSE)){
                            fileTransDispose((MessagePack)message);
                        }
                        // 如果消息有显示的窗口的把它显示到最前面
                        if (chatUI == null && object != null) {
                            chatUI = new ChatUI(object);
                        }

                        if (chatUI != null) {
                            chatUI.addMessage(msgPack);
                            PubValue.addChatUI(chatUI);
                            chatUI.setVisible(true);
                            chatUI.toFront();
                        }
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            MainUI.getInstance().getUserTree().setAllDownLine();
            PubToolkit.showInformation("与服务器断开连接");
        } catch (IOException e) {
            MainUI.getInstance().getUserTree().setAllDownLine();
            PubToolkit.showInformation("与服务器断开连接");
        } finally {
            if (myObjectIn != null) {
                try {
                    myObjectIn.close();
                    myObjectIn = null;
                } catch (IOException e1) {
                }
            }
            if (myObjectOut != null) {
                try {
                    myObjectOut.close();
                    myObjectOut = null;
                } catch (IOException e1) {
                }
            }
            if (this.client != null) {
                try {
                    this.client.close();
                } catch (IOException e) {
                }
            }
        }

    }
    /**
     * 处理文件传送
     * @param msgPack
     */
    public void fileTransDispose(MessagePack msgPack){
        if(msgPack.getType().equals(PackOper.UPFILE)){
            System.out.println("收到传送文件请求");
            //找出窗口
            ChatUI chatUI = PubValue.getChatUIForId(msgPack.getFrom());
            if(chatUI == null){
                Object object = MainUI.getInstance().getUserTree().findUser(msgPack.getFrom());
                chatUI = new ChatUI(object);
                PubValue.addChatUI(chatUI);
            }
//			添加任务
            TransFileStock.CreateNewReceiveTrans(chatUI, msgPack.getMessage());
            chatUI.setVisible(true);
            chatUI.toFront();
        }else if(msgPack.getType().equals(PackOper.UPFILE_FIAT)){
            //许可传送
            System.out.println("许可传送文件");
            ChatUI chatUI = PubValue.getChatUIForId(msgPack.getFrom());
            if(chatUI != null){
                System.out.println("寻找任务");
                TransTask transTask = TransFileStock.getSendFileUI(msgPack.getMessage());
                System.out.println("任务:"+transTask);
                if(transTask == null)
                    return;
                System.out.println("准备建立发送线程");
                SendStreamThread sendStreamThread = new SendStreamThread(msgPack.getFromIP(),msgPack.getFromPort(), transTask);
                sendStreamThread.start();
                return;
            }else
                TransFileStock.removeSendTrans(msgPack.getMessage());
        }else if(msgPack.getType().equals(PackOper.UPFILE_DEFUSE)){
            //不许可
            ChatUI chatUI = PubValue.getChatUIForId(msgPack.getFrom());
            if(chatUI != null){
                TransTask transTask = TransFileStock.getSendFileUI(msgPack.getMessage());
                if(transTask != null){
                    transTask.getFileUI().setClose();
                    TransFileStock.removeSendTrans(msgPack.getMessage());
                }
            }
        } else if(msgPack.getType().equals(PackOper.SEND_DEFUSE)){
            System.out.println("dddddddddddddddddd");
            ChatUI chatUI = PubValue.getChatUIForId(msgPack.getFrom());
            if(chatUI != null){
                TransTask transTask = TransFileStock.getReceiveFileUI(msgPack.getMessage());
                if(transTask != null){
                    transTask.getFileUI().setClose();
                    TransFileStock.removeSendTrans(msgPack.getMessage());
                }
            }
        }
    }


    /**
     * 发送消息
     * @param object
     * @return
     */
    public boolean sendMessage(Object object) {
        try {
            if (myObjectOut != null) {
                myObjectOut.writeMessage(object);
            }
        } catch (IOException e) {
            // e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 停止消息服务
     *
     */
    public void setStop() {
        isStop = true;
    }
}