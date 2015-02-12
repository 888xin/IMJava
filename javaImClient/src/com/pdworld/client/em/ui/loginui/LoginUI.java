package com.pdworld.client.em.ui.loginui;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.pdworld.client.em.pub.ConfigOper;
import com.pdworld.client.em.pub.PubValue;
import com.pdworld.client.em.thread.ClientThread;
import com.pdworld.client.em.ui.images.GetImage;
import com.pdworld.client.em.ui.mainui.MainUI;
import com.pdworld.client.em.unit.Config;
import com.pdworld.pub.io.MyObjectInputStream;
import com.pdworld.pub.io.MyObjectOutputStream;
import com.pdworld.pub.pack.PackOper;
import com.pdworld.pub.pub.PubToolkit;
import com.pdworld.pub.unit.Message;
import com.pdworld.pub.unit.User;


/**
 *
 * @author Administrator
 *
 * 登录窗口
 */
public class LoginUI extends JFrame implements ActionListener {

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;

    /**
     * 窗口的盒子布局
     */
    private Box loginBox;

    /**
     * 广告面板
     */
    private ADUI adUI = new ADUI();

    /**
     * 输入面板
     */
    private InputUI inputUI = new InputUI();

    /**
     * 按钮面板
     */
    private ButtonUI buttonUI = new ButtonUI();

    /**
     * 配置面板
     */
    private ConfigUI configUI = new ConfigUI();

    /**
     * 构造函数
     *
     */
    public LoginUI() {
        init();
    }

    /**
     * 初始化方法
     *
     */
    public void init() {
		/*
		 * 设置窗口的基本选项
		 */
        // 设置默认关闭
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置处于最上层
        // this.setAlwaysOnTop(true);
        // 设置不可改变大小
        this.setResizable(false);
        // 设置窗口的标题
        this.setTitle(LoginConfig.LOGINTITLE);
        // 设置窗口的ICON
        ImageIcon imageIcon = GetImage.getSkinImage("qqicon.gif");
        if (imageIcon != null) {
            this.setIconImage(imageIcon.getImage());
        }

		/*
		 * 向窗口里装载面板
		 */
        loginBox = Box.createVerticalBox();
        this.getContentPane().add(loginBox, BorderLayout.CENTER);
        loginBox.add(adUI);
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(7, 5, 5, 5));
        Box myBox = Box.createVerticalBox();
        panel.setLayout(new BorderLayout());
        panel.add(myBox, BorderLayout.CENTER);
        panel.setBackground(LoginConfig.BORDERCOLOR);
        loginBox.add(panel);
        myBox.add(inputUI);
        myBox.add(Box.createVerticalStrut(10));
        myBox.add(buttonUI);
        myBox.add(Box.createVerticalStrut(6));
        myBox.add(configUI);

        this.addButtonActionListener(this);
        this.addButtonActionListener(new LoginUIButtonActionListener(this));
        buttonUI.setDefaultButton(this);
        showState();
    }

    /**
     * 设置帐号列表
     *
     * @param set
     *            帐号列表
     */
//	public void setAccountsList(Set set) {
//		inputUI.setAccountsList(set);
//	}

    /**
     * 返回帐号
     *
     * @return
     */
    public String getAccounts() {
        return inputUI.getAccounts();
    }

    /**
     * 返回密码
     *
     * @return
     */
    public String getPassword() {
        return inputUI.getPassword();
    }

    /**
     * 设置地址
     *
     * @param address
     *            十进制点阵IP地址
     */
    public void setAddress(String address) {
        configUI.setAddress(address);
    }

    /**
     * 设置端口
     *
     * @param port
     *            值:大于0小于65536
     */
    public void setPort(int port) {
        configUI.setPort(port);
    }

    /**
     * 返回地址
     *
     * @return 十进制点阵IP地址
     */
    public String getAddress() {
        return configUI.getAddress();
    }

    /**
     * 返回端口
     *
     * @return 值:大于0小于65536
     */
    public int getPort() {
        return configUI.getPort();
    }

    /**
     * 设置是否隐身登录
     *
     * @param b
     *            true 隐身登录,false 显身登录
     */
    public void setHideLoginState(boolean b) {
        inputUI.setHideLoginState(b);
    }

    /**
     * 设置是否自动登录
     *
     * @param b
     *            true 为自动登录,false 为手动登录
     */
    public void setAutoLoignState(boolean b) {
        inputUI.setAutoLoginState(b);
    }

    /**
     * 取得是否隐身登录
     *
     * @return true 隐身登录,false 显身登录
     */
    public boolean getHideLoginState() {
        return inputUI.getHideLoginState();
    }

    /**
     * 取得是否自动登录
     *
     * @return true 为自动登录,false 为手动登录
     */
    public boolean getAutoLoginState() {
        return inputUI.getAutoLoginState();
    }

    /**
     * 为界面上所有按钮添加点击监听
     *
     * @param a
     */
    public void addButtonActionListener(ActionListener a) {
        buttonUI.addButtonActionListener(a);
    }

    /**
     * 窗口状态设置方法
     *
     */
    public void showState() {
        configUI.setVisible(!configUI.isVisible());
        buttonUI.showConfigState();
        this.pack();
        this.setLocationRelativeTo(null);
        this.setLocation(this.getLocation().x, this.getLocation().y - 28);
        this.setVisible(true);
    }

    /*
     * 为界面的按钮添加一些默认的操作
     *
     */
    public void actionPerformed(ActionEvent e) {
        // 如果点击"CONFIGBTN"
        if (((JButton) e.getSource()).getActionCommand().equals(
                LoginConfig.CONFIGBTN)) {
            this.showState();
        }
        // 如果点击"CANCELBTN"
        if (((JButton) e.getSource()).getActionCommand().equals(
                LoginConfig.CANCELBTN)) {
            this.setVisible(false);
        }

    }

    /**
     * 设置配置信息
     * @param config
     */
    public void setConfig(Config config){
        this.configUI.setConfig(config);
    }

    /**
     * 取得配置信息
     * @return
     */
    public Config getConfig(){
        return this.configUI.getConfig();
    }
}

class LoginUIButtonActionListener implements ActionListener {

    private LoginUI loginUI;

    private int loginCount = 0;

    public LoginUIButtonActionListener(LoginUI loginUI) {
        this.loginUI = loginUI;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            String actionString = e.getActionCommand();
            if(actionString.equals(LoginConfig.CANCELBTN)){
                loginUI.setVisible(false);
                loginUI.dispose();
                System.exit(0);
            }
            if (actionString.equals(LoginConfig.LOGINBTN)) {
                if (loginUI.getAccounts() == null
                        || loginUI.getAccounts().equals("")) {
                    PubToolkit.showYes(loginUI, "对不起,请输入用户帐号.");
                    return;
                }
                if (loginUI.getPassword().equals("")) {
                    PubToolkit.showYes(loginUI, "对不起,请输入密码.");
                    return;
                }
                if (loginUI.getAddress() == null) {
                    PubToolkit.showYes(loginUI, "对不起,请输入正确的IP地址,如127.0.0.1");
                    return;
                }
                if (loginUI.getPort() == -1) {
                    PubToolkit.showYes(loginUI, "对不起,请输入端口在0到65535之间.");
                    return;
                }
                Socket socket = null;
                MyObjectOutputStream myObjectOut = null;
                MyObjectInputStream myObjectIn = null;
                try {
                    socket = new Socket(loginUI.getAddress(), loginUI.getPort());
                    myObjectOut = new MyObjectOutputStream(socket
                            .getOutputStream());
                    myObjectIn = new MyObjectInputStream(socket
                            .getInputStream());

                    User user = new User();
                    user.setType(PackOper.LOGIN);
                    user.setId(loginUI.getAccounts());
                    System.out.println("userId"+user.getId());
                    user.setPassword(loginUI.getPassword());
                    System.out.println("passwrod:"+user.getPassword());
                    myObjectOut.writeMessage(user);


                    Message message = null;
                    message = myObjectIn.readMessage();
                    if (message instanceof User) {
                        user = (User) message;
                        System.out.println(user.getType());
                        if (user.getType().equals(PackOper.LOGIN_SUCCEED)) {
                            loginUI.setVisible(false);
                            // 把登录窗口关掉
                            MainUI.getInstance().setUser(user);
                            PubValue.setUser(user);
                            MainUI.getInstance().setVisible(true);
                            // 显示主窗口,并等待加树
                            ClientThread clientThread = new ClientThread(socket);
                            clientThread.start();
                            PubValue.setClientThread(clientThread);
                            ConfigOper.saveConfig(this.loginUI.getConfig());


                        } else if (user.getType().equals(
                                PackOper.LOGIN_DEFEATED)) {
                            PubToolkit.showYes(loginUI, "对不起,登录失败,用户或密码不正确!");
                            loginCount++;
                            if(loginCount == 3){
                                PubToolkit.showYes(loginUI, "对不起,你已经三次登录失败,点'确定'将关闭程序.");
                                System.exit(0);
                            }
                        } else if (user.getType()
                                .equals(PackOper.LOGIN_ONLINED)) {
                            PubToolkit.showYes(loginUI, "对不起,此用户已经在其它地方登录!");
                            loginCount++;
                            if(loginCount == 3){
                                PubToolkit.showYes(loginUI, "对不起,你已经三次登录失败,点'确定'将关闭程序.");
                                System.exit(0);
                            }
                        }
                    } else {
                        // 发送信息不正确,关闭连接
                        if (myObjectIn != null) {
                            try {
                                myObjectIn.close();
                            } catch (IOException e1) {
                                // e1.printStackTrace();
                            }
                        }
                        if (myObjectOut != null) {
                            try {
                                myObjectOut.close();
                            } catch (IOException e1) {
                                // e1.printStackTrace();
                            }
                        }
                        if (socket != null) {
                            try {
                                socket.close();
                            } catch (IOException e1) {
                                // e1.printStackTrace();
                            }
                        }
                    }

                } catch (UnknownHostException e1) {
                    //e1.printStackTrace();
                    PubToolkit.showYes(loginUI, "对不起,连接服务器出错!");
                } catch (IOException e1) {
                    //e1.printStackTrace();
                    PubToolkit.showYes(loginUI, "对不起,连接服务器出错!");
                }
            }
        }
    }
}
