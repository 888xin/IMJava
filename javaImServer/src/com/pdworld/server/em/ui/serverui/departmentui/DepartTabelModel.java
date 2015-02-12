package com.pdworld.server.em.ui.serverui.departmentui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.pdworld.pub.unit.Department;
import com.pdworld.server.em.dao.factory.DepartmentDaoFactory;



/**
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class DepartTabelModel extends AbstractTableModel {

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;

    private List columnNameList;

    private List dataList;

    public DepartTabelModel() {
        this.columnNameList = DepartmentDaoFactory.getDepartmentDao().getColumnNames();
        this.dataList = DepartmentDaoFactory.getDepartmentDao().getDeptModel();
    }

    public DepartTabelModel(List columnNameList, List dataList) {
        this.columnNameList = columnNameList;
        this.dataList = dataList;
    }

    public int getColumnCount() {
        if(columnNameList == null)
            return -1;
        return columnNameList.size();
    }

    public int getRowCount() {
        if(dataList == null){
            return -1;
        }
        return dataList.size();
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Class getColumnClass(int columnIndex) {
        Object o = getValueAt(0, columnIndex);
        if (o == null) {
            return String.class;
        } else {
            return o.getClass();
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return ((List) dataList.get(rowIndex)).get(columnIndex);
    }

    public String getColumnName(int columnIndex) {
        return (String) columnNameList.get(columnIndex);
    }

    public void setData(List dataList) {
        this.dataList = dataList;
        this.fireTableDataChanged();
    }

    public Department getRowObject(int row) {
        Department temp = new Department();
        temp.setId((String) getValueAt(row, 0));
        temp.setName((String) getValueAt(row, 1));
        temp.setRemark((String) getValueAt(row, 2));
        return temp;
    }

    public void update() {
        this.dataList = DepartmentDaoFactory.getDepartmentDao().getDeptModel();
        super.fireTableDataChanged();
    }
}