package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;

public class Program {

	public static void main(String[] args) {
		
		SellerDao obj_1 = DaoFactory.createSellerDaoJDBC();
		
		obj_1.delete(17);
	}

}
