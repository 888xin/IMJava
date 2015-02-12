package com.pdworld.server.em.dao.factory;

import com.pdworld.server.em.dao.database.DataBaseUserDao;
import com.pdworld.server.em.dao.ifc.UserIFC;


public class UserDaoFactory {

	private static UserIFC userDao;

	public static UserIFC getUserDao() {
		if (userDao == null) {
			userDao = DataBaseUserDao.getInstance();
		}
		return userDao;
	}
}
