package com.pdworld.server.em.dao.ifc;

import java.util.HashMap;
import java.util.List;

import com.pdworld.pub.unit.Department;


public interface DepartmentIFC {
    /**
     * 删除部门
     *
     * @param dpt
     * @return
     */
    public boolean delete(String dptId);

    /**
     * 更新部门
     *
     * @param dpt
     * @return boolean
     */
    public boolean modify(Department dpt);

    /**
     * 添加部门
     *
     * @param dpt
     * @return boolean
     */
    public boolean add(Department dpt);

    /**
     * 按部门ID查询
     *
     * @param dpt
     * @return List
     */
    public List selectId(String value);

    /**
     * 按部门名称查询
     *
     * @param value
     * @return
     */
    public List selectName(String value);

    /**
     * 按部门参数查询
     *
     * @param hm
     * @return
     */
    public List select(HashMap hm);

    /**
     * 取得最大编号
     *
     * @return
     */
    public String getMaxId();

    /**
     * 取得部门列表的所有信息
     *
     * @return
     */
    public List getDeptModel();

    /**
     * 取得标题名
     *
     * @return
     */
    public List getColumnNames();

}