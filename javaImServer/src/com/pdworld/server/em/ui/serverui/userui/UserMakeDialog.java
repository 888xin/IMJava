package com.pdworld.server.em.ui.serverui.userui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.pdworld.pub.pack.PackOper;
import com.pdworld.pub.pub.PubToolkit;
import com.pdworld.pub.unit.Department;
import com.pdworld.pub.unit.User;
import com.pdworld.server.em.dao.factory.DepartmentDaoFactory;
import com.pdworld.server.em.dao.factory.UserDaoFactory;
import com.pdworld.server.em.pub.PubValue;
import com.pdworld.server.em.service.ClientThread;
import com.pdworld.server.em.ui.serverui.ServerUI;
import com.pdworld.server.em.ui.serverui.image.GetImage;

/**
 * @author Administrator
 */
public class UserMakeDialog extends JDialog {

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;

    private JLabel idLabel = new JLabel("用户编号：");

    private JLabel nameLabel = new JLabel("用户名称：");

    private JLabel passwordLabel = new JLabel("密    码：");

    private JLabel sexLabel = new JLabel("性    别：");

    private JLabel deptLabel = new JLabel("部    门：");

    private JLabel ageLable = new JLabel("年    龄：");

    private JLabel telLable = new JLabel("电    话：");

    private JLabel addressLabel = new JLabel("地    址：");

    private JLabel regtimeLabel = new JLabel("注册时间：");

    private JLabel isOnlineLabel = new JLabel("是否在线：");

    private JTextField idText = new JTextField();

    private JTextField nameText = new JTextField();

    private JPasswordField passwordText = new JPasswordField("123456");

    private JTextField ageText = new JTextField();

    private JTextField telText = new JTextField();

    private JTextField addressText = new JTextField();

    private JTextField regtimeText = new JTextField();

    private JTextField isOnlineText = new JTextField();

    private JComboBox iconComb = new JComboBox();

    private JComboBox sexComb = new JComboBox();

    private JComboBox deptComb = new JComboBox();

    private JButton saveBtn = new JButton("保存信息");

    private JButton cancelBtn = new JButton("取消操作");

    private JButton passwordResetBtn = new JButton("密码重设");

    private List depts;

    private User user;

    private String type;

    public UserMakeDialog(JFrame mainFrame, String title, User user, String type) {
        super(mainFrame, title, true);
        this.user = user;
        this.type = type;
        init();
        this.setState(type);
    }

    private void setState(String type){
        if(type.equals(UserUIConfig.ADDBTN)){
            passwordResetBtn.setVisible(false);
        }else if(type.equals(UserUIConfig.MAKEBTN)){
            passwordResetBtn.setVisible(true);
        }
    }

    public void init() {
        this.idText.setEditable(false);
        this.regtimeText.setEditable(false);
        this.passwordText.setEnabled(false);
        this.isOnlineText.setEnabled(false);

        Box mainBox = Box.createVerticalBox();

        Box tempBox1 = Box.createVerticalBox();

        Box tempBox2 = Box.createHorizontalBox();
        tempBox2.add(idLabel);
        tempBox2.add(idText);

        tempBox1.add(tempBox2);

        tempBox2 = Box.createHorizontalBox();
        tempBox2.add(nameLabel);
        tempBox2.add(nameText);
        tempBox1.add(Box.createVerticalStrut(15));
        tempBox1.add(tempBox2);

        tempBox2 = Box.createHorizontalBox();
        tempBox2.add(tempBox1);
        tempBox2.add(Box.createHorizontalStrut(20));
        tempBox2.add(iconComb);

        mainBox.add(Box.createVerticalStrut(15));
        mainBox.add(tempBox2);

        Box temp = Box.createHorizontalBox();
        temp.add(passwordLabel);
        temp.add(passwordText);
        temp.add(isOnlineLabel);
        temp.add(isOnlineText);

        mainBox.add(Box.createVerticalStrut(15));
        mainBox.add(temp);

        tempBox1 = Box.createHorizontalBox();
        tempBox1.add(sexLabel);
        tempBox1.add(sexComb);
        tempBox1.add(ageLable);
        tempBox1.add(ageText);

        mainBox.add(Box.createVerticalStrut(15));
        mainBox.add(tempBox1);

        tempBox1 = Box.createHorizontalBox();
        tempBox1.add(deptLabel);
        tempBox1.add(deptComb);
        tempBox1.add(telLable);
        tempBox1.add(telText);

        mainBox.add(Box.createVerticalStrut(15));
        mainBox.add(tempBox1);

        tempBox1 = Box.createHorizontalBox();
        tempBox1.add(addressLabel);
        tempBox1.add(addressText);

        mainBox.add(Box.createVerticalStrut(15));
        mainBox.add(tempBox1);

        tempBox1 = Box.createHorizontalBox();
        tempBox1.add(regtimeLabel);
        tempBox1.add(regtimeText);

        mainBox.add(Box.createVerticalStrut(15));
        mainBox.add(tempBox1);

        tempBox1 = Box.createHorizontalBox();
        tempBox1.add(saveBtn);
        tempBox1.add(Box.createHorizontalStrut(80));
        tempBox1.add(cancelBtn);
        tempBox1.add(Box.createHorizontalStrut(80));
        tempBox1.add(passwordResetBtn);

        mainBox.add(Box.createVerticalStrut(15));
        mainBox.add(tempBox1);

        Dimension size = new Dimension(80, 25);
        idLabel.setPreferredSize(size);
        nameLabel.setPreferredSize(size);
        sexLabel.setPreferredSize(size);
        deptLabel.setPreferredSize(size);
        addressLabel.setPreferredSize(size);
        regtimeLabel.setPreferredSize(size);
        isOnlineLabel.setPreferredSize(size);
        passwordLabel.setPreferredSize(size);
        ageLable.setPreferredSize(size);
        telLable.setPreferredSize(size);

        size = new Dimension(150, 25);
        idText.setPreferredSize(size);
        nameText.setPreferredSize(size);
        iconComb.setPreferredSize(size);
        passwordText.setPreferredSize(size);
        isOnlineText.setPreferredSize(size);

        sexComb.setPreferredSize(size);
        deptComb.setPreferredSize(size);
        addressText.setPreferredSize(size);
        regtimeText.setPreferredSize(size);

        ageText.setPreferredSize(size);
        telText.setPreferredSize(size);

        this.getContentPane().add(mainBox);
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createBevelBorder(20));
        panel.add(mainBox);
        this.getContentPane().add(panel);
        this.setResizable(false);
        this.setSize(500, 390);

        String sexs[] = { "男", "女" };
        this.setSex(sexs);

        depts = DepartmentDaoFactory.getDepartmentDao().select(null);
        Iterator it = depts.iterator();

        String deptNames[] = new String[depts.size()];
        int count = 0;
        while (it.hasNext()) {
            Department dept = (Department) it.next();
            deptNames[count] = dept.getName();
            count++;
        }
        this.setLocationRelativeTo(this.getOwner());
        this.initIconCom();
        this.iconComb.setRenderer(new IconCombobox());

        this.setDept(deptNames);
        this.setUser(this.user);

        this.addButtonActionListener(new UserMakeButtonActionListener(this));
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                if(PubToolkit.showYesNo(UserMakeDialog.this, "真的要取消吗")){
                    UserMakeDialog.this.dispose();
                }
            }
        });

    }

    public void setSex(String[] sexs) {
        if (sexs != null) {
            for (int i = 0; i < sexs.length; i++) {
                sexComb.addItem(sexs[i]);
            }
        }
    }

    private void initIconCom() {
        for (int i = 0; i < 150; i++) {
            ImageIcon imageIcon = GetImage.getBigHead(i);
            if (imageIcon != null) {
                iconComb.addItem("" + i);
            }
        }
    }

    public void setDept(String[] deptNames) {
        if (depts != null) {
            for (int i = 0; i < deptNames.length; i++) {
                deptComb.addItem(deptNames[i]);
            }
        }
    }

    public void addButtonActionListener(ActionListener a) {
        this.saveBtn.addActionListener(a);
        this.cancelBtn.addActionListener(a);
        this.passwordResetBtn.addActionListener(a);
        this.saveBtn.setActionCommand(UserUIConfig.SAVEBTN);
        this.cancelBtn.setActionCommand(UserUIConfig.CANCELBTN);
        this.passwordResetBtn.setActionCommand(UserUIConfig.PASSWORDRESTBTN);
    }

    public User getUser() {

        User user = new User();
        user.setId(idText.getText());
        user.setName(nameText.getText());
        user.setIconId(Integer.parseInt((String) iconComb.getSelectedItem()));
        user.setPassword("123456");
        user.setSex((String) sexComb.getSelectedItem());
        user.setAddress(addressText.getText());
        try {
            user.setAge(Integer.parseInt(ageText.getText()));
        } catch (Exception e) {

        }
        user.setRegtime(regtimeText.getText());
        user.setTel(telText.getText());
        Department dapartment = (Department) depts.get(deptComb
                .getSelectedIndex());
        user.setDeptId(dapartment.getId());
        return user;
    }

    public void setUser(User user) {
        idText.setText(user.getId());
        nameText.setText(user.getName());
        for (int i = 0; i < iconComb.getItemCount(); i++) {
            if (iconComb.getItemAt(i).toString().equals("" + user.getIconId())) {
                iconComb.setSelectedIndex(i);
                break;
            }
        }
        sexComb.setSelectedItem(user.getSex());
        addressText.setText(user.getAddress());
        ageText.setText(user.getAge() + "");
        regtimeText.setText(user.getRegtime());
        telText.setText(user.getTel());

        for (int i = 0; i < depts.size(); i++) {
            if (((Department) depts.get(i)).getId().equals(user.getDeptId())) {
                deptComb.setSelectedIndex(i);
                break;
            }
        }
        isOnlineText.setText(user.getIsOnline() == 1 ? "在线" : "离线");
    }
}

class UserMakeButtonActionListener implements ActionListener {

    private UserMakeDialog userMakeDialog;

    public UserMakeButtonActionListener(UserMakeDialog userMakeDialog) {
        this.userMakeDialog = userMakeDialog;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            User user = userMakeDialog.getUser();
            String actionString = ((JButton) e.getSource()).getActionCommand();
            if (actionString.equals(UserUIConfig.SAVEBTN)) {
                if (user.getName() == null || user.getName().equals("")) {
                    PubToolkit.showError("对不起,请输入用户名称.");
                    return;
                }
                if (user.getAge() <= 0) {
                    PubToolkit.showError("对不起,请输入年龄大于等于1.");
                    return;
                }

                if (user.getTel() == null) {
                    PubToolkit.showError("对不起,输入的电话格式不正确.");
                }

                if (userMakeDialog.getType().equals(UserUIConfig.MAKEBTN)) {
                    if (UserDaoFactory.getUserDao().update(user)) {
                        PubToolkit.showInformation("恭喜,修改成功!");
                        ((ServerUI) userMakeDialog.getOwner()).getUserUI()
                                .update(
                                        ((ServerUI) userMakeDialog.getOwner())
                                                .getUserUI().getSearch());

                        Iterator it = PubValue.getOnLineUserThread();
                        user.setType(PackOper.UPDATE_USER);
                        while(it.hasNext()){
                            ((ClientThread)it.next()).sendMessage(user);
                        }

                        userMakeDialog.setVisible(false);
                        userMakeDialog.dispose();
                    } else {
                        PubToolkit.showInformation("对不起,修改失败!");
                    }
                } else if (userMakeDialog.getType().equals(UserUIConfig.ADDBTN)) {
                    if (UserDaoFactory.getUserDao().add(user)) {
                        ((ServerUI) userMakeDialog.getOwner()).getUserUI()
                                .update(
                                        ((ServerUI) userMakeDialog.getOwner())
                                                .getUserUI().getSearch());
                        PubToolkit.showInformation("恭喜,添加成功!");

                        Iterator it = PubValue.getOnLineUserThread();
                        user.setType(PackOper.ADD_USER);
                        while(it.hasNext()){
                            ((ClientThread)it.next()).sendMessage(user);
                        }

                        userMakeDialog.setVisible(false);
                        userMakeDialog.dispose();
                    } else {
                        PubToolkit.showInformation("对不起,添加失败!");
                    }
                }
            } else if (actionString.equals(UserUIConfig.CANCELBTN)) {
                if (PubToolkit.showYesNo(userMakeDialog, "真的要取消吗?")) {
                    userMakeDialog.setVisible(false);
                    userMakeDialog.dispose();
                }
            } else if (actionString.equals(UserUIConfig.PASSWORDRESTBTN)) {
                if (PubToolkit.showYesNo(userMakeDialog, "真的要重设密码吗?")) {
                    if (UserDaoFactory.getUserDao().resetPassword(user.getId(),"123456")) {
                        PubToolkit.showInformation("恭喜,重设密码成功!");
                    } else {
                        PubToolkit.showInformation("对不起,重设密码失败!");
                    }
                }
            }
        }
    }

}