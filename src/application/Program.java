package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program {

	public static void main(String[] args) {
		
		DepartmentDao obj = DaoFactory.createDepartmentDaoJDBC();
		
		List<Department> list = obj.findAll();
		
		list.forEach(System.out::println);
	}

}
