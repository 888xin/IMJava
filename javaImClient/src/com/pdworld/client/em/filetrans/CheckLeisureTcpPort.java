package com.pdworld.client.em.filetrans;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 检测某IP可用端口,从1025到65535
 */
public class CheckLeisureTcpPort extends Thread{

    private int port = -1;

    private String ip;

    public CheckLeisureTcpPort(String ip){
        this.ip = ip;
        this.start();
    }

    public void run(){
        for(int i = 1025; i<= 65535; i++){
            if(port != -1)
                return;
            new CheckTcpServerPort(ip, i, this);
        }
    }

    public int getPort(){
        while(true){
            if(port == -1)
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }
            else
                return port;
        }
    }

    public synchronized void setPort(int port){
        if(this.port != -1)
            return;
        this.port = port;
    }
}
/**
 * 检测某个IP,某个端口的可用性
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
class CheckTcpServerPort extends Thread{

    private int port;

    private String ip;

    private CheckLeisureTcpPort checkLeisureTcpPort;

    public CheckTcpServerPort(String ip, int port, CheckLeisureTcpPort checkLeisureTcpPort){
        this.port = port;
        this.ip = ip;
        this.checkLeisureTcpPort = checkLeisureTcpPort;
        this.start();
    }

    public void run(){
        Socket socket = null;
        try {
            socket = new Socket(ip, port);
            checkLeisureTcpPort.setPort(port);
        } catch (UnknownHostException e) {
            //e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
        } finally{
            if(socket != null)
                try {
                    socket.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
        }
    }
}