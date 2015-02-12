package com.pdworld.client.em.ui.mainui;

import java.awt.Cursor;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;
/**
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class MainUIMouseInputListener extends MouseInputAdapter{

    private MainUI mainUI;

    private int x, y;

    private int sx, sy;

    public MainUIMouseInputListener(MainUI mainUI){
        this.mainUI = mainUI;
    }

    public void mouseDragged(MouseEvent e) {
        //移动窗口
        if (e.getSource() != mainUI.getRigthdownLabel()) {
            mainUI.setCursor(new Cursor(Cursor.MOVE_CURSOR));
            mainUI.setLocation(sx + (e.getX() - x), sy + (e.getY() - y));
            sx = mainUI.getX();
            sy = mainUI.getY();
        } else {
            //扩展窗口
         /*   if (mainUI.getPreferredSize().width < mainUI.getMinSize().width) {
                mainUI.setPreferredSize(new Dimension((int) mainUI.getMinSize().width,
                        (int) mainUI.getPreferredSize().getHeight()));
                mainUI.pack();
                return;
            }
            if (mainUI.getPreferredSize().getHeight() < mainUI.getMinSize().getHeight()) {
                mainUI.setPreferredSize(new Dimension((int) mainUI
                        .getPreferredSize().getWidth(), (int) mainUI.getMinSize()
                        .getHeight()));
                mainUI.pack();
                return;
            }
            mainUI.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
            mainUI.setPreferredSize(new Dimension(mainUI.getPreferredSize().width
                    + (e.getX() - x), mainUI.getPreferredSize().height
                    + (e.getY() - y)));
            mainUI.pack();*/
        }
    }

    public void mousePressed(MouseEvent e) {
        sx = mainUI.getX();
        sy = mainUI.getY();
        x = e.getX();
        y = e.getY();
    }
    public void mouseReleased(MouseEvent e) {
        mainUI.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
}
