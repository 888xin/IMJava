package com.pdworld.client.em.ui.images;

import java.net.URL;

import javax.swing.ImageIcon;

/**
 * @author Administrator
 *
 * 专门获取图片的工具类
 */
public class GetImage {

    /**
     * 返回图片
     * @param fileName
     * @return
     */
    public static ImageIcon getImage(String fileName) {
        URL url = GetImage.class.getResource(fileName);
        if (url != null)
            return new ImageIcon(url);
        return null;
    }

    /**
     * 返回外观图片
     * @param fileName
     * @return
     */
    public static ImageIcon getSkinImage(String fileName) {
        return GetImage.getImage("skin/" + fileName);
    }

    /**
     * 返回表情图片,根据文件名
     * @param iconId
     * @return
     */
    public static ImageIcon getFace(int iconId) {
        return GetImage.getImage("face/" + iconId + ".gif");
    }

    /**
     * 返回表格图片,根据编号
     * @param fileName
     * @return
     */
    public static ImageIcon getFace(String fileName) {
        return GetImage.getImage("face/" + fileName);
    }

    /**
     * 返回小头像图片,根据编号
     * @param iconId
     * @return
     */
    public static ImageIcon getMinHead(int iconId){
        return GetImage.getImage("head/" + iconId + "_m.gif");
    }

    /**
     * 返回大头像图片,根据编号
     * @param iconId
     * @return
     */
    public static ImageIcon getBigHead(int iconId){
        return GetImage.getImage("head/" + iconId + ".gif");
    }

    /**
     * 返回不在线头像图片,根据编号
     * @param iconId
     * @return
     */
    public static ImageIcon getGrayHead(int iconId){
        return GetImage.getImage("head/gray/" + iconId + "_m_gray.gif");
    }

    /**
     * 返回表情的URL
     * @param iconId
     * @return
     */

    public static URL getFaceUrl(int iconId){
        return GetImage.class.getResource("face/" + iconId +".gif");
    }

}