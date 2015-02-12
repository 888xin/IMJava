package com.pdworld.client.em.ui.chatui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Box;

import com.pdworld.client.em.ui.images.GetImage;


/**
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class FunctionButtonPanel extends JPanel {

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;

    private JButton faceButton = new JButton();

    private JButton fileButton = new JButton();

    private JButton fontButton = new JButton();

    public FunctionButtonPanel() {
        init();
    }

    public void setFileBtn(boolean isVisible){
        fileButton.setVisible(isVisible);
    }

    public void init() {
        setButton("setface.gif","setfaceover.gif",faceButton);
        setButton("sendfile.gif","sendfileover.gif",fileButton);
        setButton("setfont.gif","setfontover.gif",fontButton);

        Box box = Box.createHorizontalBox();
        box.add(Box.createHorizontalStrut(10));
        box.add(fontButton);
        box.add(Box.createHorizontalStrut(10));
        box.add(faceButton);
        box.add(Box.createHorizontalStrut(10));
        box.add(fileButton);
        box.add(Box.createHorizontalGlue());

        this.setLayout(new BorderLayout());
        this.add(box, BorderLayout.CENTER);



        this.setPreferredSize(new Dimension(Short.MAX_VALUE, 25));
    }

    private void setButton(String icon, String preIcon, JButton button){
        ImageIcon imageIcon = GetImage.getSkinImage(icon);
        if(imageIcon != null){
            button.setIcon(imageIcon);
        }
        imageIcon = GetImage.getSkinImage(preIcon);
        if(imageIcon != null){
            button.setPressedIcon(imageIcon);
        }
        button.setContentAreaFilled(false);
        button.setBorder(null);

        button.setMargin(new Insets(0,0,0,0));

    }

    public void addButtonActionListener(ActionListener actionListener){
        this.faceButton.addActionListener(actionListener);
        this.fileButton.addActionListener(actionListener);
        this.fontButton.addActionListener(actionListener);
        faceButton.setActionCommand(ChatUIConfig.FACEBTN);
        fileButton.setActionCommand(ChatUIConfig.FILEBTN);
        fontButton.setActionCommand(ChatUIConfig.FONTBTN);
    }
}