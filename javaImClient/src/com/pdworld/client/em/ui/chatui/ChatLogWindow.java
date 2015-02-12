package com.pdworld.client.em.ui.chatui;

import java.awt.BorderLayout;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JWindow;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;

import com.pdworld.client.em.ui.images.GetImage;


/**
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class ChatLogWindow extends JWindow {

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;

    private static String messCompart = "\u0009";

    private JTextPane chatLogText = new JTextPane();

    public ChatLogWindow() {
        init();
    }

    public void init() {
        JScrollPane jsp = new JScrollPane(chatLogText);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(jsp);
    }

    public void setWidth(int width) {
        this.setSize(width, this.getHeight());
    }

    public void setChatLog(String str) {
        this.chatLogText.setText(str);
    }

    public void setMessage(List list) {
        Iterator it = list.iterator();
        String bufMsg = null;
        String speakname = null;
        String speakid = null;
        String date = null;
        String msg = null;

        int start = 0;
        Document chatDocument = chatLogText.getDocument();
        while (it.hasNext()) {
            bufMsg = (String) it.next();
            //System.out.println(bufMsg);
            try {
                start = bufMsg.indexOf("[SPEAKNAME=")+("[SPEAKNAME=").length();
                speakname = bufMsg.substring(start, bufMsg.indexOf("]", start));
                start = bufMsg.indexOf("[SPEAKID=")+("[SPEAKID=").length();
                speakid = bufMsg.substring(start, bufMsg.indexOf("]", start));
                start = bufMsg.indexOf("[DATE=")+("[DATE=").length();
                date = bufMsg.substring(start, bufMsg.indexOf("]", start));
                start = bufMsg.indexOf("[MESSAGE=")+("[MESSAGE=").length();
                msg = bufMsg.substring(start, bufMsg.lastIndexOf("]"));

                try {
                    chatDocument.insertString(chatDocument.getLength(),
                            speakname + "(" + speakid + ")  " + date + "\n",
                            null);
                    unbindMessage(msg);
                } catch (BadLocationException e) {
                    //e.printStackTrace();
                }
            } catch (Exception e) {

            }
        }
    }

    private void unbindMessage(String str) {
        Document chatDocument = chatLogText.getDocument();
        String[] mess = str.split(messCompart);
        int start = 0;
        StyledDocument styledDocument = this.chatLogText.getStyledDocument();
        try {
            styledDocument.insertString(chatDocument.getLength(), "  ", null);
        } catch (BadLocationException e1) {
            //e1.printStackTrace();
        }
        for (int i = 0; i < mess.length; i++) {
            this.chatLogText.setCaretPosition(styledDocument.getLength());
            if (mess[i].indexOf("[MESSAGE=") >= 0) {
                start = mess[i].indexOf("[MESSAGE=") + "[MESSAGE=".length();
                try {
                    styledDocument.insertString(chatDocument.getLength(),
                            mess[i].substring(start, mess[i]
                                    .indexOf("]", start)), null);
                } catch (BadLocationException e) {
                    //e.printStackTrace();
                }
            } else if (mess[i].indexOf("[IMAGE=") >= 0) {
                start = mess[i].indexOf("[IMAGE=") + "[IMAGE=".length();
                ImageIcon imageIcon = GetImage.getFace(mess[i].substring(start,
                        mess[i].indexOf("]", start)).trim());
                if (imageIcon != null)
                    chatLogText.insertIcon(imageIcon);
            }
        }
    }

}