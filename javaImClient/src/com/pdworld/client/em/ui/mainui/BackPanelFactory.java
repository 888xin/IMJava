package com.pdworld.client.em.ui.mainui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class BackPanelFactory extends JPanel {

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;

    public static final int AUTOEXT = 1;

    public static final int NOAUTOEXT = 2;

    public static final int Y_AXIS = 3;

    public static final int X_AXIS = 4;

    public static final int YX_AXIS = BackPanelFactory.Y_AXIS
            + BackPanelFactory.X_AXIS;

    private int auto;

    private int axis;

    private ImageIcon limageIcon;

    private ImageIcon cimageIcon;

    private ImageIcon rimageIcon;

    private int width;

    private int height;

    private BackPanelFactory(int auto, int axis, ImageIcon limageIcon,
                             ImageIcon cimageIcon, ImageIcon rimageIcon) {
        this.auto = auto;
        this.axis = axis;
        if (BackPanelFactory.X_AXIS == this.axis) {
            if (limageIcon != null) {
                this.limageIcon = limageIcon;
                width = limageIcon.getIconWidth();
                height = limageIcon.getIconHeight();
            }
            if (cimageIcon != null) {
                this.cimageIcon = cimageIcon;
                width += cimageIcon.getIconWidth();
                height = cimageIcon.getIconHeight();
            }
            if (rimageIcon != null) {
                this.rimageIcon = rimageIcon;
                width += rimageIcon.getIconWidth();
                height = rimageIcon.getIconHeight();
            }
        } else if (BackPanelFactory.Y_AXIS == this.axis) {
            if (limageIcon != null) {
                this.limageIcon = limageIcon;
                width = limageIcon.getIconWidth();
                height += limageIcon.getIconHeight();
            }
            if (cimageIcon != null) {
                this.cimageIcon = cimageIcon;
                width = cimageIcon.getIconWidth();
                height += cimageIcon.getIconHeight();
            }
            if (rimageIcon != null) {
                this.rimageIcon = rimageIcon;
                width = rimageIcon.getIconWidth();
                height += rimageIcon.getIconHeight();
            }
        } else if (BackPanelFactory.YX_AXIS == this.axis) {
            if (limageIcon != null) {
                this.limageIcon = limageIcon;
                width += limageIcon.getIconWidth();
                height += limageIcon.getIconHeight();
            }
            if (cimageIcon != null) {
                this.cimageIcon = cimageIcon;
                width += cimageIcon.getIconWidth();
                height += cimageIcon.getIconHeight();
            }
            if (rimageIcon != null) {
                this.rimageIcon = rimageIcon;
                width += rimageIcon.getIconWidth();
                height += rimageIcon.getIconHeight();
            }
        }
        this.setPreferredSize(new Dimension(width, height));
    }

    public static JPanel createHorizontalAutoExt(ImageIcon limageIcon,
                                                 ImageIcon cimageIcon, ImageIcon rimageIcon) {
        return new BackPanelFactory(BackPanelFactory.AUTOEXT,
                BackPanelFactory.X_AXIS, limageIcon, cimageIcon, rimageIcon);
    }

    public static JPanel createVerticalAutoExt(ImageIcon limageIcon,
                                               ImageIcon cimageIcon, ImageIcon rimageIcon) {
        return new BackPanelFactory(BackPanelFactory.AUTOEXT,
                BackPanelFactory.Y_AXIS, limageIcon, cimageIcon, rimageIcon);
    }

    public static JPanel createHorizontalNoAutoExt(ImageIcon limageIcon,
                                                   ImageIcon cimageIcon, ImageIcon rimageIcon) {
        return new BackPanelFactory(BackPanelFactory.NOAUTOEXT,
                BackPanelFactory.X_AXIS, limageIcon, cimageIcon, rimageIcon);
    }

    public static JPanel createVerticalNoAutoExt(ImageIcon limageIcon,
                                                 ImageIcon cimageIcon, ImageIcon rimageIcon) {
        return new BackPanelFactory(BackPanelFactory.NOAUTOEXT,
                BackPanelFactory.Y_AXIS, limageIcon, cimageIcon, rimageIcon);
    }

    public static JPanel createVerticalandHorizontal(ImageIcon limageIcon,
                                                     ImageIcon cimageIcon, ImageIcon rimageIcon) {
        return new BackPanelFactory(BackPanelFactory.AUTOEXT,
                BackPanelFactory.YX_AXIS, limageIcon, cimageIcon, rimageIcon);
    }

    private BufferedImage crateHorizontal() {
        BufferedImage buf;
        if (auto == BackPanelFactory.AUTOEXT) {
            buf = new BufferedImage(this.getWidth(), height,
                    BufferedImage.TYPE_3BYTE_BGR);
        } else {
            buf = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        }
        Graphics graphics = buf.getGraphics();
        if (cimageIcon != null) {
            for (int i = 0; i < buf.getWidth(); i += this.cimageIcon
                    .getIconWidth()) {
                graphics.drawImage(this.cimageIcon.getImage(), i, 0, i
                        + this.cimageIcon.getIconWidth(), this.cimageIcon
                        .getIconHeight(), 0, 0, this.cimageIcon.getIconWidth(),
                        this.cimageIcon.getIconHeight(), Color.WHITE, null);
            }
        }

        if (rimageIcon != null) {
            graphics.drawImage(rimageIcon.getImage(), buf.getWidth()
                    - rimageIcon.getIconWidth(), 0, buf.getWidth(), rimageIcon
                    .getIconHeight(), 0, 0, rimageIcon.getIconWidth(),
                    rimageIcon.getIconHeight(), Color.WHITE, null);

            if (limageIcon != null) {
                graphics.drawImage(limageIcon.getImage(), 0, 0, null);
            }

        }
        return buf;
    }

    private BufferedImage createVertical() {
        BufferedImage buf;
        if (auto == BackPanelFactory.AUTOEXT) {
            if (BackPanelFactory.YX_AXIS != this.axis) {
                buf = new BufferedImage(width, this.getHeight(),
                        BufferedImage.TYPE_3BYTE_BGR);
            } else {
                buf = new BufferedImage(width / 3, this.getHeight(),
                        BufferedImage.TYPE_3BYTE_BGR);
            }
        } else if (BackPanelFactory.YX_AXIS != this.axis) {
            buf = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        } else
            buf = new BufferedImage(width / 3, height,
                    BufferedImage.TYPE_3BYTE_BGR);
        Graphics graphics = buf.getGraphics();
        if (cimageIcon != null) {
            for (int i = 0; i < buf.getHeight(); i += this.cimageIcon
                    .getIconHeight()) {
                graphics.drawImage(this.cimageIcon.getImage(), 0, i,
                        this.cimageIcon.getIconWidth(), i
                        + this.cimageIcon.getIconHeight(), 0, 0,
                        this.cimageIcon.getIconWidth(), this.cimageIcon
                        .getIconHeight(), Color.WHITE, null);
            }
        }
        if (rimageIcon != null) {
            graphics.drawImage(rimageIcon.getImage(), 0, buf.getHeight()
                    - rimageIcon.getIconHeight(), buf.getWidth(), buf
                    .getHeight(), 0, 0, rimageIcon.getIconWidth(), rimageIcon
                    .getIconHeight(), Color.WHITE, null);
        }

        if (limageIcon != null) {
            graphics.drawImage(limageIcon.getImage(), 0, 0, null);
        }
        return buf;
    }

    private BufferedImage createVerticalandHorizontal() {
        BufferedImage temp = this.createVertical();

        BufferedImage buf = new BufferedImage(this.getWidth(),
                this.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

        Graphics graphics = buf.getGraphics();

        for (int i = 0; i < buf.getWidth(); i += temp.getWidth()) {
            graphics.drawImage(temp, i, 0, i + temp.getWidth(), temp
                    .getHeight(), 0, 0, temp.getWidth(), temp.getHeight(),
                    Color.WHITE, null);
        }

        return buf;
    }

    public void paintComponent(Graphics g) {
        // super.paintComponent(g);
        BufferedImage bufImage = null;
        switch (this.axis) {
            case BackPanelFactory.X_AXIS:
                bufImage = crateHorizontal();
                break;
            case BackPanelFactory.Y_AXIS:
                bufImage = createVertical();
                break;
            case BackPanelFactory.YX_AXIS:
                bufImage = createVerticalandHorizontal();
                break;
        }
        g.drawImage(bufImage, 0, 0, null);
    }

}