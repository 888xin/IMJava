package com.pdworld.client.em.filetrans.trans;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * 接受文件服务器
 *
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class TransFileServer extends Thread {

    /**
     * 服务器SOCKET
     */
    private ServerSocket server;

    /**
     * 服务器运行的IP
     */
    private InetAddress ip;

    /**
     * 服务器运行的端口
     */
    private int port = -1;

    /**
     * 标识服务器停止状态
     */
    private boolean isStop = false;

    /**
     * 构造函数
     */
    private static TransFileServer transFileServer;

    public static TransFileServer getInstance() {
        if (transFileServer == null)
            transFileServer = new TransFileServer();
        return transFileServer;
    }

    private TransFileServer() {
        init();
        this.start();
    }

    /**
     * 对IP与端口进行初始化
     */
    public void init() {
        InetAddress address[];
        try {
            address = InetAddress.getAllByName(InetAddress.getLocalHost()
                    .getHostName());
            if (address.length > 0)
                ip = address[0];
            System.out.println("试着建立");
            System.out.println("port:" + port + ";ip:" + ip.getHostAddress());
            server = new ServerSocket(0);
            this.port = server.getLocalPort();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }
    }

    /**
     * 启动服务器
     */
    public void run() {
        Socket client = null;
        try {

            System.out.println("建立接收服务器"+server.getLocalPort());
            while (!isStop) {
                client = server.accept();
                System.out.println("收到一个发送线程");
                ReceiveStreamThread receiveStreamThread = new ReceiveStreamThread(client);
                receiveStreamThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    // e.printStackTrace();
                }
            }
            isStop = true;
        }
    }

    /**
     * 取得当前接受文件服务器运行状态
     *
     * @return
     */
    public boolean getIsStop() {
        return isStop;
    }

    /**
     * 停止服务器
     */
    public void setStop() {
        this.isStop = true;
        try {
            this.server.close();
        } catch (IOException e) {
            //			e.printStackTrace();
        }
    }

    /**
     * 取得服务器运行的IP
     *
     * @return
     */
    public int getServerPort() {
        return port;
    }

    /**
     * 取得服务器绑定的端口
     *
     * @return
     */
    public String getServerIP() {
        return ip.getHostAddress();
    }
}