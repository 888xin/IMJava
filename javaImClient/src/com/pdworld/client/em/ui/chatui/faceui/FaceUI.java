package com.pdworld.client.em.ui.chatui.faceui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JDialog;

/**
 * @author Administrator
 *
 * 聊天表情选择窗口
 */
public class FaceUI extends JDialog {

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;

    private FaceModel faceModel = new DefaultFaceModel();;

    private FaceGridUI faceGridUI = new FaceGridUI(faceModel);

    private ViewIconUI viewIconUI = new ViewIconUI(faceModel.getGridBackColor(), new Color(49,
            106, 196), faceModel.getGridWidth() * 3, faceModel
            .getGridHeigth() * 3);

    public FaceUI() {
        init();
    }

    public void init() {
        this.setUndecorated(true);
        this.getContentPane().add(faceGridUI, BorderLayout.CENTER);
        this.getLayeredPane().add(viewIconUI);
        this.faceGridUI.addMouseListener(new FaceUIViewMouseListener(this));
        this.pack();
    }

    public void addMouseListener(MouseListener mouseListener) {
        this.faceGridUI.addMouseListener(mouseListener);
    }

    public ViewIconUI getViewIconUI(){
        return viewIconUI;
    }

    public FaceModel getFaceModel(){
        return faceModel;
    }

    /**
     * 把查询表情的窗口移动到最左边
     */
    public void moveViewIconLeft() {
        viewIconUI.setBounds(0, 0, viewIconUI.getWidth(),viewIconUI.getHeight());
    }

    /**
     * 把查询表情的窗口移动到最右边
     */
    public void moveViewIconRight() {
        viewIconUI.setBounds(this.getWidth()-viewIconUI.getWidth(),0, viewIconUI.getWidth(),viewIconUI.getHeight());
    }

}
class FaceUIViewMouseListener extends MouseAdapter{

    private FaceUI faceUI;

    private ViewIconUI viewIconUI;

    private FaceModel faceModel;

    public FaceUIViewMouseListener(FaceUI faceUI){
        this.faceUI = faceUI;
        this.viewIconUI = faceUI.getViewIconUI();
        this.faceModel = faceUI.getFaceModel();
    }

    public void mouseEntered(MouseEvent e) {
        FaceIconUI faceIconUI = (FaceIconUI) e.getSource();
        viewIconUI.setIcon(faceIconUI.getUrl());

        int x, y;
        x = faceIconUI.getId() / faceModel.getColumn();
        x += faceIconUI.getId() % faceModel.getColumn() > 0 ? 1 : 0;
        y = faceIconUI.getId() % faceModel.getColumn();

        if (y <= 4 && x <= 4) {
            faceUI.moveViewIconRight();
        } else if (y >= (faceModel.getColumn() - 4) && x <= 4) {
            faceUI.moveViewIconLeft();
        }
    }
}


