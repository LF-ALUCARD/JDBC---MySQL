package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DB.DBException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("" + "INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES " + "(?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, obj.getName());
			ps.setString(2, obj.getEmail());
			ps.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			ps.setDouble(4, obj.getBaseSalary());
			ps.setInt(5, obj.getDepartment().getId());

			int dados = ps.executeUpdate();

			if (dados > 0) {
				rs = ps.getGeneratedKeys();
				obj.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		}

	}

	@Override
	public void update(Seller obj) {
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement("" + "UPDATE seller SET Name = ?, Email =?, "
					+ "BirthDate = ?, BaseSalary = ?, DepartmentId = ? " + "WHERE seller.Id = ?");

			ps.setString(1, obj.getName());
			ps.setString(2, obj.getEmail());
			ps.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			ps.setDouble(4, obj.getBaseSalary());
			ps.setInt(5, obj.getDepartment().getId());

			ps.setInt(6, obj.getId());

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		}

	}

	@Override
	public void delete(Integer Id) {

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement("" + "DELETE FROM seller WHERE seller.Id = ?;");
			ps.setInt(1, Id);

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		}

	}

	@Override
	public Seller findByld(Integer Id) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("" + "SELECT sl.*, dp.Name AS DpName FROM seller AS sl "
					+ "INNER JOIN department AS dp " + "ON sl.DepartmentId = dp.Id " + "WHERE sl.Id = ?");

			ps.setInt(1, Id);

			rs = ps.executeQuery();

			if (rs.next()) {
				Department dp = NovoDepartamento(rs);
				Seller seller = NovoSeller(rs, dp);
				return seller;
			}
			return null;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		}

	}

	private Seller NovoSeller(ResultSet rs, Department department) throws SQLException {
		Seller obj = new Seller();

		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setDepartment(department);

		return obj;
	}

	private Department NovoDepartamento(ResultSet rs) throws SQLException {

		Department department = new Department(rs.getInt("DepartmentId"), rs.getString("DpName"));

		return department;
	}

	@Override
	public List<Seller> findAll() {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("" + "SELECT sl.*, dp.Name AS DpName FROM seller AS sl\r\n"
					+ "INNER JOIN department AS dp\r\n" + "ON sl.DepartmentId = dp.Id");

			rs = ps.executeQuery();

			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {
				Department department = map.get(rs.getInt("DepartmentId"));

				if (department == null) {
					department = NovoDepartamento(rs);
					map.put(rs.getInt("DepartmentId"), department);
				}

				Seller seller = NovoSeller(rs, department);

				list.add(seller);
			}
			return list;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		}

	}

	@Override
	public List<Seller> findAllByld(Integer dp) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(""
					+ "SELECT sl.*, dp.Name AS DpName FROM seller AS sl\r\n"
					+ "INNER JOIN department AS dp\r\n"
					+ "ON sl.DepartmentId = dp.Id\r\n"
					+ "WHERE DepartmentId = ?");
			
			ps.setInt(1, dp);

			rs = ps.executeQuery();

			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {
				Department department = map.get(rs.getInt("DepartmentId"));

				if (department == null) {
					department = NovoDepartamento(rs);
					map.put(rs.getInt("DepartmentId"), department);
				}

				Seller seller = NovoSeller(rs, department);

				list.add(seller);
			}
			return list;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
	}

}
