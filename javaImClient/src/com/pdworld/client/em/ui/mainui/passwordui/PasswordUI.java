package com.pdworld.client.em.ui.mainui.passwordui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import com.pdworld.client.em.pub.PubValue;
import com.pdworld.pub.pack.PackOper;
import com.pdworld.pub.pub.PubToolkit;
import com.pdworld.pub.unit.MessagePack;


/**
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class PasswordUI extends JDialog implements ActionListener {

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;

    private JPasswordField oldPassword = new JPasswordField();;

    private JPasswordField newPassword1 = new JPasswordField();

    private JPasswordField newPassword2 = new JPasswordField();

    private JButton saveButton;

    private JButton closeButton;

    public PasswordUI(JFrame mainFrame) {
        super(mainFrame, "修改密码", true);
        init();
    }

    public void init() {
        this.getContentPane().setLayout(
                new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        Dimension size = new Dimension(100, 20);
        oldPassword.setPreferredSize(size);
        newPassword1.setPreferredSize(size);
        newPassword2.setPreferredSize(size);

        JPanel myPanel = new JPanel();
        JLabel myLabel = new JLabel("请输入旧密码:");
        myPanel.add(myLabel);
        myPanel.add(oldPassword);
        this.getContentPane().add(myPanel);

        myPanel = new JPanel();
        myLabel = new JLabel("请输入新密码:");
        myPanel.add(myLabel);
        myPanel.add(newPassword1);
        this.getContentPane().add(myPanel);

        myPanel = new JPanel();
        myLabel = new JLabel("请确认新密码:");
        myPanel.add(myLabel);
        myPanel.add(newPassword2);
        this.getContentPane().add(myPanel);

        myPanel = new JPanel();

        saveButton = new JButton("保存");
        closeButton = new JButton("取消");

        myPanel.add(saveButton);
        myPanel.add(closeButton);

        this.getContentPane().add(myPanel);

        closeButton.addActionListener(this);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addButtonActionListener(new PasswordButtonActionListener(this));

    }

    public void addButtonActionListener(ActionListener actionListener) {
        this.saveButton.addActionListener(actionListener);
        this.closeButton.addActionListener(actionListener);
    }

    public String getOldPassword() {
        return new String(this.oldPassword.getPassword());
    }

    public String getNewPassword1() {
        return new String(this.newPassword1.getPassword());
    }

    public String getNewPassword2() {
        return new String(this.newPassword2.getPassword());
    }

    public void actionPerformed(ActionEvent e) {
        this.dispose();
    }

    public void addSaveActionListener(ActionListener actionListener) {
        saveButton.addActionListener(actionListener);
    }
}

class PasswordButtonActionListener implements ActionListener {

    private PasswordUI passwordUI;

    public PasswordButtonActionListener(PasswordUI passwordUI) {
        this.passwordUI = passwordUI;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            String command = e.getActionCommand();
            if (command.equals("保存")) {
                if (passwordUI.getOldPassword().equals("")) {
                    PubToolkit.showInformation("对不起,请输入原密码.");
                    return;
                } else if (!passwordUI.getOldPassword().equals(
                        PubValue.getUser().getPassword())) {
                    System.out.println(passwordUI.getOldPassword());
                    System.out.println(PubValue.getUser().getPassword());
                    PubToolkit.showInformation("对不起,原密码输入不正确,请重新输入.");
                    return;
                } else if (!passwordUI.getNewPassword1().equals(
                        passwordUI.getNewPassword2())) {
                    PubToolkit.showInformation("对不起,新密码两次输入不一样,请重新输入.");
                    return;
                }else if (passwordUI.getNewPassword1().equals("")
                        || passwordUI.getNewPassword2().equals("")) {
                    PubToolkit.showInformation("对不起,新密码不能为空,请重新输入.");
                    return;
                }
                //发送密码包
                MessagePack msgPack = new MessagePack();
                msgPack.setType(PackOper.UPPASSWORD);
                //原密码
                msgPack.setFrom(PubValue.getUser().getPassword());
                //新密码
                msgPack.setTo(passwordUI.getNewPassword1());
                PubValue.getClientThread().sendMessage(msgPack);
                passwordUI.setVisible(false);
                passwordUI.dispose();
            } else {
                if (PubToolkit.showYesNo(passwordUI, "真的要取消吗?")) {
                    passwordUI.setVisible(false);
                    passwordUI.dispose();
                }
            }

        }
    }
}