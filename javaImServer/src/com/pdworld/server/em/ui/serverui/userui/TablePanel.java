package com.pdworld.server.em.ui.serverui.userui;

import java.util.HashMap;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class TablePanel extends JScrollPane {

    private static final long serialVersionUID = 1L;

    private JTable userTable = null;

    private UserTableModel userTableModel = null;

    public TablePanel(UserTableModel userTableModel) {
        try {
            this.userTableModel = userTableModel;
            init();
        } catch (IllegalAccessException e) {
        }
    }

    public void init() throws IllegalAccessException {

        userTable = new JTable(this.userTableModel);
        TableColumn tc = this.userTable.getColumn("头像");
        this.userTable.setDragEnabled(false);
        if (tc != null)
            tc.setCellRenderer(new UserTableIconCellReader());
        this.setViewportView(userTable);
        userTable.setRowSelectionAllowed(true);
        userTable.setRowHeight(25);
    }

    public void update(HashMap hm) {

        userTableModel.update(hm);
        //userTable.repaint();
    }

    public String getRowId() {
        int row = userTable.getSelectedRow();
        if (row >= 0) {
            return userTableModel.getRowId(row);
        }
        return null;
    }

    /**
     * 返回被选中的所有编号
     * @return
     */
    public String[] getSelectId(){
        int[] rows = userTable.getSelectedRows();
        String selectIds[] = null;
        if(rows.length >0){
            selectIds = new String[rows.length];
            for(int i=0;i<rows.length;i++){
                selectIds[i] = this.userTableModel.getRowId(rows[i]);
            }
        }
        return selectIds;
    }

}