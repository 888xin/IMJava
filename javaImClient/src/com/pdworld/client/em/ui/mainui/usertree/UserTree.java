package com.pdworld.client.em.ui.mainui.usertree;

import javax.swing.ImageIcon;
import javax.swing.JTree;

import com.pdworld.client.em.ui.images.GetImage;
import com.pdworld.pub.unit.Company;
import com.pdworld.pub.unit.Department;
import com.pdworld.pub.unit.MyTimerUpTree;
import com.pdworld.pub.unit.User;

/**
 * 用户树类
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class UserTree extends JTree {

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;

    private MyTreeModel myTreeModel;

    private MyTimerUpTree myTimerUpTree;

    public UserTree(MyTreeModel myTreeModel) {
        super(myTreeModel);
        this.myTreeModel = myTreeModel;
        this.setCellRenderer(new MyTreeCellRenderer());
        ImageIcon imageIcon = GetImage.getMinHead(1);
        if (imageIcon != null) {
            this.setRowHeight(imageIcon.getIconHeight() + 2);
        }
        this.setToggleClickCount(0);
    }

    /**
     * 设置公司
     * @param company
     */
    public void setCompany(Company company) {
        myTreeModel.setCompany(company);
        updateTree();
    }

    /**
     * 添加一个用户
     */
    public void addUser(User user) {
        this.myTreeModel.addUser(user);
        updateTree();
    }

    /**
     * 添加一个部门
     * @param department
     */
    public void addDepartment(Department department) {
        this.myTreeModel.addDepartment(department);
        updateTree();
    }

    /**
     * 删除一个用户
     * @param user
     */
    public void deleteUser(User user) {
        System.out.println("执行用户删除");
        this.myTreeModel.deleteUser(user);
        updateTree();
    }

    /**
     * 删除一个部门
     * @param department
     */
    public void deleteDepartment(Department department) {
        this.myTreeModel.deleteDepartment(department);
        updateTree();
    }

    /**
     * 更新一个用户
     * @param user
     */
    public void updateUser(User user) {
        this.myTreeModel.updateUser(user);
        updateTree();
    }

    /**
     * 更新一个部门
     * @param department
     */
    public void updateDepartment(Department department) {
        this.myTreeModel.updateDepartment(department);
        updateTree();
    }

    /**
     * 根据用户ID寻找用户的信息
     * @param id
     * @return User
     */
    public User findUser(String id) {
        return this.myTreeModel.findUser(id);
    }

    /**
     * 根据部门ID寻找部门的信息
     * @param id
     * @return Department
     */
    public Department findDepartment(String id) {
        return this.myTreeModel.findDepartment(id);
    }

    /**
     * 取得公司信息
     * @return Company
     */
    public Company getCompany() {
        return this.myTreeModel.getCompany();
    }

    /**
     * 刷新用户树
     *
     */
    public void updateTree() {
        if (myTimerUpTree == null || !myTimerUpTree.isAlive()) {
            myTimerUpTree = new MyTimerUpTree(50, this);
            myTimerUpTree.setDaemon(true);
            myTimerUpTree.start();
        } else
            myTimerUpTree.setCancel();
    }

    /**
     * 取得某部门在线用户数据
     * @param department
     * @return int
     */
    public int getDepartmentOnlineUserCount(Department department) {
        return this.myTreeModel.getDepartmentOnlineUserCount(department);

    }

    /**
     * 取得某部门所有用户数
     * @param department
     * @return int
     */
    public int getDepartmentUserCount(Department department) {
        return myTreeModel.getDepartmentUserCount(department);
    }

    /**
     * 设置所有人处理下线状态
     *
     */
    public void setAllDownLine() {
        myTreeModel.setAllDownLine();
        updateTree();
    }
}