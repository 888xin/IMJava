package com.pdworld.client.em.ui.loginui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.Box;

import com.pdworld.client.em.ui.loginui.LoginConfig;


import java.awt.event.ActionListener;

/**
 * @author Administrator
 *
 * 按钮界面:配置,登录,取消 三个按钮
 *
 */
public class ButtonUI extends JPanel {
    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;

    /**
     * 配置按钮
     */
    private JButton btnConfig = new JButton(LoginConfig.CONFIGBTN);

    /**
     * 登录按钮
     */
    private JButton btnLogin = new JButton(LoginConfig.LOGINBTN);

    /**
     * 取消按钮
     */
    private JButton btnCancel = new JButton(LoginConfig.CANCELBTN);

    /**
     * 内部盒子布局
     */
    private Box myBox = Box.createHorizontalBox();

    /**
     * 初始化
     *
     */
    public void init() {
        this.setOpaque(false);

        Dimension dim = new Dimension(76, 23);

        btnLogin.setPreferredSize(dim);
        btnLogin.setMaximumSize(dim);
        btnLogin.setMinimumSize(dim);

        btnCancel.setPreferredSize(dim);
        btnCancel.setMaximumSize(dim);
        btnCancel.setMinimumSize(dim);

        btnConfig.setPreferredSize(dim);
        btnConfig.setMaximumSize(dim);
        btnConfig.setMinimumSize(dim);

        btnConfig.setOpaque(false);
        btnLogin.setOpaque(false);
        btnCancel.setOpaque(false);

        btnConfig.setName("Config");

        this.setLayout(new BorderLayout());
        this.add(myBox, BorderLayout.CENTER);

        myBox.add(btnConfig);
        myBox.add(Box.createHorizontalGlue());
        myBox.add(btnLogin);
        myBox.add(Box.createHorizontalStrut(8));
        myBox.add(btnCancel);

    }

    /**
     * 构造函数
     *
     */
    public ButtonUI() {
        init();
    }

    /**
     * 添加按钮事件
     *
     * @param a
     */
    public void addButtonActionListener(ActionListener a) {
        btnCancel.addActionListener(a);
        btnConfig.addActionListener(a);
        btnLogin.addActionListener(a);
        btnLogin.setActionCommand(LoginConfig.LOGINBTN);
        btnConfig.setActionCommand(LoginConfig.CONFIGBTN);

    }

    /**
     *
     */
    public void showConfigState() {
        if (btnConfig.getName().equals("Config")) {
            btnConfig.setText(LoginConfig.CONFIGBTN + "↓");
            btnConfig.setName("UnConfig");
        } else {
            btnConfig.setText(LoginConfig.CONFIGBTN + "↑");
            btnConfig.setName("Config");
        }
    }

    public void setDefaultButton(JFrame f) {
        if (f != null) {
            f.getRootPane().setDefaultButton(btnLogin);
        }
    }
}