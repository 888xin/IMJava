package com.pdworld.server.em.dao.ifc;

import java.util.HashMap;
import java.util.List;

import com.pdworld.pub.unit.User;


public interface UserIFC {
    /**
     * 更新某个用户上下线
     *
     * @param user
     * @param isOnline
     * @return
     */
    public boolean setOnline(String id, int isOnline);

    /**
     * 删除用户
     *
     * @param dpt
     * @return
     */
    public boolean delete(String id);

    /**
     * 更新用户
     *
     * @param dpt
     * @return
     */
    public boolean update(User user);

    /**
     * 添加用户
     *
     * @param dpt
     * @return boolean
     */
    public boolean add(User user);

    /**
     * 查询用户
     *
     * @param dpt
     * @return
     */
    public List selectId(String id);

    /**
     * 查询某部门ID下的员工
     *
     * @param id
     * @return
     */
    public List selectDeptId(String id);

    /**
     * 查询某此条件的用户
     *
     * @param id
     * @return
     */
    public List select(HashMap hm);

    /**
     * 取得最大编号
     *
     * @return
     */
    public String getMaxId();

    public List selectOnLine();

    /**
     * 取得用户列表的所有信息
     *
     * @return
     */
    //这里要进行查询参数配置:
    //员工编号,如果为空值或者是空字符串,就不当成查询条件,以下同
    //员工姓名
    //员工部门
    public List getUserModel(HashMap hm);

    /**
     * 返回列表名
     *
     * @return
     */
    public List getColumnNames();

    /**
     * 重设密码
     *
     * @param id
     * @param password
     * @return
     */
    public boolean resetPassword(String id, String password);
}