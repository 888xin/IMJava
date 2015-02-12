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

import com.pdworld.pub.unit.User;
import com.pdworld.server.em.dao.ifc.UserIFC;
import com.pdworld.server.em.pub.DataBaseComCon;


public class DataBaseUserDao implements UserIFC {

    private static DataBaseUserDao oracleUserDao;

    private DataBaseUserDao() {
    }

    public static DataBaseUserDao getInstance() {
        if (oracleUserDao == null)
            oracleUserDao = new DataBaseUserDao();
        return oracleUserDao;
    }

    public boolean setOnline(String id, int isOnline) {
        Connection conn = DataBaseComCon.getConnection();
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("UPDATE T_USER SET U_ISONLINE = ? WHERE U_ID = ?");
            pst.setInt(1, isOnline);
            pst.setString(2, id);
            if (pst.executeUpdate() > 0) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e1) {
                }
            }
        }
        return false;
    }

    public boolean delete(String id) {
        Connection conn = DataBaseComCon.getConnection();
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("DELETE FROM T_USER WHERE U_ID = ?");
            pst.setString(1, id);
            if (pst.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e1) {
                }
            }
        }
        return false;
    }

    public boolean update(User user) {
        Connection conn = DataBaseComCon.getConnection();
        PreparedStatement pst = null;
        String sql = "UPDATE T_USER SET U_NAME=?,U_SEX=?,"
                + "U_ICONID=?,U_DEPTID=?,U_AGE=?,U_TEL=?,U_ADDRESS=?,"
                + "U_REGTIME=? WHERE U_ID=?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getName().trim());
            pst.setString(2, user.getSex().trim());
            pst.setInt(3, user.getIconId());
            pst.setString(4, user.getDeptId().trim());
            pst.setInt(5, user.getAge());
            pst.setString(6, user.getTel().trim());
            pst.setString(7, user.getAddress().trim());
            pst.setString(8, user.getRegtime().trim());
            pst.setString(9, user.getId().trim());

            if (pst.executeUpdate() > 0) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e1) {
                }
            }
        }
        return false;
    }

    public boolean add(User user) {
        Connection conn = DataBaseComCon.getConnection();
        PreparedStatement pst = null;
        String sql = "INSERT INTO T_USER(U_ID,U_NAME,U_PASSWORD,U_SEX,U_ICONID,U_DEPTID,U_AGE,U_TEL,U_ADDRESS,U_REGTIME,U_ISONLINE)"
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getId());
            pst.setString(2, user.getName());
            pst.setString(3, "123456");
            pst.setString(4, user.getSex());
            pst.setInt(5, user.getIconId());
            pst.setString(6, user.getDeptId());
            pst.setInt(7, user.getAge());
            pst.setString(8, user.getTel());
            pst.setString(9, user.getAddress());
            pst.setString(10, user.getRegtime());
            pst.setInt(11, 0);
            if (pst.executeUpdate() > 0) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e1) {
                }
            }
        }
        return false;
    }

    public List selectId(String id) {
        HashMap hm = new HashMap();
        hm.put("U_ID", id);
        return select(hm);
    }

    public List selectDeptId(String id) {
        HashMap hm = new HashMap();
        hm.put("D_ID", id);
        return select(hm);
    }

    public List select(HashMap hm) {
        StringBuffer sql = new StringBuffer(
                "SELECT U_ID,U_NAME,U_SEX,U_ICONID,U_DEPTID,U_AGE,U_TEL,U_ADDRESS,U_REGTIME, U_ISONLINE,U_PASSWORD FROM T_USER,T_DEPARTMENT WHERE T_USER.U_DEPTID = T_DEPARTMENT.D_ID ");
        Connection conn = DataBaseComCon.getConnection();
        PreparedStatement pst = null;
        List list = new ArrayList();
        String value = null;
        ResultSet rs = null;
        if (hm != null && hm.size() > 0) {
            value = (String) hm.get("U_ID");// 用户ID
            if (value != null && !value.equals("")) {
                sql.append(" AND U_ID = ? ");
            }
            value = (String) hm.get("U_NAME");// 用户名称
            if (value != null && !value.equals("")) {
                sql.append(" AND U_NAME = ? ");
            }
            value = (String) hm.get("D_NAME");// 部门名称
            if (value != null && !value.equals("")) {
                sql.append(" AND D_NAME = ? ");
            }
            value = (String) hm.get("D_ID");// 部门ID
            if (value != null && !value.equals("")) {
                sql.append(" AND D_ID = ? ");
            }
            value = (String) hm.get("U_ISONLINE");// 在线与否
            if (value != null && !value.equals("")) {
                sql.append(" AND U_ISONLINE = ? ");
            }
            value = null;
            sql.append(" ORDER BY U_ID");
        }
        try {
            int count = 1;
//			System.out.println(sql.toString());
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
            User user = null;
            while (rs.next()) {
                user = new User();
                user.setId(rs.getString("U_ID").trim());
                user.setName(rs.getString("U_NAME").trim());
                user.setSex(rs.getString("U_SEX").trim());
                user.setIconId(rs.getInt("U_ICONID"));
                user.setDeptId(rs.getString("U_DEPTID").trim());
                user.setAge(Integer.parseInt(rs.getString("U_AGE").trim()));
                user.setTel(rs.getString("U_TEL").trim());
                user.setAddress(rs.getString("U_ADDRESS").trim());
                user.setRegtime(rs.getString("U_REGTIME").trim());
                user.setIsOnline(rs.getInt("U_ISONLINE"));
                user.setPassword(rs.getString("U_PASSWORD").trim());
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e1) {
                }
            }
        }
        return list;

    }

    public String getMaxId() {
        String sql = "SELECT MAX(U_ID)+1 AS MAX FROM T_USER";
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

    public List selectOnLine() {
        HashMap hm = new HashMap();
        hm.put("U_ISONLINE", "" + 1);
        return getUserModel(hm);
    }

    public List getUserModel(HashMap hm) {
        StringBuffer sql = new StringBuffer("SELECT U_ID,U_NAME,U_SEX,U_ICONID,D_NAME,U_AGE,U_TEL,U_ADDRESS,U_REGTIME,U_ISONLINE=(case U_ISONLINE WHEN 1 THEN '在线' WHEN 0 THEN '离线' end) FROM T_USER,T_DEPARTMENT WHERE T_USER.U_DEPTID = T_DEPARTMENT.D_ID ");
        Connection conn = DataBaseComCon.getConnection();
        PreparedStatement pst = null;
        List list = new ArrayList();
        String value = null;
        ResultSet rs = null;
        if (hm != null && hm.size() > 0) {
            value = (String) hm.get("U_ID");// 用户ID
            if (value != null && !value.equals("")) {
                sql.append(" AND U_ID = ? ");
            }
            value = (String) hm.get("U_NAME");// 用户名称
            if (value != null && !value.equals("")) {
                sql.append(" AND U_NAME = ? ");
            }
            value = (String) hm.get("D_NAME");// 部门名称
            if (value != null && !value.equals("")) {
                sql.append(" AND D_NAME = ? ");
            }
            value = (String) hm.get("D_ID");// 部门ID
            if (value != null && !value.equals("")) {
                sql.append(" AND D_ID = ? ");
            }
            value = (String) hm.get("U_ISONLINE");// 在线与否

            if (value != null && !value.equals("")) {
                sql.append(" AND U_ISONLINE = ? ");
            }
            value = null;
            sql.append(" ORDER BY U_ID");
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
            List row = null;
            while (rs.next()) {
                row = new ArrayList();
                ResultSetMetaData rsmd = rs.getMetaData();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    row.add(rs.getString(i).trim());
                }
                list.add(row);
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

    public List getColumnNames() {
        List list = new ArrayList();
        list.add("编号");
        list.add("姓名");
        list.add("性别");
        list.add("头像");
        list.add("部门");
        list.add("年龄");
        list.add("电话");
        list.add("地址");
        list.add("注册时间");
        return list;
    }

    public boolean resetPassword(String id, String password) {
        if (id == null || id.equals("")) {
            return false;
        }
        String sql = "UPDATE T_USER SET U_PASSWORD = ? WHERE U_ID = ?";
        Connection conn = DataBaseComCon.getConnection();
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, password);
            pst.setString(2, id);
            if (pst.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}