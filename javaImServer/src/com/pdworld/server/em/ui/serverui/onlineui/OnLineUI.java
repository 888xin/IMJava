package com.pdworld.server.em.ui.serverui.onlineui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.pdworld.pub.pack.PackOper;
import com.pdworld.pub.pub.Parameter;
import com.pdworld.pub.pub.PubToolkit;
import com.pdworld.pub.unit.Company;
import com.pdworld.pub.unit.Department;
import com.pdworld.pub.unit.MessagePack;
import com.pdworld.pub.unit.User;
import com.pdworld.server.em.ServerMain;
import com.pdworld.server.em.dao.factory.UserDaoFactory;
import com.pdworld.server.em.pub.LogOper;
import com.pdworld.server.em.pub.PubValue;
import com.pdworld.server.em.service.ClientThread;
import com.pdworld.server.em.service.MessageServer;
import com.pdworld.server.em.ui.serverui.ServerUI;
import com.pdworld.server.em.ui.serverui.userui.TablePanel;
import com.pdworld.server.em.ui.serverui.userui.UserTableModel;


/**
 * 在线用户面板
 *
 */
public class OnLineUI extends JPanel {

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;

    private BottomPane bottomPane = new BottomPane();

    private TopPanel topPanel = new TopPanel();

    private TablePanel tablePanel;

    private UserTableModel userTableModel;

    public OnLineUI() {
        init();
    }

    public void init() {
        userTableModel = new UserTableModel(UserDaoFactory.getUserDao()
                .getColumnNames(), UserDaoFactory.getUserDao().selectOnLine());
        tablePanel = new TablePanel(userTableModel);
        tablePanel.setBorder(BorderFactory.createTitledBorder("在线用户"));

        this.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);
        this.add(tablePanel, BorderLayout.CENTER);
        this.add(bottomPane, BorderLayout.SOUTH);
        this.addButtonActionListener(new OnLineButtonActionListener(this));
    }

    /**
     * 取得被选中的所有在线用户
     *
     * @return
     */
    public String[] getSelectId() {
        return this.tablePanel.getSelectId();
    }

    /**
     * 按钮监听
     *
     * @param a
     */
    public void addButtonActionListener(ActionListener a) {
        topPanel.addButtonActionListener(a);
        bottomPane.addButtonActionListener(a);
    }

    /**
     * 更新在线用户列表
     */
    public void updateOnLine() {
        HashMap hm = new HashMap();
        hm.put("U_ISONLINE", "" + 1);
        this.tablePanel.update(hm);
        this.updateUI();
    }

    /**
     * 取得要发送的信息
     *
     * @return
     */
    public String getMessage() {
        return this.bottomPane.getMessage();
    }

    /**
     * 消除发送的信息
     *
     */
    public void clear() {
        this.bottomPane.clear();
    }

    /**
     * 设置消息服务器按钮状态
     *
     * @param type
     */
    public void setServerBtn(String type) {
        this.topPanel.setServerBtn(type);
    }

    /**
     * 取得信息发送对象
     *
     * @return
     */
    public Object getSendObject() {
        return bottomPane.getSendObject();
    }

    /**
     * 初始化发送对象
     *
     */
    public void initSendCom() {
        this.bottomPane.initSendCom();
    }
}

/**
 * 在线用户按钮监听类
 *
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */

class OnLineButtonActionListener implements ActionListener {

    private OnLineUI onLineUI;

    public OnLineButtonActionListener(OnLineUI onLineUI) {
        this.onLineUI = onLineUI;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            String command = e.getActionCommand();
            if (command.equals(OnLineConfig.STARTSERVERBTN)) {
                try {
                    MessageServer messageServer = new MessageServer(ServerMain.getServerPort());
                    messageServer.start();
                    PubValue.setMessageServer(messageServer);
                    this.onLineUI.setServerBtn(OnLineConfig.STOPSERVERBTN);
                    System.out.println("消息服务启动成功");
                    ServerUI.getInstance().getLogUI().addLog(
                            LogOper.getInstance().insertStartServerLog());
                } catch (IOException e1) {
                    ServerUI.getInstance().getLogUI().addLog(
                            LogOper.getInstance().insertStartServerErrorLog());
                    PubToolkit.showYes(ServerUI.getInstance(), "消息服务启动失败!");
                }
            } else if (command.equals(OnLineConfig.STOPSERVERBTN)) {
                Iterator it = PubValue.getOnLineUserThread();
                MessagePack msgPack = new MessagePack();
                msgPack.setType(PackOper.SERVERCLOSE);
                while (it.hasNext()) {
                    ClientThread clientThread = (ClientThread) it.next();
                    clientThread.sendMessage(msgPack);
                }
                PubValue.removeAll();
                PubValue.setAllUserDown();
                ServerUI.getInstance().getOnLineUI().updateOnLine();
                this.onLineUI.setServerBtn(OnLineConfig.STARTSERVERBTN);
                if (PubValue.getMessageServer() != null) {
                    PubValue.getMessageServer().stopServer();
                    PubValue.setMessageServer(null);
                }

                System.out.println("消息服务关闭成功");
            } else if (command.equals(OnLineConfig.DOWNONLINEBTN)) {
                // 强制下线
                String[] downIds = onLineUI.getSelectId();
                if (!(downIds != null && downIds.length > 0)) {
                    PubToolkit.showInformation("请选择要强制下线的用户");
                    return;
                }
                if (!PubToolkit
                        .showYesNo(ServerUI.getInstance(), "真的要对此用户进行强制下线吗?")) {
                    return;
                }
                for (int i = 0; i < downIds.length; i++) {

                    // 取得要下线的用户线程
                    ClientThread downClientThread = PubValue
                            .getUserThread(downIds[i]);
                    // 先通知此人,停止线程
                    if (downClientThread != null) {
                        User downUser = downClientThread.getUser();

                        downUser.setType(PackOper.FORCEDOWN_LINE);
                        downUser.setIsOnline(Parameter.NOTONLINED);
                        downClientThread.sendMessage(downUser);
                        downClientThread.setStop();
                        ServerUI.getInstance().getLogUI().addLog(
                                LogOper.getInstance().insertForceDownLineLog(
                                        downUser));
                        // 从线程组中删除
                        PubValue.deleteUserThread(downUser.getId());

                        // 通知所有人
                        Iterator it = PubValue.getOnLineUserThread();
                        ClientThread clientThread;
                        downUser.setType(PackOper.DOWN_LINE);
                        while (it.hasNext()) {
                            clientThread = (ClientThread) it.next();
                            clientThread.sendMessage(downUser);
                        }
                        // 更新数据库
                        UserDaoFactory.getUserDao().setOnline(downUser.getId(),
                                Parameter.NOTONLINED);
                    }
                    // 更新在线用户表格
                    ServerUI.getInstance().getOnLineUI().updateOnLine();
                    System.out.println("强制下线成功");
                }
            } else if (command.equals(OnLineConfig.SENDMESSAGEBTN)) {
                // 发送系统信息
                Object object = onLineUI.getSendObject();
                MessagePack msgPack = new MessagePack();
                msgPack.setType(PackOper.MESSAGE);
                msgPack.setFrom("");
                msgPack.setTo("");
                msgPack.setMessage(onLineUI.getMessage());
                ClientThread clientThread = null;
                Iterator it = PubValue.getOnLineUserThread();
                if (object instanceof Company) {
                    while (it.hasNext()) {
                        clientThread = (ClientThread) it.next();
                        clientThread.sendMessage(msgPack);
                    }
                } else if (object instanceof Department) {
                    Department department = (Department) object;
                    while (it.hasNext()) {
                        clientThread = (ClientThread) it.next();
                        if (clientThread.getUser().getDeptId().equals(
                                department.getId())) {
                            clientThread.sendMessage(msgPack);
                        }
                    }
                }
                onLineUI.clear();
            }
        }
    }

}