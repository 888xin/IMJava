package com.pdworld.client.em.ui.chatui;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.JPanel;

/**
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class BottomPanel extends JPanel{

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;

    private JButton sendBtn = new JButton("发送");

    private JButton closeBtn = new JButton("关闭");

    private JButton chatBtn = new JButton("聊天记录");

    public BottomPanel()
    {
        init();
    }

    public void init(){

        Box box = Box.createHorizontalBox();
        box.add(Box.createHorizontalStrut(10));
        box.add(chatBtn);
        box.add(Box.createHorizontalGlue());
        box.add(closeBtn);
        box.add(Box.createHorizontalStrut(20));
        box.add(sendBtn);
        box.add(Box.createHorizontalStrut(10));

        this.setLayout(new BorderLayout());
        this.add(box, BorderLayout.CENTER);
    }

    public void addButtonActionListener(ActionListener actionListener){
        chatBtn.addActionListener(actionListener);
        closeBtn.addActionListener(actionListener);
        sendBtn.addActionListener(actionListener);
        chatBtn.setActionCommand(ChatUIConfig.CHATBTN);
        closeBtn.setActionCommand(ChatUIConfig.CLOSEBTN);
        sendBtn.setActionCommand(ChatUIConfig.SENDBTN);
    }
}
