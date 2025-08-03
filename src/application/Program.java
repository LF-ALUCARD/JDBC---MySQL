package application;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDao obj_1 = DaoFactory.createSellerDaoJDBC();
		
		Seller seller = new Seller(17, "bot1", "bot1@gmail.com", new Date(), 5000.0, new Department(2, null));
		
		System.out.println("=== TESTE 2 SELLER ===");
		seller.setBaseSalary(10000.00);
		obj_1.update(seller);
		System.out.println("Dados atualizados com sucesso");
	}

}
