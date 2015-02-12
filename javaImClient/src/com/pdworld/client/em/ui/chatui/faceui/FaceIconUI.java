package com.pdworld.client.em.ui.chatui.faceui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.BorderFactory;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class FaceIconUI extends JLabel implements MouseListener {

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;

    private URL url;

    private Color backColor = Color.WHITE;

    private Color borderColorNormal = new Color(223, 230, 246);

    private Color borderColorOver = Color.BLUE;

    private int id;

    private int iconId;

    /**
     * 构造函数
     *
     * @param url
     * @param width
     * @param height
     * @param backColor
     * @param borderColorNormal
     * @param borderColorOver
     */
    public FaceIconUI(URL url, int width, int height, Color backColor,
                      Color borderColorNormal, Color borderColorOver) {
        setUrl(url);
        setSize(width, height);
        setBackColor(backColor);
        setBorderColorNormal(borderColorNormal);
        setBorderColorOver(borderColorOver);
        init();
    }

    /**
     * 初始化
     *
     */
    public void init() {
        ImageIcon imageIcon = getImageIcon();
        this.setBackground(backColor);
        this.setOpaque(true);
        this.addMouseListener(this);

        Dimension dim = new Dimension(this.getWidth(), this.getHeight());
        this.setPreferredSize(dim);
        this.setMaximumSize(dim);
        this.setMinimumSize(dim);

        this.setLayout(null);

        if (url != null) {
            this.setIcon(imageIcon);
        }
        this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        this.setBounds(0, 0, this.getWidth(), this.getHeight());

        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVerticalAlignment(JLabel.CENTER);
    }

    /**
     * 取得动态图片的第一帧
     *
     * @return
     */
    private ImageIcon getImageIcon() {
        ImageIcon icon = new ImageIcon(url);
        BufferedImage buf = new BufferedImage(icon.getIconWidth(), icon
                .getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = buf.getGraphics();
        ImageIcon icon2 = new ImageIcon(url);
        g.drawImage(icon2.getImage(), 0, 0, icon2.getIconWidth(), icon2
                .getIconHeight(), Color.WHITE, null);
        return new ImageIcon(buf);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /*
     * （非 Javadoc）
     *
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseClicked(MouseEvent e) {

    }

    /*
     * （非 Javadoc）
     *
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    public void mousePressed(MouseEvent e) {

    }

    /*
     * （非 Javadoc）
     *
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    public void mouseReleased(MouseEvent e) {

    }

    /*
     * （非 Javadoc）
     *
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    public void mouseEntered(MouseEvent e) {
        this.setBorder(BorderFactory.createLineBorder(borderColorOver, 1));
    }

    /*
     * （非 Javadoc）
     *
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    public void mouseExited(MouseEvent e) {
        this.setBorder(BorderFactory.createLineBorder(borderColorNormal, 1));

    }

    /**
     * @return 返回 backColor。
     */
    public Color getBackColor() {
        return backColor;
    }

    /**
     * @param backColor
     *            要设置的 backColor。
     */
    public void setBackColor(Color backColor) {
        this.backColor = backColor;
    }

    /**
     * @return 返回 borderColorNormal。
     */
    public Color getBorderColorNormal() {
        return borderColorNormal;
    }

    /**
     * @param borderColorNormal
     *            要设置的 borderColorNormal。
     */
    public void setBorderColorNormal(Color borderColorNormal) {
        this.borderColorNormal = borderColorNormal;
    }

    /**
     * @return 返回 borderColorOver。
     */
    public Color getBorderColorOver() {
        return borderColorOver;
    }

    /**
     * @param borderColorOver
     *            要设置的 borderColorOver。
     */
    public void setBorderColorOver(Color borderColorOver) {
        this.borderColorOver = borderColorOver;
    }

    /**
     * @return 返回 url。
     */
    public URL getUrl() {
        return url;
    }

    /**
     * @param url
     *            要设置的 url。
     */
    public void setUrl(URL url) {
        this.url = url;
    }

    /**
     * @return 返回 iconId。
     */
    public int getIconId() {
        return iconId;
    }

    /**
     * @param iconId
     *            要设置的 iconId。
     */
    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}