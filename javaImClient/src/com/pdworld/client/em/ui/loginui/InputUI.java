package com.pdworld.client.em.ui.loginui;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;
import java.util.Iterator;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 * @author Administrator
 *
 * 输入界面:帐号,密码,自动登录,隐身登录
 *
 */
public class InputUI extends JPanel {

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;

    /**
     * 显示帐号提示
     */
    private JLabel labelAccounts = new JLabel(LoginConfig.ACCOUNTS);

    /**
     * 显示密码提示
     */
    private JLabel labelPassword = new JLabel(LoginConfig.PSWORD);

    /**
     * 输入帐号
     */
    private JTextField cbAccounts = new JTextField();

    /**
     * 输入密码
     */
    private JPasswordField tfPassword = new JPasswordField("");

    /**
     * 自动登录选项
     *
     */
    private JCheckBox cbAutoLogin = new JCheckBox(LoginConfig.AUTOLOGIN);

    /**
     * 隐身登录
     */
    private JCheckBox cbHideLogin = new JCheckBox(LoginConfig.HIDELOGIN);

    /**
     * 初始化输入界面
     *
     */
    public void init() {
        this.setBackground(LoginConfig.INPUTUIBACKCOLOR);
        this.setBorder(BorderFactory
                .createLineBorder(LoginConfig.INPUTUILINECOLOR));

        Dimension dim = new Dimension(LoginConfig.INPUTUIWIDTH,
                LoginConfig.INPUTUIHIGHT);
        this.setPreferredSize(dim);
        this.setMinimumSize(dim);
        this.setMaximumSize(dim);

        Dimension tmpDim = new Dimension(new Dimension(50, 20));
        this.labelAccounts.setPreferredSize(tmpDim);
        this.labelAccounts.setMaximumSize(tmpDim);
        this.labelAccounts.setMinimumSize(tmpDim);

        this.labelPassword.setPreferredSize(tmpDim);
        this.labelPassword.setPreferredSize(tmpDim);
        this.labelPassword.setPreferredSize(tmpDim);

        tmpDim = new Dimension(149, 20);
        this.cbAccounts.setPreferredSize(tmpDim);
        this.cbAccounts.setMaximumSize(tmpDim);
        this.cbAccounts.setMinimumSize(tmpDim);

        tmpDim = new Dimension(149, 18);
        this.tfPassword.setPreferredSize(tmpDim);
        this.tfPassword.setMaximumSize(tmpDim);
        this.tfPassword.setMinimumSize(tmpDim);

        Insets tmpInsets = new Insets(2, 2, 2, 2);
        cbAccounts.setFont(new Font("宋体", Font.PLAIN, 12));
        cbAccounts.setEditable(true);

        tfPassword.setMargin(tmpInsets);
        tfPassword.setFont(new Font("宋体", Font.PLAIN, 12));

        this.cbAutoLogin.setOpaque(false);
        this.cbHideLogin.setOpaque(false);

        Box myBox = Box.createVerticalBox();
        this.setLayout(new BorderLayout());
        this.add(myBox, BorderLayout.CENTER);

        myBox.add(Box.createVerticalStrut(12));

        JPanel tmpPanel = new JPanel();
        tmpPanel.setOpaque(false);// 设置成透明就不用设置背景了,背景就跟主面板一起
        tmpDim = new Dimension(LoginConfig.INPUTUIWIDTH, 30);
        tmpPanel.setPreferredSize(tmpDim);
        tmpPanel.setMaximumSize(tmpDim);
        tmpPanel.setMinimumSize(tmpDim);

        tmpPanel.add(labelAccounts);
        tmpPanel.add(cbAccounts);

        myBox.add(tmpPanel);

        myBox.add(Box.createVerticalStrut(7));

        tmpPanel = new JPanel();
        tmpPanel.setOpaque(false);
        tmpDim = new Dimension(LoginConfig.INPUTUIWIDTH, 30);
        tmpPanel.setPreferredSize(tmpDim);
        tmpPanel.setMaximumSize(tmpDim);
        tmpPanel.setMinimumSize(tmpDim);
        tmpPanel.add(labelPassword);
        tmpPanel.add(tfPassword);

        myBox.add(tmpPanel);

        tmpPanel = new JPanel();
        tmpPanel.setOpaque(false);
        tmpDim = new Dimension(LoginConfig.INPUTUIWIDTH, 28);
        tmpPanel.setPreferredSize(tmpDim);
        tmpPanel.setMaximumSize(tmpDim);
        tmpPanel.setMinimumSize(tmpDim);
        //tmpPanel.add(cbAutoLogin);
        //tmpPanel.add(cbHideLogin);

        myBox.add(tmpPanel);
    }

    /**
     * 输入界面的构造函数
     *
     */
    public InputUI() {
        init();
    }

    /**
     * 取得是否自动登录
     *
     * @return
     */
    public boolean getAutoLoginState() {
        return cbAutoLogin.isSelected();
    }

    /**
     * 取得是否隐身登录
     *
     * @return
     */
    public boolean getHideLoginState() {
        return cbHideLogin.isSelected();
    }

    /**
     * 设置是否自动登录
     *
     * @param b
     */
    public void setAutoLoginState(boolean b) {
        cbAutoLogin.setSelected(b);
    }

    /**
     * 设置是否隐身登录
     *
     * @param b
     */
    public void setHideLoginState(boolean b) {
        cbHideLogin.setSelected(b);
    }

    /**
     * 取得账号
     */
    public String getAccounts() {
        return (String) cbAccounts.getText();
    }

    /**
     * 取得密码
     *
     * @return
     */
    public String getPassword() {
        return new String(tfPassword.getPassword());
    }

    /**
     * 设置帐号列表
     *
     * @param set
     */
//	public void setAccountsList(Set set) {
//		cbAccounts.removeAllItems();
//		if (set != null) {
//			Iterator it = set.iterator();
//			while (it.hasNext()) {
//				cbAccounts.addItem(it.next());
//			}
//		}
//	}
}