package com.pdworld.server.em.ui.serverui.userui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.pdworld.pub.pack.PackOper;
import com.pdworld.pub.pub.PubToolkit;
import com.pdworld.pub.unit.Department;
import com.pdworld.pub.unit.User;
import com.pdworld.server.em.dao.factory.DepartmentDaoFactory;
import com.pdworld.server.em.dao.factory.UserDaoFactory;
import com.pdworld.server.em.pub.PubValue;
import com.pdworld.server.em.service.ClientThread;


/**
 * 用户管理面板
 * @author Administrator
 */
public class UserUI extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * 操作按钮面板
     */
    private ButtonPanel buttonPanel = new ButtonPanel();

    /**
     * 探索面板
     */
    private SearchPanel searchPanel = new SearchPanel();

    /**
     * 表格面板
     */
    private TablePanel tablePanel;

    /**
     * 主窗口
     */
    private JFrame mainFrame;

    /**
     * 构造函数
     * @param mainFrame
     */
    public UserUI(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        init();
    }

    /**
     * 初始化
     *
     */
    public void init() {
        searchPanel = new SearchPanel();
        List list = DepartmentDaoFactory.getDepartmentDao().select(null);
        String dptComs[] = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            dptComs[i] = ((Department) list.get(i)).getName();
            //	System.out.println(dptComs[i]);
        }
        searchPanel.setDptCom(dptComs);

        buttonPanel = new ButtonPanel();

        tablePanel = new TablePanel(new UserTableModel(UserDaoFactory.getUserDao()
                .getColumnNames(), UserDaoFactory.getUserDao().getUserModel(searchPanel
                .getSearch())));

        this.setLayout(new BorderLayout());
        this.add(searchPanel, BorderLayout.NORTH);
        this.add(tablePanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.addButtonActionListener(new UserUIButtonActionListener(this));

    }

    /**
     * 添加按钮监听
     * @param actionListener
     */
    public void addButtonActionListener(ActionListener actionListener) {
        this.buttonPanel.addButtonActionListener(actionListener);
        this.searchPanel.addButtonActionListener(actionListener);
    }

    /**
     * 取得主窗口
     * @return
     */
    public JFrame getMainFrame() {
        return mainFrame;
    }

    /**
     * 取得选择的用户ID
     * @return
     */
    public String getRowId() {
        return this.tablePanel.getRowId();
    }

    /**
     * 取得搜索条件
     * @return
     */
    public HashMap getSearch() {
        return this.searchPanel.getSearch();
    }

    /**
     * 更新用户表格
     * @param hm
     */
    public void update(HashMap hm) {
        this.tablePanel.update(hm);
    }
}
/**
 * 用户管理面板按钮监听类
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
class UserUIButtonActionListener implements ActionListener {

    private UserUI userUI;

    public UserUIButtonActionListener(UserUI userUI) {
        this.userUI = userUI;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            String command = ((JButton) e.getSource()).getActionCommand();
            //	User user = userUI.getU
            if (command.equals(UserUIConfig.DELBTN)) {
                //弹出选择,如果是进行删除,正常删除提示删除成功,删除不成功就提示删除不成功
                if (userUI.getRowId() == null) {
                    PubToolkit.showInformation("对不起,请选择要删除的用户.");
                    return;
                }
                if (PubToolkit.showYesNo(userUI.getMainFrame(), "真的要删除吗?")) {
                    User tempUser = (User)UserDaoFactory.getUserDao().selectId(userUI.getRowId()).get(0);
                    if(tempUser.getIsOnline() == 1){
                        PubToolkit.showYes(userUI.getMainFrame(), "对不起,此用户处于在线状态,请先强制下线,再删除.");
                        return;
                    }
                    if (UserDaoFactory.getUserDao().delete(userUI.getRowId())) {
                        userUI.update(userUI.getSearch());
                        PubToolkit.showInformation("恭喜,删除成功.");

                        //显示修改对话框
                        Iterator it = PubValue.getOnLineUserThread();
                        tempUser.setType(PackOper.DELETE_USER);
                        while(it.hasNext()){
                            ((ClientThread)it.next()).sendMessage(tempUser);
                        }

                    }
                }
            } else if (command.equals(UserUIConfig.ADDBTN)) {
                //生成最大编号,显示添加对话框
                User user = new User();
                user.setId(UserDaoFactory.getUserDao().getMaxId());
                user.setRegtime(PubToolkit.getDate());
                (new UserMakeDialog(userUI.getMainFrame(), "添加用户", user,	UserUIConfig.ADDBTN)).setVisible(true);
            } else if (command.equals(UserUIConfig.MAKEBTN)) {
                if (userUI.getRowId() == null) {
                    PubToolkit.showInformation("对不起,请选择要修改的记录.");
                    return;
                } else {
                    List list = UserDaoFactory.getUserDao().selectId(userUI.getRowId());
                    User user = (User)list.get(0);
                    //显示修改对话框
                    (new UserMakeDialog(userUI.getMainFrame(), "修改用户",user ,UserUIConfig.MAKEBTN)).setVisible(true);
                }
            } else if (command.equals(UserUIConfig.SEARCHBTN)) {
                //查询
                userUI.update(userUI.getSearch());
            }
        }
    }
}