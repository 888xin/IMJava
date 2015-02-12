package com.pdworld.server.em.service;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;

import com.pdworld.pub.io.MyObjectInputStream;
import com.pdworld.pub.io.MyObjectOutputStream;
import com.pdworld.pub.pack.PackOper;
import com.pdworld.pub.pub.Parameter;
import com.pdworld.pub.unit.Message;
import com.pdworld.pub.unit.MessagePack;
import com.pdworld.pub.unit.User;
import com.pdworld.server.em.dao.factory.DepartmentDaoFactory;
import com.pdworld.server.em.dao.factory.UserDaoFactory;
import com.pdworld.server.em.pub.LogOper;
import com.pdworld.server.em.pub.PubValue;
import com.pdworld.server.em.ui.serverui.ServerUI;

/**
 * 与客户端进行消息交换的线程类
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class ClientThread extends Thread {

    /**
     * 相应的用户信息
     */
    private User user;

    /**
     * 与之相连的SOCKET
     */
    private Socket client;

    /**
     * 自定义的输入流
     */
    private MyObjectInputStream myObjectIn;

    /**
     * 自定义的输出流
     */
    private MyObjectOutputStream myObjectOut;

    /**
     * 是否停止线程运行
     */
    private boolean isStop = false;

    /**
     * 线程构造函数
     * @param client 与客户端相连的SOCKET
     */
    public ClientThread(Socket client) {
        this.client = client;
    }

    /**
     * 取得本线程的用户信息
     * @return
     */
    public User getUser() {
        return this.user;
    }

    /**
     * 发送信息与此线程相连的用户
     * @param message
     */
    public void sendMessage(Message message) {
        try {
            myObjectOut.writeMessage(message);
        } catch (IOException e) {
            // e.printStackTrace();
        }
    }

    /**
     * 停止线程运行
     *
     */
    public void setStop() {
        isStop = true;
    }

    /**
     * 与客户端交流
     */
    public void run() {
        System.out.println("服务器启动对客户的一个线程");
        try {
            myObjectIn = new MyObjectInputStream(client.getInputStream());
            myObjectOut = new MyObjectOutputStream(client.getOutputStream());
            Message message = null;
            while (!isStop) {
                message = myObjectIn.readMessage();
                System.out
                        .println("---------------------------------------------");
                System.out.println("收到客户端的一个包,类型:" + message.getType());
                if (this.user != null) {
                    messageDispose(message);
                } else {
                    login(message);
                }
                System.out
                        .println("---------------------------------------------");
            }
        } catch (UnsupportedEncodingException e) {
            // e.printStackTrace();
            setStop();
        } catch (IOException e) {
            // e.printStackTrace();
            setStop();
        } finally {
            if (myObjectIn != null) {
                try {
                    myObjectIn.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
            if (myObjectOut != null) {
                try {
                    myObjectOut.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
            if (this.user != null) {
                ServerUI.getInstance().getLogUI().addLog(
                        LogOper.getInstance().insertDownLineLog(this.user));
                UserDaoFactory.getUserDao().setOnline(this.user.getId(),
                        Parameter.NOTONLINED);

                ServerUI.getInstance().getOnLineUI().updateOnLine();
                PubValue.deleteUserThread(this.user.getId());

                this.user.setIsOnline(Parameter.NOTONLINED);
                this.user.setType(PackOper.DOWN_LINE);
                Iterator it = PubValue.getOnLineUserThread();
                while (it.hasNext()) {
                    ClientThread clientThread = (ClientThread) it.next();
                    clientThread.sendMessage(this.user);
                }
            }
        }
        //System.out.println("线程结束");
    }

    /**
     * 用来处理所有消息
     * @param message
     * @throws IOException
     */
    private void messageDispose(Message message) throws IOException {
        if (message.getType().equals(PackOper.CHAT_ALL)) {
            System.out.println("公司聊天,发给所有在线人");
            chatCompany(message);
        } else if (message.getType().equals(PackOper.CHAT_DEPARTMENT)) {
            System.out.println("部门聊天,发给此部门的所有人");
            chatDepartment(message);
        } else if (message.getType().equals(PackOper.CHAT_USER)) {
            System.out.println("私聊,发给具体的对方");
            chatUser(message);
        } else if (message.getType().equals(PackOper.DOWN_LINE)) {
            downLine(message);
        } else if (message.getType().equals(PackOper.UPPASSWORD)) {
            uppassword(message);
        } else if (message.getType().equals(PackOper.UPFILE)
                || message.getType().equals(PackOper.UPFILE_DEFUSE)
                || message.getType().equals(PackOper.UPFILE_FIAT)
                || message.getType().equals(PackOper.SEND_DEFUSE)) {

            chatUser(message);
        }
    }

    /**
     * 用户处理用户的密码修改
     * @param message
     * @throws IOException
     */
    private void uppassword(Message message) throws IOException {
        MessagePack msgPack = (MessagePack) message;
        if (!user.getPassword().equals(msgPack.getFrom())) {
            //与原密码不一样
            message.setType(PackOper.UPPASSWORD_DEFEATED);
            myObjectOut.writeMessage(message);

        } else if (msgPack.getTo().trim().equals("")) {
            //新密码为空
            message.setType(PackOper.UPPASSWORD_NEW_NULL);
            myObjectOut.writeMessage(message);
        } else {
            //达到要求,更新
            this.user.setPassword(msgPack.getTo());
            UserDaoFactory.getUserDao().resetPassword(this.user.getId(),
                    msgPack.getTo().trim());
            message.setType(PackOper.UPPASSWORD_SUCCEED);
            myObjectOut.writeMessage(message);
        }
    }

    /**
     * 公司聊天,发给所有在线人
     */
    private void chatCompany(Message message) {
        Iterator it = PubValue.getOnLineUserThread();
        String noSendId = null;
        if (message instanceof MessagePack) {
            noSendId = ((MessagePack) message).getFrom();
        }
        if (noSendId == null)
            noSendId = "";
        while (it.hasNext()) {
            ClientThread clientThread = (ClientThread) it.next();
            if (!clientThread.getUser().getId().equals(noSendId))
                clientThread.sendMessage(message);
        }
    }

    /**
     * 部门聊天,发给此部门的所有人
     */
    private void chatDepartment(Message message) {
        Iterator it = PubValue.getOnLineUserThread();
        if (!(message instanceof MessagePack))
            return;
        MessagePack msgPack = (MessagePack) message;
        while (it.hasNext()) {
            ClientThread clientThread = (ClientThread) it.next();
            if (clientThread.getUser().getDeptId().equals(msgPack.getTo())
                    && !(clientThread.getUser().getId().equals(msgPack
                    .getFrom()))) {
                clientThread.sendMessage(message);
            }
        }
    }

    /**
     * 私聊,发给具体的对方
     */
    private void chatUser(Message message) {
        MessagePack msgPack = (MessagePack) message;
        //		System.out
        //				.println("newgossssssssssssssssssssssssssssssssssssssssssssss");
        //		System.out.println("---------------server receive
        // Msg---------------");
        //		System.out.println("Type: " + msgPack.getType());
        //		System.out.println("from :" + msgPack.getFrom());
        //		System.out.println("to :" + msgPack.getTo());
        //		System.out.println("message: " + msgPack.getMessage());
        //		System.out.println("---------------server receive
        // over---------------");

        ClientThread clientThread = PubValue.getUserThread(msgPack.getTo());
        clientThread.sendMessage(msgPack);
    }

    /**
     * 用户下线操作
     * @param message
     * @throws IOException
     */
    private void downLine(Message message) throws IOException {
        // 下线包
        // 先把这个用户从在线列表中删除,再通知所有在线用户,
        // 先删除是为了防止,在通知所有在线用户时,造成时间过长,从而影响其它上线用户的真实信息
        // 下线对界面放在finally语句中,这样可以防止,中间异常而没有刷新在线列表
        // 还要在finally语句中,添加从存储列表删除的语句,这样做的原因也是为了防止导致.
        // 还要刷新数据库的在线状态
        this.setStop();
        this.client.close();
        //		PubValue.deleteUserThread(this.user.getId());
        //		Iterator it = PubValue.getOnLineUserThread();
        //		ClientThread clientThread;
        //		while (it.hasNext()) {
        //			clientThread = (ClientThread) it.next();
        //			clientThread.sendMessage(message);
        //		}
        //		UserDaoFactory.getUserDao().setOnline(this.user.getId(),
        // Parameter.NOTONLINED);
        //		ServerUI.getInstance().getOnLineUI().updateOnLine();
        //		ServerUI.getInstance().getLogUI().addLog(LogOper.getInstance().insertDownLineLog(this.user));
    }

    /**
     * 处理登录信息
     * @param message
     * @throws IOException
     */
    private void login(Message message) throws IOException {
        System.out.println(message.getClass());
        if (message.getType().equals(PackOper.LOGIN)) {
            User user = (User) message;
            System.out.println("登录ID:" + user.getId());
            List list = UserDaoFactory.getUserDao().selectId(user.getId());
            if (list.size() > 0) {
                User tempUser = (User) list.get(0);
                System.out.println("密码: 上传密码<" + user.getPassword()
                        + ">--,数据库密码:<" + tempUser.getPassword() + ">");
                if (tempUser.getPassword().equals(user.getPassword())) {
                    if (tempUser.getIsOnline() == Parameter.ONLINE) {
                        //	System.out.println(user.getId() + "判断已经在线了");
                        message.setType(PackOper.LOGIN_ONLINED);
                        myObjectOut.writeMessage(message);
                        this.setStop();
                        return;
                    }
                    ServerUI.getInstance().getLogUI().addLog(
                            LogOper.getInstance().insertOnLineLog(tempUser));
                    //登录成功后
                    //先通知所有人
                    //再通知此用户
                    //再把自己加到线程组中
                    //接着更新数据库
                    //下载树给此用户
                    tempUser.setType(PackOper.LOGIN_SUCCEED);
                    tempUser.setIsOnline(Parameter.ONLINE);
                    this.chatCompany(tempUser);

                    this.user = tempUser;
                    this.myObjectOut.writeMessage(tempUser);
                    PubValue.addUserThread(this.user.getId(), this);
                    UserDaoFactory.getUserDao().setOnline(tempUser.getId(),
                            Parameter.ONLINE);
                    ServerUI.getInstance().getOnLineUI().updateOnLine();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e1) {
                        // e1.printStackTrace();
                    }
                    // 登录成功后发送树
                    PubValue.company.setType(PackOper.ADD_COMPANY);
                    myObjectOut.writeMessage(PubValue.company);
                    list = DepartmentDaoFactory.getDepartmentDao().select(null);
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        message = (Message) it.next();
                        message.setType(PackOper.ADD_DEPARTMENT);
                        myObjectOut.writeMessage(message);
                    }

                    list = UserDaoFactory.getUserDao().select(null);
                    it = list.iterator();
                    while (it.hasNext()) {
                        message = (Message) it.next();
                        message.setType(PackOper.ADD_USER);
                        myObjectOut.writeMessage(message);
                    }
                    return;
                }
            }
            message.setType(PackOper.LOGIN_DEFEATED);
            myObjectOut.writeMessage(message);
            this.setStop();
        }
    }
}