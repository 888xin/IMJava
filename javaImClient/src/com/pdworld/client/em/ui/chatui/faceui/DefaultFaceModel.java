package com.pdworld.client.em.ui.chatui.faceui;

import java.awt.Color;
import java.net.URL;

import com.pdworld.client.em.ui.images.GetImage;


/**
 * 表情表格默认模型
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class DefaultFaceModel implements FaceModel {

    private final int cols = 15;

    private final int rows = 8;

    /**
     * 返回表情表格的列数
     */
    public int getColumn() {
        return cols;
    }

    /**
     * 返回表情表格的行数
     */
    public int getRow() {
        return rows;
    }

    /**
     * 取得某个表情的URL
     */
    public URL getIconUrl(int rowIndex, int columnIndex) {
        return  GetImage.getFaceUrl(rowIndex*cols+columnIndex);
    }

    /**
     * 取得每个方格高度
     */
    public int getGridHeigth() {
        return 27;
    }

    /**
     * 取得每个方格的宽度
     */
    public int getGridWidth() {
        return 27;
    }

    /**
     * 取得每个方格的间隔
     */
    public int getSpace() {
        return 1;
    }

    /**
     * 取得方格的线条颜色
     */
    public Color getGridLineColor() {
        return new Color(223, 230, 246);
    }

    /**
     * 取得整个表格的边框颜色
     */
    public Color getGridBorderColor() {
        return new Color(49, 106, 196);
    }

    /**
     * 取得方格的边框颜色
     */
    public Color getImageIconBorderColor() {
        return Color.WHITE;
    }

    /**
     * 取得方格的背景颜色
     */
    public Color getGridBackColor() {
        return Color.WHITE;
    }

    /**
     * 取得方格中表情的背景颜色
     */
    public Color getImageIconBackColor() {
        return Color.WHITE;
    }

    /**
     * 取得鼠标经过时方格边框颜色
     */
    public Color getImageIconOverBorderColor() {
        return Color.BLUE;
    }

    /**
     * 取得鼠标经过时方格背景颜色
     */
    public Color getImageIconOverBackColor() {
        return Color.WHITE;
    }
}
