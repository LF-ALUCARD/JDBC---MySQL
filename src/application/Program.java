package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDao obj_1 = DaoFactory.createSellerDaoJDBC();
		
		List<Seller> list = obj_1.findAllByld(2);
		
		list.forEach(System.out::println);
	}

}
