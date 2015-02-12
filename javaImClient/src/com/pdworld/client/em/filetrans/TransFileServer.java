package com.pdworld.client.em.filetrans;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class TransFileServer extends Thread {

    private ServerSocket server;

    private InetAddress ip;

    private int port = -1;

    public TransFileServer() {
        init();
    }

    public void init() {
        InetAddress address[];
        try {
            address = InetAddress.getAllByName(InetAddress.getLocalHost()
                    .getHostName());
            if (address.length > 0)
                ip = address[0];
            if (port == -1)
                port = new CheckLeisureTcpPort(ip.getHostAddress()).getPort();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        Socket client = null;
        server = null;
        try {
            server = new ServerSocket(port, 15, ip);
            client = server.accept();
            ReceiveStreamThread receiveStreamThread = new ReceiveStreamThread(
                    client);
            receiveStreamThread.start();
        } catch (IOException e) {
            // e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    // e.printStackTrace();
                }
            }
        }
    }

    public int getServerPort() {
        return port;
    }

    public String getServerIP() {
        return ip.getHostAddress();
    }
}
