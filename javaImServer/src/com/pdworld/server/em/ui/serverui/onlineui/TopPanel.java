package com.pdworld.server.em.ui.serverui.onlineui;

import java.awt.Dimension;
import javax.swing.BorderFactory;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.pdworld.server.em.ui.serverui.image.GetImage;


/**
 * @author Administrator
 *
 */
public class TopPanel extends JPanel{

    private static final long serialVersionUID = 1L;

    private JButton serverBtn = new JButton(OnLineConfig.STARTSERVERTEXT);

    private JButton downLineBtn = new JButton(OnLineConfig.DOWNONLINETEXT);

    private JLabel iconLabel = new JLabel();

    public TopPanel(){
        init();
    }

    public void init(){
        this.add(iconLabel);
        this.add(serverBtn);
        this.add(downLineBtn);
        this.setServerBtn(OnLineConfig.STARTSERVERBTN);

        iconLabel.setPreferredSize(new Dimension(28, 28));
        serverBtn.setPreferredSize(new Dimension(100, 25));
        downLineBtn.setPreferredSize(new Dimension(100,25));
        this.setBorder(BorderFactory.createTitledBorder("服务器"));
    }

    public void setServerBtn(String type){
        if(type.equals(OnLineConfig.STARTSERVERBTN)){
            ImageIcon imageIcon = GetImage.getSkinImage("stopserver.gif");
            if(imageIcon != null){
                iconLabel.setIcon(imageIcon);
            }
            this.serverBtn.setActionCommand(OnLineConfig.STARTSERVERBTN);
            this.serverBtn.setText(OnLineConfig.STARTSERVERTEXT);

        }else if(type.equals(OnLineConfig.STOPSERVERBTN)){
            ImageIcon imageIcon = GetImage.getSkinImage("startserver.gif");
            if(imageIcon != null){
                iconLabel.setIcon(imageIcon);
            }
            this.serverBtn.setActionCommand(OnLineConfig.STOPSERVERBTN);
            this.serverBtn.setText(OnLineConfig.STOPSERVERTEXT);
        }
    }

    public void addButtonActionListener(ActionListener actionListener){
        this.serverBtn.addActionListener(actionListener);
        this.downLineBtn.addActionListener(actionListener);
        this.serverBtn.setActionCommand(OnLineConfig.STARTSERVERBTN);
        this.downLineBtn.setActionCommand(OnLineConfig.DOWNONLINEBTN);
    }
}
