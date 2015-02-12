package com.pdworld.client.em.ui.mainui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.tree.TreePath;

import com.pdworld.client.em.pub.PubValue;
import com.pdworld.client.em.ui.chatui.ChatUI;
import com.pdworld.client.em.ui.images.GetImage;
import com.pdworld.client.em.ui.mainui.passwordui.PasswordUI;
import com.pdworld.client.em.ui.mainui.usertree.MyTreeModel;
import com.pdworld.client.em.ui.mainui.usertree.UserTree;
import com.pdworld.pub.pack.PackOper;
import com.pdworld.pub.pub.PubToolkit;
import com.pdworld.pub.unit.User;


/**
 * 客户端主窗口 
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class MainUI extends JFrame {

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;

    /**
     * 标题面板
     */
    private TitleUI titleUI = new TitleUI();

    /**
     * 用户树面板
     */
    private UserTree userTree;

    private JScrollPane userTreeJSP;

    private JLabel rigthdownLabel;

    /**
     * 系统消息窗口
     */
    private MessageUI messageUI = new MessageUI(this);

    private Dimension minSize = new Dimension(216, 500);

    /**
     * 用户信息
     */
    private User user;

    private JButton userConfigBtn = new JButton("用户设置");

    private JButton sysMessageBtn = new JButton(MainUIConfig.MESSAGEUITITLE);
//	private SystemTray systemTray;我晕，JDK1.5不支持托盘

    /**
     * 采用单一模式
     */
    private static MainUI mainUI;

    private MainUI() {
        super(MainUIConfig.TITLE);
        init();
    }

    public static MainUI getInstance() {
        if (mainUI == null) {
            mainUI = new MainUI();
        }
        return mainUI;
    }

    /**
     * 初始化
     *
     */
    public void init() {
        this.userTree = new UserTree(new MyTreeModel());
        this.setUndecorated(true);

        userConfigBtn.setOpaque(false);
        sysMessageBtn.setOpaque(false);
        ImageIcon imageIcon = GetImage.getSkinImage(MainUIConfig.MAINICON);

        if (imageIcon != null)
            this.setIconImage(imageIcon.getImage());

        this.getContentPane().add(titleUI, BorderLayout.NORTH);

        JPanel leftPanel = BackPanelFactory.createVerticalAutoExt(GetImage
                .getSkinImage("leftcenter.gif"), GetImage
                .getSkinImage("leftcenter.gif"), GetImage
                .getSkinImage("leftcenter.gif"));

        JPanel rigthPanel = BackPanelFactory.createVerticalAutoExt(GetImage
                .getSkinImage("back.gif"), GetImage.getSkinImage("back.gif"),
                GetImage.getSkinImage("back.gif"));

        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BorderLayout());

        JPanel treePanel = new JPanel();
        treePanel.setOpaque(false);
        treePanel.setLayout(new BorderLayout());

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BorderLayout());
        btnPanel.add(userConfigBtn, BorderLayout.NORTH);
        btnPanel.add(sysMessageBtn, BorderLayout.SOUTH);

        treePanel.add(btnPanel, BorderLayout.NORTH);

        userTreeJSP = new JScrollPane(userTree);
        treePanel.add(userTreeJSP, BorderLayout.CENTER);

        tempPanel.setBackground(Color.WHITE);
        tempPanel.add(leftPanel, BorderLayout.WEST);
        tempPanel.add(treePanel, BorderLayout.CENTER);
        tempPanel.add(rigthPanel, BorderLayout.EAST);

        this.getContentPane().add(tempPanel, BorderLayout.CENTER);
        tempPanel = BackPanelFactory.createHorizontalAutoExt(GetImage
                .getSkinImage("ToolBarBackgroundL.gif"), GetImage
                .getSkinImage("ToolBarBackgroundC.gif"), GetImage
                .getSkinImage("ToolBarBackgroundR.gif"));

        this.getContentPane().add(tempPanel, BorderLayout.SOUTH);

        tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.X_AXIS));
        tempPanel.add(Box.createHorizontalGlue());
        rigthdownLabel = new JLabel();
        rigthdownLabel.setIcon(GetImage.getSkinImage("ToolBarBackgroundR.gif"));

        tempPanel.add(rigthdownLabel);

        MainUIMouseInputListener mainUIMouseInputListener = new MainUIMouseInputListener(
                this);
        rigthdownLabel.addMouseListener(mainUIMouseInputListener);
        rigthdownLabel.addMouseMotionListener(mainUIMouseInputListener);

        this.addMouseMotionListener(mainUIMouseInputListener);
        this.addMouseListener(mainUIMouseInputListener);
        this.addButtonActionListener(new MainUIButtonActionListener(this));
        this.addUserTreeMouseListener(new MainUIUserTreeListener(this));
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter(){

            public void windowClosing(WindowEvent e){
                if (PubToolkit.showYesNo(mainUI, "真的要退出程序吗?")) {
                    // 发送下线包,退出
                    try {
                        if (PubValue.getClientThread().isAlive()) {
                            User user = PubValue.getUser();
                            user.setType(PackOper.DOWN_LINE);
                            PubValue.getClientThread().sendMessage(user);
                            try {
                                Thread.sleep(50);
                            } catch (Exception e1) {

                            }
                            PubValue.getClientThread().setStop();
                        }
                        PubValue.closeAllChatUI();
                        mainUI.setVisible(false);
                        mainUI.dispose();
                    } catch (Exception e1) {

                    } finally {
                        System.exit(0);
                    }
                }
            }
        });
    }

    /**
     * 添加按钮监听
     * @param actionListener
     */
    public void addButtonActionListener(ActionListener actionListener) {
        titleUI.addActionListener(actionListener);
        userConfigBtn.addActionListener(actionListener);
        sysMessageBtn.addActionListener(actionListener);
        userConfigBtn.setActionCommand(MainUIConfig.USERCONFIGBTN);
        sysMessageBtn.setActionCommand(MainUIConfig.SYSMESSAGEBTN);
    }

    public JLabel getRigthdownLabel() {
        return rigthdownLabel;
    }

    public Dimension getMinSize() {
        return minSize;
    }

    /**
     * 设置用户信息
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
        this.titleUI.setHead(user.getIconId());
        this.titleUI.setId(user.getId());
        this.titleUI.setText(user.getName());
    }

    /**
     * 取得用户信息
     * @return User
     */
    public User getUser() {
        return this.user;
    }

    /**
     * 取得用户树
     * @return
     */
    public UserTree getUserTree() {
        return this.userTree;
    }

    /**
     * 添加系统消息
     * @param msg
     */
    public void addMessage(String msg) {
        this.messageUI.addMessage(msg);
        sysMessageBtn.setText(MainUIConfig.MESSAGEUITITLE + "(你有新消息)");
    }

    /**
     * 显示系统消息窗口
     *
     */
    public void showMessage() {
        sysMessageBtn.setText(MainUIConfig.MESSAGEUITITLE);
        this.messageUI.setVisible(true);
    }

    public void setVisible(boolean isVisible) {
        if (isVisible) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            this.setSize((int) super.getPreferredSize().getWidth(),
                    (int) screenSize.getHeight() - 100);
            this.setLocation(screenSize.width - this.getWidth() - 10, 10);
        }
        super.setVisible(isVisible);
    }

    /**
     * 添加用户树鼠标监听
     * @param mouseListener
     */
    public void addUserTreeMouseListener(MouseListener mouseListener) {
        userTree.addMouseListener(mouseListener);
    }

}

/**
 * 对主窗口的员工树的操作
 *
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
class MainUIUserTreeListener extends MouseAdapter {

    private MainUI mainUI;

    public MainUIUserTreeListener(MainUI mainUI) {
        this.mainUI = mainUI;
    }

    public void mousePressed(MouseEvent e) {
        // 如果双击树节点
        if (e.getClickCount() == 2) {
            TreePath treePath = mainUI.getUserTree().getClosestPathForLocation(
                    e.getX(), e.getY());
            if (treePath != null) {
                Object object = treePath.getLastPathComponent();
                if (object != null) {
                    if (object instanceof User) {
                        User user = (User) object;
                        if (user.getIsOnline() == 0) {
                            PubToolkit.showInformation("对不起,不能与离线用户聊天!");
                            return;
                        } else if (user.equals(PubValue.getUser())) {
                            PubToolkit.showYes(mainUI, "对不起,不能与自己聊天");
                            return;
                        }
                    }
                    ChatUI chatUI = PubValue.getChatUIForObject(object);
                    if (chatUI == null) {
                        chatUI = new ChatUI(object);
                        PubValue.addChatUI(chatUI);
                    }
                    chatUI.setLocationRelativeTo(null);
                    chatUI.setVisible(true);

                    chatUI.requestFocus();
                    chatUI.toFront();
                }
            }
        }
    }
}
/**
 * 客户端窗口按钮监听类
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
class MainUIButtonActionListener implements ActionListener {

    private MainUI mainUI;

    public MainUIButtonActionListener(MainUI mainUI) {
        this.mainUI = mainUI;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            String command = e.getActionCommand();
            if (command.equals(MainUIConfig.CLOSEBTN)) {
                // 点关闭,表示下线
                // 显示对话框
                if (PubToolkit.showYesNo(mainUI, "真的要退出程序吗?")) {
                    // 发送下线包,退出
                    try {
                        if (PubValue.getClientThread().isAlive()) {
                            User user = PubValue.getUser();
                            user.setType(PackOper.DOWN_LINE);
                            PubValue.getClientThread().sendMessage(user);
                            try {
                                Thread.sleep(50);
                            } catch (Exception e1) {

                            }
                            PubValue.getClientThread().setStop();
                        }
                        PubValue.closeAllChatUI();
                        mainUI.setVisible(false);
                        mainUI.dispose();
                    } catch (Exception e1) {

                    } finally {
                        System.exit(0);
                    }
                }
            } else if (command.equals(MainUIConfig.USERCONFIGBTN)) {
                PasswordUI passwordUI = new PasswordUI(mainUI);
                passwordUI.setVisible(true);
            } else if (command.equals(MainUIConfig.SYSMESSAGEBTN)) {
                mainUI.showMessage();
            }
        }
    }

}