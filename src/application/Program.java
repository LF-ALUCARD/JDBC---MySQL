package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDao obj_1 = DaoFactory.createSellerDaoJDBC();
		
		Seller seller = obj_1.findByld(17);
		
		System.out.println(seller);
	}

}
