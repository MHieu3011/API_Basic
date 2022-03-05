package com.vccorp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
		UserModel user = new UserModel();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = HikariConfiguration.getInstance().getConnection();
			connection.setAutoCommit(false);
			String sql = "INSERT INTO user(name, address, age, email, money) VALUES(?, ?, ?, ?, ?)";
			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, userDTO.getName());
			statement.setString(2, userDTO.getAddress());
			statement.setInt(3, userDTO.getAge());
			statement.setString(4, userDTO.getEmail());
			statement.setLong(5, userDTO.getMoney());
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();
			while (resultSet.next()) {
				user.setId(resultSet.getLong(1));
			}
			connection.commit();
			user.setName(userDTO.getName());
			user.setAddress(userDTO.getAddress());
			user.setAge(userDTO.getAge());
			user.setEmail(userDTO.getEmail());
			user.setMoney(userDTO.getMoney());
		} catch (Exception e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	@Override
	public String delete(String email) {
		String sql = "DELETE FROM user WHERE email = ?";
		update(sql, email);
		return "delete success " + email;
	}

	@SuppressWarnings("resource")
	@Override
	public UserModel update(UserDTO userDTO) {
		UserModel user = new UserModel();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		UserMapper mapper = new UserMapper();
		try {
			connection = HikariConfiguration.getInstance().getConnection();
			connection.setAutoCommit(false);
			String sql1 = "UPDATE user SET name = ?, address = ?, age = ?, money = ? WHERE email = ?";
			statement = connection.prepareStatement(sql1);
			statement.setString(1, userDTO.getName());
			statement.setString(2, userDTO.getAddress());
			statement.setInt(3, userDTO.getAge());
			statement.setLong(4, userDTO.getMoney());
			statement.setString(5, userDTO.getEmail());
			statement.executeUpdate();

			String sql2 = "SELECT id, name, address, age, email, money FROM user WHERE email = ?";
			statement = connection.prepareStatement(sql2);
			statement.setString(1, userDTO.getEmail());
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				user = mapper.mapRow(resultSet);
			}
			connection.commit();
		} catch (Exception e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
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

	@SuppressWarnings("resource")
	@Override
	public UserModel addMoney(Long id, Long money) {
		UserModel user = new UserModel();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		UserMapper mapper = new UserMapper();
		try {
			connection = HikariConfiguration.getInstance().getConnection();
			connection.setAutoCommit(false);
			String sql1 = "UPDATE user SET money = money + ? WHERE id = ?";
			statement = connection.prepareStatement(sql1);
			statement.setLong(1, money);
			statement.setLong(2, id);
			statement.executeUpdate();

			String sql2 = "SELECT id, name, address, age, email, money FROM user WHERE id = ?";
			statement = connection.prepareStatement(sql2);
			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				user = mapper.mapRow(resultSet);
			}
			connection.commit();
		} catch (Exception e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	@Transactional
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
			String sql1 = "UPDATE user SET money = money - ? WHERE id = ? AND money >= ?";
			statement = connection.prepareStatement(sql1);
			statement.setLong(1, money);
			statement.setLong(2, idA);
			statement.setLong(3, money);
			int check = statement.executeUpdate();

			if (check != 0) {
				String sql2 = "UPDATE user SET money = money + ? WHERE id = ?";
				statement = connection.prepareStatement(sql2);
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
				connection.commit();
			}
		} catch (Exception e) {
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
