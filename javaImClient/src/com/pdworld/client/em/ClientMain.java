package com.pdworld.client.em;

import java.awt.Font;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.pdworld.client.em.pub.ConfigOper;
import com.pdworld.client.em.ui.loginui.LoginUI;
import com.pdworld.pub.pub.PubToolkit;


/**
 * 客户端
 * @author Administrator
 */
public class ClientMain {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        PubToolkit.setUIFont(new Font("宋体", Font.PLAIN, 12));
        LoginUI loginUI = new LoginUI();
        loginUI.setConfig(ConfigOper.getConfig());
        loginUI.setVisible(true);
    }
}