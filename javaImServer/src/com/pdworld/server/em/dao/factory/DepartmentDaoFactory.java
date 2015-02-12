package com.pdworld.server.em.dao.factory;

import com.pdworld.server.em.dao.database.DataBaseDepartmentDao;
import com.pdworld.server.em.dao.ifc.DepartmentIFC;


public class DepartmentDaoFactory {

	private static DepartmentIFC departmentDao;

	public static DepartmentIFC getDepartmentDao() {
		if (departmentDao == null) {
			departmentDao = DataBaseDepartmentDao.getInstance();
		}
		return departmentDao;
	}
}
