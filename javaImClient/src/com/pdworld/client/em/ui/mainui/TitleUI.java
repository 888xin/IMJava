package com.pdworld.client.em.ui.mainui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.pdworld.client.em.ui.images.GetImage;


/**
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class TitleUI extends JPanel {

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;

    private JLabel headLabel = new JLabel();

    private JLabel idLabel = new JLabel();

    private JLabel textLabel = new JLabel();

    private JButton closeBtn = new JButton();;

    public TitleUI() {
        init();
    }

    public TitleUI(String id, int iconId, String text) {
        setHead(iconId);
        setId(id);
        setText(text);
        init();
    }

    public void init() {
        ImageIcon imageIcon = GetImage.getSkinImage("CloseButton_Normal.gif");

        boolean isHaveImage = false;
        if (imageIcon != null) {
            closeBtn.setIcon(imageIcon);
            isHaveImage = true;
        }
        imageIcon = GetImage.getSkinImage("CloseButton_Hover.gif");
        if (imageIcon != null) {
            closeBtn.setRolloverIcon(imageIcon);
            isHaveImage = true;
        }
        imageIcon = GetImage.getSkinImage("CloseButton_Down.gif");
        if (imageIcon != null) {
            closeBtn.setPressedIcon(imageIcon);
            isHaveImage = true;
        }
        closeBtn.setBorder(null);
        closeBtn.setContentAreaFilled(false);
        if (!isHaveImage) {
            closeBtn.setContentAreaFilled(true);
            closeBtn.setText("X");
        }
        closeBtn.setOpaque(false);
        closeBtn.setBounds(0, 0, 18, 18);
        closeBtn.setPreferredSize(new Dimension(18, 18));
        closeBtn.setMaximumSize(closeBtn.getPreferredSize());
        closeBtn.setMinimumSize(closeBtn.getPreferredSize());
        this.setLayout(new BorderLayout());
        JPanel backPanel = BackPanelFactory.createHorizontalAutoExt(GetImage
                .getSkinImage("titleTopLeft.gif"), GetImage
                .getSkinImage("titleTopCenter.gif"), GetImage
                .getSkinImage("titleTopRight.gif"));

        backPanel.setLayout(new BoxLayout(backPanel, BoxLayout.X_AXIS));

        backPanel.add(Box.createGlue());
        JPanel closePanel = new JPanel();

        closePanel.setPreferredSize(new Dimension(closeBtn.getWidth(),
                Short.MAX_VALUE));
        closePanel.setMaximumSize(closePanel.getPreferredSize());
        closePanel.setMinimumSize(closePanel.getPreferredSize());

        closePanel.setOpaque(false);
        closePanel.add(closeBtn);
        backPanel.add(closePanel);
        backPanel.add(Box.createHorizontalStrut(10));
        this.add(backPanel, BorderLayout.NORTH);

        backPanel = BackPanelFactory.createHorizontalAutoExt(GetImage
                .getSkinImage("titleleft.gif"), GetImage
                .getSkinImage("titlecenter.gif"), GetImage
                .getSkinImage("titleright.gif"));

        this.add(backPanel, BorderLayout.CENTER);
        backPanel.setLayout(new BoxLayout(backPanel, BoxLayout.X_AXIS));

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel();
        imageIcon = GetImage.getSkinImage("titleleft.gif");
        if (imageIcon != null) {
            label.setIcon(imageIcon);
        }
        label.setLayout(null);
        label.add(headLabel);
        headLabel.setOpaque(false);
        headLabel.setHorizontalAlignment(JLabel.CENTER);
        headLabel.setVerticalAlignment(JLabel.CENTER);
        headLabel.setBounds(9, 9, 37, 37);

        backPanel.add(label);

        JLabel label2 = new JLabel();
        imageIcon = GetImage.getSkinImage("StatusButton_Down.gif");
        if (imageIcon != null) {
            label2.setIcon(imageIcon);
        }
        label2.setBounds(44, 1, 50, 50);
        label.add(label2);

        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.Y_AXIS));

        idLabel.setOpaque(false);
        textLabel.setOpaque(false);
        idLabel.setPreferredSize(new Dimension(Short.MAX_VALUE, 15));
        textLabel.setPreferredSize(new Dimension(Short.MAX_VALUE, 20));
        idLabel.setFont(new Font("宋体", Font.BOLD, 12));
        idLabel.setForeground(new Color(49, 106, 196));
        tempPanel.add(idLabel);
        tempPanel.add(textLabel);
        tempPanel.setOpaque(false);
        backPanel.add(Box.createHorizontalStrut(7));
        backPanel.add(tempPanel);
    }

    public void setHead(int iconId) {
        ImageIcon imageIcon = GetImage.getMinHead(iconId);
        if (imageIcon != null) {
            headLabel.setIcon(imageIcon);
        }
    }

    public void addActionListener(ActionListener actionListener) {
        closeBtn.addActionListener(actionListener);
        closeBtn.setActionCommand(MainUIConfig.CLOSEBTN);
    }

    public void setId(String id) {
        idLabel.setText(id);
    }

    public void setText(String text) {
        textLabel.setText(text);
    }
}