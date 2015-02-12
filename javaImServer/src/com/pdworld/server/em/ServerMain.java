package com.pdworld.server.em;

import java.awt.Font;

import javax.swing.UIManager;

import com.pdworld.pub.pub.PubToolkit;
import com.pdworld.server.em.pub.PubValue;
import com.pdworld.server.em.pub.DataBaseComCon;
import com.pdworld.server.em.ui.serverui.ServerUI;



public class ServerMain {

    private static final int serverPort = 8888;

    public static int getServerPort(){
        return serverPort;
    }

    /**
     * 程序启动主函数
     * @param args
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
        }
        PubToolkit.setUIFont(new Font("宋体",Font.PLAIN,12));
        if (DataBaseComCon.testConnection()) {
            PubValue.setAllUserDown();
            ServerUI serverUI = ServerUI.getInstance();
            serverUI.setVisible(true);
        }else{
            PubToolkit.showInformation("对不起,连接数据源出错.");
        }
    }
}