package model.dao;

import DB.ConfigDB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
	
	public static SellerDao createSellerDaoJDBC() { 
		return new SellerDaoJDBC(ConfigDB.startDataBase());
	}
	
	public static DepartmentDao createDepartmentDaoJDBC() {
		return new DepartmentDaoJDBC(ConfigDB.startDataBase());
	}
}
