package com.pdworld.client.em.ui.chatui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.pdworld.client.em.filetrans.ui.TransFileUI;


/**
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class RightPanel extends JPanel {

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;
    private JTabbedPane transFilePane = new JTabbedPane(JTabbedPane.BOTTOM);

    public RightPanel() {
        init();
    }

    public void init() {
        this.setLayout(new BorderLayout());
        this.add(transFilePane, BorderLayout.NORTH);
        this.add(new JPanel());
        this.setPreferredSize(new Dimension(250,this.getPreferredSize().height));
    }

    public void addTransFileUI(TransFileUI transFileUI) {
        this.transFilePane.addTab((this.transFilePane.getTabCount() + 1) + "",
                transFileUI);
    }

    public void removeTransFileUI(TransFileUI transFileUI){
        System.out.println("remove");
        transFilePane.remove(transFileUI);
        System.out.println("remove over");
    }

}