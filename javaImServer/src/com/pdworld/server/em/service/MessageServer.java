package com.pdworld.server.em.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.pdworld.server.em.pub.LogOper;
import com.pdworld.server.em.ui.serverui.ServerUI;


/**
 * 消息服务器
 */
public class MessageServer extends Thread {

    private ServerSocket server;

    private boolean isStop = false;

    public MessageServer(int port) throws IOException {
        server = new ServerSocket(port);
    }

    /**
     * 消息服务
     */
    public void run() {
        Socket client;
        try {
            while (!isStop) {
                client = server.accept();
                (new ClientThread(client)).start();
            }
        } catch (IOException e) {
            // e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e1) {
                    // e1.printStackTrace();
                }
            }
            isStop = true;
        }
    }

    /**
     * 停止消息服务器
     *
     */
    public void stopServer() {
        isStop = true;
        try {
            server.close();
            ServerUI.getInstance().getLogUI().addLog(
                    LogOper.getInstance().insertStopServerLog());
        } catch (IOException e) {
            ServerUI.getInstance().getLogUI().addLog(
                    LogOper.getInstance().insertStopServerErrorLog());
        }
    }

}