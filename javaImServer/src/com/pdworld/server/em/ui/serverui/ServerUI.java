package com.pdworld.server.em.ui.serverui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import com.pdworld.pub.pub.PubToolkit;
import com.pdworld.server.em.pub.PubValue;
import com.pdworld.server.em.ui.serverui.departmentui.DepartmentUI;
import com.pdworld.server.em.ui.serverui.image.GetImage;
import com.pdworld.server.em.ui.serverui.logui.LogUI;
import com.pdworld.server.em.ui.serverui.onlineui.OnLineUI;
import com.pdworld.server.em.ui.serverui.userui.UserUI;



/**
 * 服务器主窗口
 * @author Administrator
 */
public class ServerUI extends JFrame {

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;

    /**
     * 部门管理面板
     */
    private DepartmentUI departmentUI = new DepartmentUI(this);

    /**
     * 用户管理面板
     */
    private UserUI userUI = new UserUI(this);

    /**
     * 在线用户管理面板
     */
    private OnLineUI onlineUI = new OnLineUI();

    /**
     * 日志管理面板
     */
    private LogUI logUI = new LogUI(this);


    private JTabbedPane tabbedPane = new JTabbedPane();

    /**
     * 采用单一模式
     */
    private static ServerUI serverUI;

    /**
     * 取得单一模式实例的静态方法
     * @return
     */
    public static ServerUI getInstance() {
        if (serverUI == null)
            serverUI = new ServerUI();
        return serverUI;
    }

    /**
     * 受保护的服务主窗口构造函数
     *
     */
    private ServerUI() {
        super("56Pd Talker后台管理");
        init();
    }

    /**
     * 初始代主窗口
     *
     */
    public void init() {
        this.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        ImageIcon imageIcon = GetImage.getSkinImage("set.gif");
        if(imageIcon != null){
            this.setIconImage(imageIcon.getImage());
        }
        this.tabbedPane.add(onlineUI, "在线用户管理");
        this.tabbedPane.add(departmentUI, "部门管理");
        this.tabbedPane.add(userUI, "用户管理");
        this.tabbedPane.add(logUI, "日志管理");

        this.setResizable(false);

        this.setSize(900, 600);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.addWindowListener(new ServerUIWindowListener());
    }

    /**
     * 取得在线用户管理面板
     * @return
     */
    public OnLineUI getOnLineUI() {
        return this.onlineUI;
    }

    /**
     * 取得日志管理面板
     * @return
     */
    public LogUI getLogUI() {
        return this.logUI;
    }

    /**
     * 取得部门管理面板
     * @return 返回 departmentUI。
     */
    public DepartmentUI getDepartmentUI() {
        return departmentUI;
    }

    /**
     * 取得用户管理面板
     * @return 返回 userUI。
     */
    public UserUI getUserUI() {
        return userUI;
    }
}
/**
 * 服务器主窗口的窗口监听类
 * @author Administrator
 */
class ServerUIWindowListener extends WindowAdapter{

    /**
     * 当主窗口关闭时
     */
    public void windowClosing(WindowEvent e) {
        if(!PubToolkit.showYesNo(ServerUI.getInstance(), "真的要关闭服务器吗?"))
            return;
        if(PubValue.getMessageServer() != null){
            PubToolkit.showYes(ServerUI.getInstance(),"消息服务器正在运行中,请先关闭.");
        }else{
            PubValue.removeAll();
            System.exit(0);
        }
    }
}
