package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DB.ConfigDB;
import DB.DBException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

	private Connection conn;

	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("" + "INSERT INTO department (Name) " + "VALUES " + "(?)",
					Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, obj.getName());

			int dados = ps.executeUpdate();

			if (dados > 0) {
				rs = ps.getGeneratedKeys();

				if (rs.next()) {
					int Id = rs.getInt(1);
					obj.setId(Id);
				}
			}
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			ConfigDB.closePreparedStatement(ps);
			ConfigDB.closeResultSet(rs);
		}
	}

	@Override
	public void update(Department obj) {
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement("UPDATE department set Name = ? " + "WHERE Id = ? ");

			ps.setString(1, obj.getName());
			ps.setInt(2, obj.getId());

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			ConfigDB.closePreparedStatement(ps);
		}

	}

	@Override
	public void delete(Integer Id) {

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement("DELETE FROM department WHERE Id = ?");

			ps.setInt(1, Id);

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		}

	}

	@Override
	public Department findByld(Integer Id) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT * FROM Department WHERE Id = ?");

			ps.setInt(1, Id);

			rs = ps.executeQuery();

			if (rs.next()) {
				Department dp = NovoDepartamento(rs);
				return dp;
			}
			return null;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		}

	}

	private Department NovoDepartamento(ResultSet rs) throws SQLException {

		Department dp = new Department();
		dp.setId(rs.getInt("Id"));
		dp.setName(rs.getString("Name"));

		return dp;
	}

	@Override
	public List<Department> findAll() {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT * FROM Department");

			rs = ps.executeQuery();

			List<Department> list = new ArrayList<>();

			while (rs.next()) {
				Department dp = NovoDepartamento(rs);
				list.add(dp);
			}
			return list;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		}

	}

}
