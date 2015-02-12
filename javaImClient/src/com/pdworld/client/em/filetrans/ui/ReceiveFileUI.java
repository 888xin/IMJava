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
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.pdworld.client.em.filetrans.TransFileStock;
import com.pdworld.client.em.filetrans.TransTask;
import com.pdworld.client.em.filetrans.trans.TransFileServer;
import com.pdworld.client.em.pub.PubValue;
import com.pdworld.client.em.ui.chatui.ChatUI;
import com.pdworld.client.em.ui.images.GetImage;
import com.pdworld.pub.pack.PackOper;
import com.pdworld.pub.pub.PubToolkit;
import com.pdworld.pub.unit.MessagePack;


/**
 * 接收文件面板
 *
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class ReceiveFileUI extends TransFileUI {

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = -5498491880279462921L;

    private JLabel fileInfoLabel = new JLabel();

    private JLabel infoLabel = new JLabel();

    private JButton saveOtherBtn = new JButton("另存为");

    private JButton cancelBtn = new JButton("拒绝");

    private JButton okBtn = new JButton("接受");

    private JButton closeBtn = new JButton("关闭");

    private JProgressBar receiveProBar = new JProgressBar();

    private TransTask transTask;

    private ChatUI chatUI;

    private boolean isCancel = false;

    public ReceiveFileUI(TransTask transTask) {
        this.transTask = transTask;
        init();
    }

    private void setButtonSize(JButton button){
        Dimension dim = new Dimension(50, 23);
        button.setMargin(new Insets(1,1,1,1));
        button.setPreferredSize(dim);
        button.setMaximumSize(dim);
        button.setMinimumSize(dim);
    }

    public void init() {
        this.setButtonSize(saveOtherBtn);
        this.setButtonSize(cancelBtn);
        this.setButtonSize(okBtn);
        this.setButtonSize(closeBtn);

        this.setLayout(new BorderLayout());
        this.add(fileInfoLabel, BorderLayout.NORTH);
        ImageIcon imageIcon = GetImage.getSkinImage("file.gif");
        fileInfoLabel.setHorizontalAlignment(JLabel.LEFT);
        if (imageIcon != null)
            this.fileInfoLabel.setIcon(imageIcon);
        fileInfoLabel.setText("<html>接收文件<br>" + transTask.getSendFile().getName() + "</html>");
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(receiveProBar, BorderLayout.CENTER);
        infoLabel.setHorizontalAlignment(JLabel.RIGHT);
        infoLabel.setText("<html>另存为:"+transTask.getReceiveFile()+"<br>大小:"+getFileLengthStr(transTask.getFileLength())+"<html>");
        panel.add(infoLabel, BorderLayout.SOUTH);
        this.add(panel, BorderLayout.CENTER);
        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.add(panel, BorderLayout.SOUTH);
        panel.add(okBtn);
        panel.add(saveOtherBtn);
        panel.add(cancelBtn);
        panel.add(closeBtn);
        closeBtn.setVisible(false);

        this.add(panel, BorderLayout.SOUTH);
        this.addButtonActionListener(new ReceiveFileUIButtonActionListener(this));
        this.setBorder(BorderFactory.createTitledBorder("接收文件"));
    }

    public TransTask getTransTask() {
        return this.transTask;
    }

    public void setMaxValue(long maxValue){
        this.receiveProBar.setMaximum((int)maxValue);
    }

    public void setTransFinish() {
//		infoLabel.setText("文件传送完毕");
        this.setBorder(BorderFactory.createTitledBorder("文件接收完毕"));
        this.okBtn.setVisible(false);
        this.cancelBtn.setVisible(false);
        this.saveOtherBtn.setVisible(false);
        this.closeBtn.setVisible(true);
    }

    public void setPlan(int plan) {
        receiveProBar.setValue(plan);
    }

    public void setClose() {
        System.out.println("receive close");
        this.chatUI.removeFileUI(this);
    }

    public void setStart() {
        saveOtherBtn.setEnabled(false);
        okBtn.setEnabled(false);
        this.setBorder(BorderFactory.createTitledBorder("开始传送文件..."));
    }

    public void setTransFail(String errorMsg) {
        this.setBorder(BorderFactory.createTitledBorder("出现异常..."));
        //infoLabel.setText("出现异常..");
        this.closeBtn.setVisible(true);
    }

    public boolean isCancel() {
        return isCancel;
    }

    public void setCancel() {
        System.out.println("取消");
        this.isCancel = false;
        this.setClose();
    }

    public void setChatUI(ChatUI chatUI) {
        this.chatUI = chatUI;
    }

    public ChatUI getChatUI() {
        return this.chatUI;
    }

    public void upOtherSave(){
        System.out.println(transTask.getReceiveFile());
        infoLabel.setText("<html>另存为:"+transTask.getReceiveFile()+"<br>大小:"+getFileLengthStr(transTask.getFileLength())+"<html>");
    }

    public String getFileLengthStr(long size) {
        System.out.println("size:" + size);
        if (size <= 1024 * 1024) {
            String str = (size / 1024.0 ) + "";
            if((str.indexOf(".")+3) <= str.length())
                str = str.substring(0, str.indexOf(".")+3);
            return str + " K";
        } else {
            String str = (size / 1024.0 / 1024.0) + "";
            if((str.indexOf(".")+3) <= str.length())
                str = str.substring(0, str.indexOf(".")+3);
            return str + " M";
        }
    }

    public void addButtonActionListener(ActionListener actionListener) {
        saveOtherBtn.addActionListener(actionListener);
        cancelBtn.addActionListener(actionListener);
        okBtn.addActionListener(actionListener);
        closeBtn.addActionListener(actionListener);

        saveOtherBtn.setActionCommand(FileUIConfig.SAVEOTHARBTN);
        cancelBtn.setActionCommand(FileUIConfig.CANCELBTN);
        okBtn.setActionCommand(FileUIConfig.OKBTN);
        closeBtn.setActionCommand(FileUIConfig.CLOSEBTN);
    }

}

class ReceiveFileUIButtonActionListener implements ActionListener {

    private ReceiveFileUI receiveFileUI;

    public ReceiveFileUIButtonActionListener(ReceiveFileUI receiveFileUI) {
        this.receiveFileUI = receiveFileUI;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            String command = e.getActionCommand();
            System.out.println("开始接收-----------------------------------------------"+PubValue.getUser().getName());
            if (command.equals(FileUIConfig.OKBTN)) {
                // 查看文件名是否重名
                System.out.println("进行接受按钮事件");
                TransTask transTask = receiveFileUI.getTransTask();
                if (transTask.getReceiveFile().exists()) {
                    if (!PubToolkit.showYesNo(receiveFileUI.getChatUI(),
                            "保存文件与现有文件重命,是否替换?")) {
                        JFileChooser chooser = new JFileChooser();
                        int returnVal = chooser.showSaveDialog(receiveFileUI);
                        System.out.println("returnVal="+returnVal);
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            transTask.setReceiveFile(chooser.getSelectedFile());
                            receiveFileUI.upOtherSave();
                        }else
                            return;
                    }
                }
                System.out.println("准备建立服务器");
                // 建立服务器
                TransFileServer transFileServer = TransFileServer.getInstance();
                // 发送确认包
                MessagePack msgPack = new MessagePack();
                msgPack.setType(PackOper.UPFILE_FIAT);
                msgPack.setFrom(PubValue.getUser().getId());
                msgPack.setTo(receiveFileUI.getTransTask().getSendId());
                msgPack.setFromIP(transFileServer.getServerIP());
                msgPack.setFromPort(transFileServer.getServerPort());
                msgPack.setMessage(transTask.toString());
                // 送出去
                PubValue.getClientThread().sendMessage(msgPack);
                System.out.println("返回确认包");
            } else if (command.equals(FileUIConfig.CANCELBTN)) {
                System.out.println("CANCEL");
                TransTask transTask = receiveFileUI.getTransTask();
                transTask.getFileUI().setCancel();

                // 发送取消包
                MessagePack msgPack = new MessagePack();
                msgPack.setType(PackOper.UPFILE_DEFUSE);
                msgPack.setFrom(PubValue.getUser().getId());
                msgPack.setTo(receiveFileUI.getTransTask().getSendId());
                msgPack.setMessage(transTask.toString());
                PubValue.getClientThread().sendMessage(msgPack);
                TransFileStock.removeReceiveTrans(transTask.toString());
            } else if (command.equals(FileUIConfig.SAVEOTHARBTN)) {
                TransTask transTask = receiveFileUI.getTransTask();
                JFileChooser chooser = new JFileChooser();
                int returnVal = chooser.showSaveDialog(receiveFileUI);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    transTask.setReceiveFile(chooser.getSelectedFile());
                    receiveFileUI.upOtherSave();
                }
            } else if(command.equals(FileUIConfig.CLOSEBTN)){
                receiveFileUI.getChatUI().removeFileUI(this.receiveFileUI);
            }
        }
    }

}
