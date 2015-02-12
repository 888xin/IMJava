
package com.pdworld.server.em.ui.serverui.departmentui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.pdworld.pub.pack.PackOper;
import com.pdworld.pub.pub.PubToolkit;
import com.pdworld.pub.unit.Department;
import com.pdworld.server.em.dao.factory.DepartmentDaoFactory;
import com.pdworld.server.em.dao.factory.UserDaoFactory;
import com.pdworld.server.em.pub.PubValue;
import com.pdworld.server.em.service.ClientThread;
import com.pdworld.server.em.ui.serverui.ServerUI;


/**
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class DepartmentUI extends JPanel {

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;

    /**
     * 按钮操作面板
     */
    private ButtonPanel buttonPanel = new ButtonPanel();

    /**
     * 信息显示表格
     */
    private JTable deparTable = new JTable();

    /**
     * 表格模式
     */
    private DepartTabelModel tableModel;

    /**
     * 主窗口
     */
    private JFrame mainFrame;

    /**
     * 构造函数
     * @param mainFrame
     */
    public DepartmentUI(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        init();
    }

    /**
     * 初始化
     *
     */
    public void init() {
        this.setLayout(new BorderLayout());

        this.tableModel = new DepartTabelModel(DepartmentDaoFactory.getDepartmentDao()
                .getColumnNames(), DepartmentDaoFactory.getDepartmentDao().getDeptModel());
        // this.tableModel = new DepartTabelModel();
        this.deparTable.setModel(tableModel);

        this.add(new JScrollPane(this.deparTable), BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        this.buttonPanel
                .addButtonActionListener(new ButtonActionListener(this));
    }

    /**
     * 取表格的模型
     * @return DepartTabelModel
     */
    public DepartTabelModel getTableModel() {
        return this.tableModel;
    }

    /**
     * 取得信息显示表格
     * @return
     */
    public JTable getTable() {
        return deparTable;
    }

    /**
     * 取得主窗口
     * @return
     */
    public JFrame getMainFrame() {
        return mainFrame;
    }
}
/**
 * 部门管理面板按钮监听类
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
class ButtonActionListener implements ActionListener {

    private DepartmentUI departmentUI;

    private JTable table;

    public ButtonActionListener(DepartmentUI departmentUI) {
        this.departmentUI = departmentUI;
        this.table = departmentUI.getTable();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            String actionCommand = ((JButton) e.getSource()).getActionCommand();
            if (actionCommand.equals(DepartmentConfig.ADDBTN)) {
                // 先取得最大编号
                Department department = new Department();
                department.setId(DepartmentDaoFactory.getDepartmentDao().getMaxId());
                DepartmentDialog temp = new DepartmentDialog(
                        DepartmentConfig.ADDBTN, department, departmentUI);
                temp.setVisible(true);
            } else if (actionCommand.equals(DepartmentConfig.DELBTN)) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    Department department = this.departmentUI.getTableModel()
                            .getRowObject(row);
                    if (UserDaoFactory.getUserDao().selectDeptId(department.getId())
                            .size() > 0) {
                        PubToolkit.showInformation("对不起,不能删除此部门.");
                        return;
                    }
                    if (PubToolkit.showYesNo(departmentUI.getMainFrame(),
                            "是否真的删除?")) {
                        if (DepartmentDaoFactory.getDepartmentDao().delete(department.getId())) {
                            this.departmentUI.getTableModel().update();
                            PubToolkit.showInformation("删除成功.");
                            //通知所有在线人,更新一个部门
                            Iterator it = PubValue.getOnLineUserThread();
                            department.setType(PackOper.DELETE_DEPARTMENT);
                            while(it.hasNext()){
                                ((ClientThread)it.next()).sendMessage(department);
                            }

                            ServerUI.getInstance().getOnLineUI().initSendCom();
                        } else {
                            PubToolkit.showError("对不起,删除失败.");
                        }
                    }
                } else {
                    PubToolkit.showInformation("请选择要删除的记录.");
                }

            } else if (actionCommand.equals(DepartmentConfig.UPDATEBTN)) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    Department department = this.departmentUI.getTableModel()
                            .getRowObject(row);
                    (new DepartmentDialog(DepartmentConfig.UPDATEBTN,
                            department, departmentUI)).setVisible(true);
                } else {
                    PubToolkit.showInformation("请选择要修改的记录.");
                }
            }
        }
    }
}