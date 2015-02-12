package com.pdworld.server.em.ui.serverui.departmentui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.pdworld.pub.pack.PackOper;
import com.pdworld.pub.pub.PubToolkit;
import com.pdworld.pub.unit.Department;
import com.pdworld.server.em.dao.factory.DepartmentDaoFactory;
import com.pdworld.server.em.pub.PubValue;
import com.pdworld.server.em.service.ClientThread;
import com.pdworld.server.em.ui.serverui.ServerUI;

/**
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class DepartmentDialog extends JDialog {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private JPanel topPanel = new JPanel();

    private JPanel centerPanel = new JPanel();

    private JPanel bottomPanel = new JPanel();

    private JLabel idLabel = new JLabel("部门编号：");

    private JLabel nameLabel = new JLabel("部门名称：");

    private JLabel remarkLabel = new JLabel("部门描述：");

    private JTextField idText = new JTextField(8);

    private JTextField nameText = new JTextField(8);

    private JTextArea remarkText = new JTextArea();

    private JButton saveBtn = new JButton("保存");

    private JButton cancelBtn = new JButton("取消");

    private String operType;

    private Department dap;

    private DepartmentUI departmentUI;

    public DepartmentDialog(String operType, Department dap,
                            DepartmentUI departmentUI) {
        super(departmentUI.getMainFrame(), true);
        this.operType = operType;
        this.dap = dap;
        this.departmentUI = departmentUI;
        init();
    }

    public void init() {
        if (operType.equals(DepartmentConfig.ADDBTN)) {
            this.setTitle("添加部门");
            idText.setText(dap.getId());
        } else if (operType.equals(DepartmentConfig.UPDATEBTN)) {
            this.setTitle("更新部门");
            idText.setText(dap.getId());
            nameText.setText(dap.getName());
            remarkText.setText(dap.getRemark());
        }

        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(idLabel);
        topPanel.add(idText);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(nameLabel);
        topPanel.add(nameText);
        topPanel.add(Box.createHorizontalStrut(20));

        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));

        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        centerPanel.add(Box.createHorizontalStrut(20));
        centerPanel.add(remarkLabel);

        remarkText.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        centerPanel.add(remarkText);
        centerPanel.add(Box.createHorizontalStrut(20));

        bottomPanel.add(saveBtn);
        bottomPanel.add(Box.createHorizontalStrut(30));
        bottomPanel.add(cancelBtn);

        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(topPanel, BorderLayout.NORTH);
        this.getContentPane().add(centerPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        idText.setEnabled(false);

        this.setSize(400, 200);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.addButtonActionListener(new MyDialogActionListener(this));
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                if(PubToolkit.showYesNo(DepartmentDialog.this, "真的要取消吗?")){
                    DepartmentDialog.this.dispose();
                }
            }
        });


    }

    public void addButtonActionListener(ActionListener a) {
        saveBtn.addActionListener(a);
        cancelBtn.addActionListener(a);
        saveBtn.setActionCommand(DepartmentConfig.SAVEBTN);
        cancelBtn.setActionCommand(DepartmentConfig.CANCELBTN);
    }

    public Department getDapartment() {
        dap.setId(idText.getText());
        dap.setName(nameText.getText());
        dap.setRemark(remarkText.getText());
//		System.out.println(remarkText.getText());
//		System.out.println(":" + dap.getId() + ":" + dap.getName() + ":"
//				+ dap.getRemark() + ":");

        return dap;
    }

    public DepartmentUI getDepartmentUI() {
        return departmentUI;
    }

    public String getOperType() {
        return this.operType;
    }
}

class MyDialogActionListener implements ActionListener {

    private DepartmentDialog departmentDialog;

    public MyDialogActionListener(DepartmentDialog departmentDialog) {
        this.departmentDialog = departmentDialog;
    }

    public void actionPerformed(ActionEvent e) {
        String command = ((JButton) e.getSource()).getActionCommand();
        Department department = departmentDialog.getDapartment();
        if (departmentDialog.getOperType().equals(DepartmentConfig.ADDBTN)) {
            // 判断是否为空
            if (command.equals(DepartmentConfig.SAVEBTN)) {
                if (department.getName().equals("")) {
                    PubToolkit.showError("对不起,请输入部门名称");
                    return;
                }
                // 判断是否已经重名
                if (DepartmentDaoFactory.getDepartmentDao().selectName(department.getName())
                        .size() > 0) {
                    PubToolkit.showError("对不起,此部门名称已经存在.");
                    return;
                }
                // 添加
                if (DepartmentDaoFactory.getDepartmentDao().add(department)) {
                    PubToolkit.showInformation("恭喜,添加成功!");
                    ((DepartTabelModel) (departmentDialog.getDepartmentUI()
                            .getTable().getModel())).update();
                    //通知所有在线人,添加一个部门
                    Iterator it = PubValue.getOnLineUserThread();
                    department.setType(PackOper.ADD_DEPARTMENT);

                    while(it.hasNext()){
                        System.out.println("发送修改部门消息");
                        ((ClientThread)it.next()).sendMessage(department);
                    }

                    ServerUI.getInstance().getOnLineUI().initSendCom();
                    departmentDialog.setVisible(false);
                    departmentDialog.dispose();

                    return;
                } else {
                    PubToolkit.showError("对不起,添加失败.");
                    return;
                }
            }
        } else if (departmentDialog.getOperType().equals(
                DepartmentConfig.UPDATEBTN)) {
            if (department.getName().equals("")) {
                PubToolkit.showError("对不起,请输入部门名称");
                return;
            }
            if (command.equals(DepartmentConfig.SAVEBTN)) {
                List list = DepartmentDaoFactory.getDepartmentDao().selectName(department
                        .getName());
                if (list.size() > 0) {
                    Department tempDepartment = (Department) list.get(0);
                    if (!tempDepartment.getId().equals(department.getId())) {
                        PubToolkit.showError("对不起,此部门名称已经存在.");
                        return;
                    }
                }
                if (DepartmentDaoFactory.getDepartmentDao().modify(department)) {
                    PubToolkit.showInformation("恭喜,修改成功!");
                    ((DepartTabelModel) (departmentDialog.getDepartmentUI()
                            .getTable().getModel())).update();

                    //通知所有在线人,更新一个部门
                    Iterator it = PubValue.getOnLineUserThread();
                    department.setType(PackOper.UPDATE_DEPARTMENT);
                    while(it.hasNext()){
                        ((ClientThread)it.next()).sendMessage(department);
                    }
                    ServerUI.getInstance().getOnLineUI().initSendCom();

                    departmentDialog.setVisible(false);
                    departmentDialog.dispose();
                    return;
                } else {
                    PubToolkit.showError("对不起,修改失败.");
                    return;
                }
            }
        }
        if (command.equals(DepartmentConfig.CANCELBTN)) {
            if (PubToolkit.showYesNo(departmentDialog, "真的要取消吗?")) {
                departmentDialog.setVisible(false);
                departmentDialog.dispose();
                return;
            }
        }
    }
}