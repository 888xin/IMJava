package com.pdworld.client.em.ui.mainui.usertree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.tree.TreeCellRenderer;

import com.pdworld.client.em.ui.images.GetImage;
import com.pdworld.client.em.ui.mainui.MainUI;
import com.pdworld.pub.unit.Company;
import com.pdworld.pub.unit.Department;
import com.pdworld.pub.unit.User;


public class MyTreeCellRenderer extends JLabel implements TreeCellRenderer {

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;

    private ImageIcon close;

    private ImageIcon open;

    private Border line = BorderFactory.createLineBorder(new Color(139, 168,
            198));

    private Border empty = BorderFactory.createEmptyBorder();

    private Object value;

    public MyTreeCellRenderer() {
        close = GetImage.getSkinImage(UserTreeConfig.TREECLOSE);
        open = GetImage.getSkinImage(UserTreeConfig.TREEOPEN);
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean selected, boolean expanded, boolean leaf, int row,
                                                  boolean hasFocus) {
        this.value = value;

        try {
            if (value instanceof Company) {
                Company c = (Company) value;
                this.setText(c.getName());

                this.setIcon(expanded ? open : close);
            } else if (value instanceof Department) {
                Department d = (Department) value;
                int Count = MainUI.getInstance().getUserTree()
                        .getDepartmentUserCount(d);
                int onLine = MainUI.getInstance().getUserTree()
                        .getDepartmentOnlineUserCount(d);
                this.setText(d.getName() + "(" + Count + "/" + onLine + ")");

                this.setIcon(expanded ? open : close);
            } else if (value instanceof User) {
                User u = (User) value;
                this.setText(u.getName());
                ImageIcon icon;
                if (u.getIsOnline() == 1) {
                    icon = GetImage.getMinHead(u.getIconId());
                    if (icon != null) {
                        this.setIcon(icon);
                    } else {
                        icon = GetImage.getMinHead(1);
                        if (icon != null) {
                            this.setIcon(icon);
                        }
                    }
                } else {
                    icon = GetImage.getGrayHead(u.getIconId());
                    if (icon != null) {
                        this.setIcon(icon);
                    } else {
                        icon = GetImage.getGrayHead(1);
                        if (icon != null) {
                            this.setIcon(icon);
                        }
                    }
                }
            }

            this.setBorder(selected ? line : empty);
            this.setBackground(selected ? new Color(206, 233, 254)
                    : Color.WHITE);
            this.setOpaque(true);
        } catch (Exception e) {
        }
        return this;
    }

    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        if (value instanceof User)
            return new Dimension(130, size.height + 2);
        else if (value instanceof Department)
            return new Dimension(150, size.height + 2);
        else
            return new Dimension(170, size.height + 2);

    }
}
