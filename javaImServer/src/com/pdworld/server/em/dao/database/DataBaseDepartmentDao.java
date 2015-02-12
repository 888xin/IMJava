package com.pdworld.server.em.dao.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.pdworld.pub.unit.Department;
import com.pdworld.server.em.dao.ifc.DepartmentIFC;
import com.pdworld.server.em.pub.DataBaseComCon;


public class DataBaseDepartmentDao implements DepartmentIFC {

    private static DataBaseDepartmentDao oracleDepartmentDao;

    private DataBaseDepartmentDao(){
    }

    public static DataBaseDepartmentDao getInstance(){
        if(oracleDepartmentDao ==  null){
            oracleDepartmentDao = new DataBaseDepartmentDao();
        }
        return oracleDepartmentDao;
    }

    public boolean delete(String dptId) {
        Connection conn = DataBaseComCon.getConnection();
        if (conn != null) {
            PreparedStatement pst = null;
            try {
                pst = conn
                        .prepareStatement("DELETE T_DEPARTMENT WHERE D_ID = ?");
                pst.setString(1, dptId);
                pst.executeUpdate();
                return true;
            } catch (SQLException e1) {
                e1.printStackTrace();
            } finally {
                if (pst != null) {
                    try {
                        pst.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        return false;
    }

    public boolean modify(Department dpt) {
        String sql = "UPDATE T_DEPARTMENT SET D_NAME=?,D_REMARK=? WHERE D_ID=?";
        Connection conn = DataBaseComCon.getConnection();
        if (conn != null) {
            PreparedStatement pst = null;
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, dpt.getName().trim());
                pst.setString(2, dpt.getRemark().trim());
                pst.setString(3, dpt.getId().trim());
                pst.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                if (pst != null) {
                    try {
                        pst.close();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
        return false;
    }

    public boolean add(Department dpt) {
        String sql = "INSERT INTO T_DEPARTMENT(D_ID, D_NAME, D_REMARK) VALUES(?, ?, ?)";
        Connection conn = DataBaseComCon.getConnection();
        if (conn != null) {
            PreparedStatement pst = null;
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, dpt.getId().trim());
                pst.setString(2, dpt.getName().trim());
                pst.setString(3, dpt.getRemark().trim());
                if (pst.executeUpdate() > 0)
                    return true;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (pst != null) {
                    try {
                        pst.close();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
        return false;
    }

    public List selectId(String value) {
        HashMap hm = new HashMap();
        hm.put("D_ID", value);
        return select(hm);
    }

    public List selectName(String value) {
        HashMap hm = new HashMap();
        hm.put("D_NAME", value);
        return select(hm);
    }

    public List select(HashMap hm) {
        StringBuffer sql = new StringBuffer(
                "SELECT D_ID, D_NAME, D_REMARK FROM T_DEPARTMENT WHERE 1=1 ");
        Connection conn = DataBaseComCon.getConnection();
        PreparedStatement pst = null;
        List list = new ArrayList();
        String value = null;
        ResultSet rs = null;
        if (hm != null && hm.size() > 0) {
            value = (String) hm.get("D_ID");//部门ID
            if (value != null && !value.equals("")) {
                sql.append("AND D_ID = ? ");
            }
            value = (String) hm.get("D_NAME");//部门名称
            if (value != null && !value.equals("")) {
                sql.append("AND D_NAME = ? ");
            }
            value = null;
        }
        try {
            int count = 1;
            pst = conn.prepareStatement(sql.toString());
            if (hm != null && hm.size() > 0) {
                Iterator it = (hm.keySet()).iterator();
                while (it.hasNext()) {
                    value = (String) hm.get(it.next());
                    if (value != null && !value.equals("")) {
                        pst.setString(count, value);
                        count++;
                    }
                }
            }
            rs = pst.executeQuery();
            Department dapartment = null;
            while (rs.next()) {
                dapartment = new Department();
                dapartment.setId(rs.getString("D_ID").trim());
                dapartment.setName(rs.getString("D_NAME").trim());
                dapartment.setRemark(rs.getString("D_REMARK").trim());
                list.add(dapartment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }

        return list;

    }

    public String getMaxId() {
        String sql = "SELECT subString(LTRIM(STR(MAX(D_ID)+1001)),2,3) AS MAX FROM T_DEPARTMENT";
        Connection conn = DataBaseComCon.getConnection();
        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                return rs.getString("MAX");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (st != null)
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }

    public List getDeptModel() {
        String sql = "SELECT D_ID, D_NAME, D_REMARK FROM T_DEPARTMENT ORDER BY D_ID";
        Connection conn = DataBaseComCon.getConnection();
        List list = new ArrayList();
        List row = null;
        Statement st = null;
        ResultSetMetaData md = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                row = new ArrayList();
                md = rs.getMetaData();
                for (int i = 1; i <= md.getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                list.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (md != null) {
                md = null;
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                rs = null;
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e1) {
                    //e1.printStackTrace();
                }
            }
        }
        return list;
    }

    public List getColumnNames() {
        List list = new ArrayList();
        list.add("部门编号");
        list.add("部门名称");
        list.add("备注");
        return list;
    }

}