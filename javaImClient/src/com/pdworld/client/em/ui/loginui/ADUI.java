package com.pdworld.client.em.ui.loginui;

import java.awt.Dimension;
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;

import com.pdworld.client.em.ui.images.GetImage;

/**
 *
 * @author Administrator
 *
 * 上方的广告界面面板 *
 */
public class ADUI extends JPanel {

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;
    /*
     * 装载广告图片的标签
     */
    private JLabel adLabel = new JLabel();

    /**
     * 初始化广告界面
     *
     */

    /**
     * 构造函数
     *
     */
    public ADUI() {
        init();
    }

    public void init() {
		/*
		 * 设置默认的大小
		 */
        Dimension dim = new Dimension(LoginConfig.ADUIWIDTH,
                LoginConfig.ADUIHIDTH);
        this.setPreferredSize(dim);
        this.setMinimumSize(dim);
        this.setMinimumSize(dim);
        // this.setOpaque(false);

		/*
		 * 添加装载广告的标签
		 */
        this.setLayout(new BorderLayout());
        this.add(adLabel, BorderLayout.CENTER);

		/*
		 * 添加广告图标
		 */
        this.setAD(GetImage.getSkinImage(LoginConfig.AD));
    }

    /**
     * 设置广告界面的广告
     *
     * @param ad
     *            广告的图片路径
     *
     */
    private void setAD(ImageIcon imageIcon) {
        if (imageIcon != null)
            this.adLabel.setIcon(imageIcon);
    }

}
