package com.pdworld.server.em.ui.serverui.departmentui;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class ButtonPanel extends JPanel {

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;

    private JButton addBtn = new JButton("添加");

    private JButton delBtn = new JButton("删除");

    private JButton makeBtn = new JButton("修改");

    public ButtonPanel() {
        init();
    }

    public void init() {

        Dimension size = new Dimension(80, 25);

        addBtn.setPreferredSize(size);
        delBtn.setPreferredSize(size);
        makeBtn.setPreferredSize(size);

        this.add(addBtn);
        this.add(delBtn);
        this.add(makeBtn);

        addBtn.setActionCommand(DepartmentConfig.ADDBTN);
        delBtn.setActionCommand(DepartmentConfig.DELBTN);
        makeBtn.setActionCommand(DepartmentConfig.UPDATEBTN);
    }

    public void addButtonActionListener(ActionListener actionListener) {
        addBtn.addActionListener(actionListener);
        delBtn.addActionListener(actionListener);
        makeBtn.addActionListener(actionListener);
    }

}


