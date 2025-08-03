package model.dao;

import java.util.List;

import model.entities.Seller;

public interface SellerDao {
	
	void insert(Seller obj);
	void update(Seller obj);
	void delete(Integer Id);
	Seller findByld(Integer Id);
	List<Seller> findAll();
	
	List<Seller> findAllByld(Integer dp);
}
