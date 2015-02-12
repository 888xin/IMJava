package com.pdworld.client.em.filetrans.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.pdworld.client.em.filetrans.TransFileStock;
import com.pdworld.client.em.filetrans.TransTask;
import com.pdworld.client.em.pub.PubValue;
import com.pdworld.client.em.ui.chatui.ChatUI;
import com.pdworld.client.em.ui.images.GetImage;
import com.pdworld.pub.pack.PackOper;
import com.pdworld.pub.unit.MessagePack;
import com.pdworld.pub.unit.User;


/**
 * 发送文件面板
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class SendFileUI extends TransFileUI {

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1243643737636753369L;

    private JLabel fileInfoLabel = new JLabel();

    private JLabel infoLabel = new JLabel();

    private JButton cancelBtn = new JButton("取消");

    private JButton closeBtn = new JButton("关闭");

    private JProgressBar sendProBar = new JProgressBar();

    private boolean isCancel = false;

    private TransTask transTask;

    private ChatUI chatUI;

    public SendFileUI(TransTask transTask) {
        this.transTask = transTask;
        init();
    }

    public void setChatUI(ChatUI chatUI) {
        this.chatUI = chatUI;
    }

    public TransTask getTransTask() {
        return this.transTask;
    }

    public void init() {
        this.setButtonSize(cancelBtn);
        this.setButtonSize(closeBtn);

        this.setLayout(new BorderLayout());
        this.add(fileInfoLabel, BorderLayout.NORTH);
        ImageIcon imageIcon = GetImage.getSkinImage("file.gif");
        fileInfoLabel.setHorizontalAlignment(JLabel.LEFT);
        if (imageIcon != null)
            this.fileInfoLabel.setIcon(imageIcon);
        fileInfoLabel.setText("<html>发送文件<br>"
                + transTask.getSendFile().getName() + "</html>");
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(sendProBar, BorderLayout.CENTER);
        infoLabel.setHorizontalAlignment(JLabel.RIGHT);
        infoLabel.setText("大小:" + getFileLengthStr(transTask.getFileLength()));
        panel.add(infoLabel, BorderLayout.SOUTH);
        this.add(panel, BorderLayout.CENTER);
        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.add(panel, BorderLayout.SOUTH);
        panel.add(cancelBtn);
        panel.add(closeBtn);
        closeBtn.setVisible(false);
        this.add(panel, BorderLayout.SOUTH);
        this.addButtonActionListener(new SendFileUIBtnActionListener(this));
        this.setBorder(BorderFactory.createTitledBorder("传送文件"));

    }

    private void setButtonSize(JButton button) {
        Dimension dim = new Dimension(50, 23);
        button.setMargin(new Insets(1, 1, 1, 1));
        button.setPreferredSize(dim);
        button.setMaximumSize(dim);
        button.setMinimumSize(dim);
    }

    public void setMaxValue(long maxValue) {
        this.sendProBar.setMaximum((int) maxValue);
    }

    public void setTransFinish() {
        this.setBorder(BorderFactory.createTitledBorder("文件传送完毕"));
        this.cancelBtn.setVisible(false);
        this.closeBtn.setVisible(true);
    }

    public void setPlan(int plan) {
        sendProBar.setValue(plan);
    }

    public void setClose() {
        this.chatUI.removeFileUI(this);
    }

    public void setStart() {
        this.setBorder(BorderFactory.createTitledBorder("开始传送..."));
    }

    public void setTransFail(String errorMsg) {
        this.setBorder(BorderFactory.createTitledBorder("传送出现异常..."));
        this.closeBtn.setVisible(true);
    }

    public boolean isCancel() {
        return isCancel;
    }

    public void setCancel() {
        this.isCancel = true;
    }

    public String getFileLengthStr(long size) {
        System.out.println("size:" + size);
        if (size <= 1024 * 1024) {
            String str = (size / 1024.0) + "";
            if ((str.indexOf(".") + 3) <= str.length())
                str = str.substring(0, str.indexOf(".") + 3);
            return str + " K";
        } else {
            String str = (size / 1024.0 / 1024.0) + "";
            if ((str.indexOf(".") + 3) <= str.length())
                str = str.substring(0, str.indexOf(".") + 3);
            return str + " M";
        }
    }

    public ChatUI getChatUI() {
        return chatUI;
    }

    public void addButtonActionListener(ActionListener actionListener) {
        cancelBtn.addActionListener(actionListener);
        cancelBtn.setActionCommand(FileUIConfig.CANCELBTN);
        closeBtn.addActionListener(actionListener);
        closeBtn.setActionCommand(FileUIConfig.CLOSEBTN);
    }
}

class SendFileUIBtnActionListener implements ActionListener {

    private SendFileUI sendFileUI;

    public SendFileUIBtnActionListener(SendFileUI sendFileUI) {
        this.sendFileUI = sendFileUI;
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals(FileUIConfig.CANCELBTN)) {
            sendFileUI.setClose();
            TransFileStock.removeSendTrans(sendFileUI.getTransTask().toString());
            MessagePack msgPack = new MessagePack();

            msgPack.setType(PackOper.SEND_DEFUSE);
            msgPack.setFrom(PubValue.getUser().getId());
            msgPack.setTo(((User)sendFileUI.getChatUI().getObject()).getId());
            msgPack.setMessage(sendFileUI.getTransTask().toString());
            // 送出去
            PubValue.getClientThread().sendMessage(msgPack);


        } else if (command.equals(FileUIConfig.CLOSEBTN)) {
            sendFileUI.getChatUI().removeFileUI(sendFileUI);
        }
    }
}