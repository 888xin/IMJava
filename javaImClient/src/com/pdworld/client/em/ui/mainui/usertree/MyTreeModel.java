package com.pdworld.client.em.ui.mainui.usertree;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.tree.DefaultTreeModel;

import com.pdworld.pub.pub.Parameter;
import com.pdworld.pub.unit.Company;
import com.pdworld.pub.unit.Department;
import com.pdworld.pub.unit.User;


public class MyTreeModel extends DefaultTreeModel {

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;

    private Company company;

    private Set departmentSet = new TreeSet();

    private Set userSet = new TreeSet();

    public MyTreeModel() {
        super(null);
    }

    /**
     * 设置公司
     *
     * @param company
     */
    public void setCompany(Company company) {
        if (company != null)
            this.company = company;
    }

    /**
     * 添加用户
     *
     * @param user
     */
    public void addUser(User user) {
        if (user != null)
            userSet.add(user);
    }

    /**
     * 添加部门
     *
     * @param department
     */
    public void addDepartment(Department department) {
        if (department != null)
            departmentSet.add(department);
    }

    /**
     * 删除列表中的某个用户
     *
     * @param user
     */
    public void deleteUser(User user) {
        userSet.remove(user);
    }

    //主要用于,服务器断开,或者网络断开
    public void setAllDownLine(){
        Iterator it = userSet.iterator();
        while(it.hasNext()){
            Object object  = it.next();
            if(object instanceof User){
                ((User)object).setIsOnline(Parameter.NOTONLINED);
            }
        }
    }

    public void deleteDepartment(Department department) {
        departmentSet.remove(department);
    }

    public void updateUser(User user) {
        this.deleteUser(user);
        this.addUser(user);
    }

    public void updateDepartment(Department department) {
        this.deleteDepartment(department);
        this.addDepartment(department);
    }

    public Object getChild(Object parent, int index) {
        if (parent instanceof Company)
            return this.getDepartment(index);
        else if (parent instanceof Department)
            return getDepartmentUser((Department) parent, index);
        else
            return null;
    }

    /**
     * 取得某序号的部门
     *
     * @param department
     * @param index
     * @return
     */
    private Object getDepartment(int index) {
        Department[] departments = (Department[]) departmentSet.toArray(new Department[0]);
        if (departments.length > 0)
            return departments[index];
        return null;
    }

    /**
     * 取得部门的序号
     *
     * @param child
     * @return
     */
    private int getDepartmentIndexOf(Object child) {
        Department[] departments = (Department[]) departmentSet
                .toArray(new Department[0]);
        for (int i = 0; i < departments.length; i++) {
            if (departments[i].equals(child)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 取得某部门某序号的用户
     *
     * @param department
     * @param index
     * @return
     */
    private Object getDepartmentUser(Department department, int index) {
        User[] users = (User[]) userSet.toArray(new User[0]);
        int count = 0;
        for (int i = 0; i < users.length; i++) {
            if (users[i].getDeptId().equals(department.getId())) {
                if (count == index) {
                    return users[i];
                }
                count++;
            }
        }
        return null;
    }

    /**
     * 取得某部门某用户的序号
     *
     * @param department
     * @param child
     * @return
     */
    private int getDepartmentUserIndexOf(Department department, User child) {
        User[] users = (User[]) userSet.toArray(new User[0]);
        int count = 0;
        for (int i = 0; i < users.length; i++) {
            if (users[i].getDeptId().equals(department.getId())) {
                if (child.getId().equals(users[i].getId())) {
                    return count;
                }
                count++;
            }
        }
        return -1;
    }

    /**
     * 取得某部门下员工的人数
     *
     * @param department
     * @return int
     */
    public int getDepartmentUserCount(Department department) {
        User[] users = (User[]) userSet.toArray(new User[0]);
        int count = 0;
        for (int i = 0; i < users.length; i++) {
            if (users[i].getDeptId().equals(department.getId())) {
                count++;
            }
        }
        return count;
    }

    public int getChildCount(Object parent) {
        if (parent instanceof Company)
            return departmentSet.size();
        else if (parent instanceof Department) {
            return getDepartmentUserCount((Department) parent);
        } else
            return -1;
    }

    public int getIndexOfChild(Object parent, Object child) {
        if (parent instanceof Company)
            return this.getDepartmentIndexOf((Department) parent);
        else if (parent instanceof Department)
            return getDepartmentUserIndexOf((Department) parent, (User) child);
        else
            return -1;
    }

    public Object getRoot() {
        return company;
    }

    public boolean isLeaf(Object node) {
        return node instanceof User;
    }

    public User findUser(String id){
        Iterator it = userSet.iterator();
        while(it.hasNext()){
            User user = (User)it.next();
            if(user.getId().equals(id)){
                return user;
            }
        }
        return null;
    }

    public Department findDepartment(String id){
        Iterator it = this.departmentSet.iterator();
        while(it.hasNext()){
            Department department = (Department)it.next();
            if(department.getId().equals(id)){
                return department;
            }
        }
        return null;
    }

    public Company getCompany(){
        return company;
    }

    public int getDepartmentOnlineUserCount(Department department) {
        User[] users = (User[]) userSet.toArray(new User[0]);
        int count = 0;
        for (int i = 0; i < users.length; i++) {
            if (users[i].getDeptId().equals(department.getId()) && users[i].getIsOnline() ==1 ) {
                count++;
            }
        }
        return count;
    }



}