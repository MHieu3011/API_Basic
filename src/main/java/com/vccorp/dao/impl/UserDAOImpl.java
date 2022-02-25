package com.vccorp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.vccorp.configuration.HikariConfiguration;
import com.vccorp.dao.UserDAO;
import com.vccorp.dto.UserDTO;
import com.vccorp.mapper.impl.UserMapper;
import com.vccorp.model.UserModel;

@Repository
public class UserDAOImpl extends AbstractDAO<UserModel> implements UserDAO {

	@Override
	public UserModel findOneById(Long id) {
		String sql = "SELECT id, name, address, age, email, money FROM user WHERE id = ?";
		List<UserModel> users = query(sql, new UserMapper(), id);
		return users.isEmpty() ? null : users.get(0);
	}

	@Override
	public UserModel findOneByEmail(String email) {
		String sql = "SELECT id, name, address, age, email, money FROM user WHERE email = ?";
		List<UserModel> users = query(sql, new UserMapper(), email);
		return users.isEmpty() ? null : users.get(0);
	}

	@Override
	public List<UserModel> findAll() {
		String sql = "SELECT id, name, address, age, email, money FROM user";
		return query(sql, new UserMapper());
	}

	@Override
	public UserModel save(UserDTO userDTO) {
		StringBuilder sql = new StringBuilder("INSERT INTO user(name, address, age, email, money) ");
		sql.append("VALUES(?, ?, ?, ?, ?)");
		Long id = insert(sql.toString(), userDTO.getName(), userDTO.getAddress(), userDTO.getAge(), userDTO.getEmail(),
				userDTO.getMoney());
		return findOneById(id);
	}

	@Override
	public String delete(String email) {
		String sql = "DELETE FROM user WHERE email = ?";
		update(sql, email);
		return "delete success " + email;
	}

	@Override
	public UserModel update(UserDTO userDTO) {
		StringBuilder sql = new StringBuilder("UPDATE user SET name = ?, address = ?, ");
		sql.append("age = ?, money = ? WHERE email = ?");
		update(sql.toString(), userDTO.getName(), userDTO.getAddress(), userDTO.getAge(), userDTO.getMoney(),
				userDTO.getEmail());
		return findOneByEmail(userDTO.getEmail());
	}

	@Override
	public List<UserModel> findAllByName(String name) {
		String sql = "SELECT id, name, address, age, email, money FROM user WHERE name = ?";
		return query(sql, new UserMapper(), name);
	}

	@Override
	public List<UserModel> findByAddress(String address) {
		String sql = "SELECT id, name, address, age, email, money FROM user WHERE address = ?";
		return query(sql, new UserMapper(), address);
	}

	@Override
	public List<UserModel> findAllBySortName() {
		String sql = "SELECT id, name, address, age, email, money FROM user ORDER BY name ASC";
		return query(sql, new UserMapper());
	}

	@Override
	public List<UserModel> findAllByLikeName(String name) {
		String sql = "SELECT id, name, address, age, email, money FROM user WHERE name LIKE ?";
		return query(sql, new UserMapper(), name);
	}

	@Override
	public List<UserModel> findAllByMatchName(String name) {
		String sql = "SELECT id, name, address, age, email, money FROM user WHERE MATCH(name) AGAINST (?)";
		return query(sql, new UserMapper(), name);
	}

	@Override
	public List<UserModel> findAllByListID(String s, long[] ids) {
		String sql = "SELECT id, name, address, age, email, money FROM user WHERE id IN " + s;
		return query(sql, new UserMapper(), ids);
	}

	@Override
	public UserModel addMoney(Long id, Long money) {
		String sql = "UPDATE user SET money = money + ? WHERE id = ? ";
		update(sql, money, id);
		return findOneById(id);
	}

	@SuppressWarnings("resource")
	@Override
	public List<UserModel> transMoney(Long idA, Long idB, Long money) {
		List<UserModel> users = new ArrayList<>();
		UserMapper mapper = new UserMapper();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = HikariConfiguration.getInstance().getConnection();
			connection.setAutoCommit(false);
			String sql1 = "select if(money >= ?, 1, 0) from user where id = ?";
			statement = connection.prepareStatement(sql1);
			statement.setLong(1, money);
			statement.setLong(2, idA);
			resultSet = statement.executeQuery();
			int check = 0;
			while (resultSet.next()) {
				check = resultSet.getInt(1);
			}
			if (check != 0) {
				String sql2 = "UPDATE user SET money = money - ? WHERE id = ?";
				statement = connection.prepareStatement(sql2);
				statement.setLong(1, money);
				statement.setLong(2, idA);
				statement.executeUpdate();

				String sql3 = "UPDATE user SET money = money + ? WHERE id = ?";
				statement = connection.prepareStatement(sql3);
				statement.setLong(1, money);
				statement.setLong(2, idB);
				statement.executeUpdate();

				String sql = "SELECT id, name, address, age, email, money FROM user WHERE id IN (?, ?)";
				statement = connection.prepareStatement(sql);
				statement.setLong(1, idA);
				statement.setLong(2, idB);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					users.add(mapper.mapRow(resultSet));
				}
			}
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (connection != null) {
					connection.setAutoCommit(true);
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return users;
	}

}
