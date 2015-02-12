package com.pdworld.server.em.ui.serverui.onlineui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTable;

/**
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class OnLineTableUI extends JPanel{

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;
    private JTable onLineTable = new JTable();

    public OnLineTableUI(){
        init();
    }

    public void init(){
        this.setLayout(new BorderLayout());
        this.add(onLineTable, BorderLayout.CENTER);
    }
}
