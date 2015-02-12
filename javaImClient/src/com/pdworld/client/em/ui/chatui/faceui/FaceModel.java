package com.pdworld.client.em.ui.chatui.faceui;

import java.awt.Color;
import java.net.URL;

/**
 * 表情表格的模型接口
 * @author 陈奋威
 * @version 1.0
 */
public interface FaceModel {

    /**
     * 返回列数
     * @return
     */
    public int getColumn();

    /**
     * 返回行数
     * @return
     */
    public int getRow();

    /**
     * 返回rowIndex行, columnInde列的图标
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    public URL getIconUrl(int rowIndex, int columnIndex);

    /**
     * 返回表格的高度
     * @return
     */
    public int getGridHeigth();

    /**
     * 返回表格的宽度
     * @return
     */
    public int getGridWidth();

    /**
     * 返回图标与表格之间的距离
     * @return
     */
    public int getSpace();

    /**
     * 返回表格线的颜色
     * @return
     */
    public Color getGridLineColor();

    /**
     * 返回整个面板的边框
     * @return
     */
    public Color getGridBorderColor();

    /**
     * 返回图标的正常情况下的边框颜色
     * @return
     */
    public Color getImageIconBorderColor();

    /**
     * 返回鼠标经过时的边框颜色
     * @return
     */
    public Color getImageIconOverBorderColor();

    /**
     * 返回表格的背景颜色
     * @return
     */
    public Color getGridBackColor();

    /**
     * 返回图标的背景颜色
     * @return
     */
    public Color getImageIconBackColor();

    /**
     * 返回经过图标的背景颜色
     * @return
     */
    public Color getImageIconOverBackColor();
}
