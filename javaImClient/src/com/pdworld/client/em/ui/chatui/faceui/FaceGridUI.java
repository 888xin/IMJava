package com.pdworld.client.em.ui.chatui.faceui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class FaceGridUI extends JPanel {

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;

    private List faceIconUIList = new ArrayList();

    private FaceModel faceModel;

    /**
     * 构造函数
     */
    public FaceGridUI(FaceModel faceModel) {
        this.faceModel = faceModel;
        init();
    }

    /**
     * 初始化
     */
    public void init() {
        Dimension dim = new Dimension(faceModel.getGridWidth()
                * faceModel.getColumn(), faceModel.getGridHeigth()
                * faceModel.getRow());
        this.setPreferredSize(dim);
        this.setMaximumSize(dim);
        this.setMinimumSize(dim);

        this.setLayout(null);
        this.setBackground(faceModel.getGridBackColor());

        this.setBounds(0, 0, faceModel.getGridWidth() * faceModel.getColumn(),
                faceModel.getGridHeigth() * faceModel.getRow());
        this.setBorder(BorderFactory.createLineBorder(faceModel.getGridBorderColor()));
        loadIcon();
    }

    /**
     * 重载
     */
    public void paintComponent(Graphics g) {
        int i;
        super.paintComponent(g);
        g.setColor(faceModel.getGridLineColor());
        for (i = 1; i < faceModel.getRow(); i++) {
            g.drawLine(0, i * faceModel.getGridHeigth(), faceModel.getColumn()
                    * faceModel.getGridWidth(), i * faceModel.getGridHeigth());
        }

        for (i = 1; i < faceModel.getColumn(); i++) {
            g.drawLine(i * faceModel.getGridWidth(), 0, i
                    * faceModel.getGridWidth(), faceModel.getRow()
                    * faceModel.getGridHeigth());
        }

    }

    /**
     * 加载图标
     *
     */
    private void loadIcon() {
        int i, j, count=0;
        FaceIconUI faceIconUI;
        URL url;

        for (i = 0; i < faceModel.getRow(); i++) {
            for (j = 0; j < faceModel.getColumn(); j++) {
                url = faceModel.getIconUrl(i, j);
                if (url != null) {
                    faceIconUI = new FaceIconUI(url,
                            faceModel.getGridWidth() - faceModel.getSpace() * 2 - 1,
                            faceModel.getGridHeigth() - faceModel.getSpace() * 2 - 1,
                            faceModel.getImageIconBackColor(),
                            faceModel.getImageIconBorderColor(),
                            faceModel.getImageIconOverBorderColor());

                    faceIconUI.setBounds(j * faceModel.getGridWidth() + 2,
                            i * faceModel.getGridHeigth() + 2,
                            faceIconUI.getWidth(),
                            faceIconUI.getHeight());

                    faceIconUI.setId(count++);
                    faceIconUI.setIconId(i * faceModel.getColumn() + j);
                    faceIconUIList.add(faceIconUI);

                    this.add(faceIconUI);
                }
            }
        }
    }

    /**
     * 为所有图标添加鼠标事件
     */
    public void addMouseListener(MouseListener mouseListener) {
        Iterator it = faceIconUIList.iterator();
        Object o;
        while (it.hasNext()) {
            o = it.next();
            if (o instanceof FaceIconUI) {
                ((FaceIconUI) o).addMouseListener(mouseListener);
            }
        }
    }
}