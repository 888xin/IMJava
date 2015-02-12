package com.pdworld.client.em.ui.chatui;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.pdworld.client.em.ui.images.GetImage;
import com.pdworld.pub.unit.Company;
import com.pdworld.pub.unit.Department;
import com.pdworld.pub.unit.User;


/**
 * @author Administrator
 *
 */
public class TopPanel extends JPanel {

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;

    private JLabel chatUserIcon = new JLabel();

    private JLabel chatUserName = new JLabel();

    private JLabel chatUserId = new JLabel();

    private JLabel dptName = new JLabel();

    private JLabel comName = new JLabel();

    public TopPanel() {
        init();
    }

    public void init() {

        Box panelBox = Box.createHorizontalBox();
        Box box1 = Box.createVerticalBox();
        box1.add(chatUserName);
        box1.add(chatUserId);
        panelBox.add(Box.createHorizontalStrut(10));
        panelBox.add(chatUserIcon);
        panelBox.add(Box.createHorizontalStrut(10));
        panelBox.add(box1);

        panelBox.add(dptName);
        panelBox.add(comName);
        panelBox.add(Box.createHorizontalGlue());

        this.setLayout(new BorderLayout());
        this.add(panelBox, BorderLayout.CENTER);
    }

    public void setObject(Object object) {
        if (object instanceof User) {
            User user = (User) object;
            chatUserName.setText(user.getName());
            ImageIcon imageIcon = GetImage.getBigHead(user.getIconId());
            if (imageIcon != null) {
                chatUserIcon.setIcon(imageIcon);
            }
            chatUserId.setText(user.getId());
            dptName.setVisible(false);
            comName.setVisible(false);
        } else if (object instanceof Department) {
            dptName.setText("部门:" + ((Department) object).getName());
        } else if (object instanceof Company) {
            comName.setText("公司:" + ((Company) object).getName());
        }
    }
}