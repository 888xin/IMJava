package com.pdworld.client.em.ui.chatui.faceui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class ViewIconUI extends JPanel {
    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;
    //背景色
    private Color backColor;
    //边框色
    private Color borderColor;
    //宽度
    private int width;
    //高度
    private int height;
    //显示图标
    private JLabel viewIcon;

    /**
     * @param backColor
     * @param borderColor
     * @param width
     * @param height
     */
    public ViewIconUI(Color backColor, Color borderColor, int width, int height) {
        setBackColor(backColor);
        setBorderColor(borderColor);
        setWidth(width);
        setHeight(height);

        init();
    }

    /**
     * 初始化
     */
    public void init()
    {
        this.setOpaque(false);
        viewIcon = new JLabel();
        viewIcon.setBorder(BorderFactory.createLineBorder(borderColor, 1));
        viewIcon.setBackground(backColor);
        viewIcon.setOpaque(true);

        viewIcon.setHorizontalAlignment(JLabel.CENTER);
        viewIcon.setVerticalAlignment(JLabel.CENTER);

        this.setBackground(backColor);
        this.setBounds(0,0,width,height);
        this.setLayout(new BorderLayout());
        this.add(viewIcon, BorderLayout.CENTER);
        this.setBorderColor(borderColor);
    }

    /**
     * 设置显示的图标
     * @param url
     */
    public void setIcon(URL url)
    {
        viewIcon.setIcon(new ImageIcon(url));
    }

    /**
     * @param backColor 要设置的 backColor。
     */
    public void setBackColor(Color backColor) {
        this.backColor = backColor;
    }
    /**
     * @param borderColor 要设置的 borderColor。
     */
    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }
    /**
     * @param height 要设置的 height。
     */
    public void setHeight(int height) {
        this.height = height;
    }
    /**
     * @param width 要设置的 width。
     */
    public void setWidth(int width) {
        this.width = width;
    }
}
