package com.vccorp.insert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.vccorp.configuration.HikariConfiguration;
import com.vccorp.dto.UserDTO;

import net.bytebuddy.utility.RandomString;

public class InsertUserWithBatch {

	private static Random randomInt = new Random();
	private static RandomString randomString = new RandomString(13);

	private static void setParameters(PreparedStatement statement, Object... parameters) {
		try {
			for (int i = 0; i < parameters.length; i++) {
				Object parameter = parameters[i];
				int index = i + 1;
				if (parameter instanceof Long) {
					statement.setLong(index, (long) parameter);
				} else if (parameter instanceof String) {
					statement.setString(index, (String) parameter);
				} else if (parameter instanceof Integer) {
					statement.setInt(index, (Integer) parameter);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static List<UserDTO> init() {
		List<UserDTO> users = new ArrayList<>();
		for (long i = 0; i < 300; i++) {
			UserDTO user = new UserDTO();
			String str = randomString.nextString();
			Integer age = randomInt.nextInt(99) + 1;
			user.setName(str);
			user.setAddress("Dia chi: " + str);
			user.setAge(age);
			user.setEmail(str + "@email.com");
			user.setMoney((long) 1000);
			users.add(user);
		}
		return users;
	}

	private static void insertWithBatch(int batchSize) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = HikariConfiguration.getInstance().getConnection();
			String sql = "INSERT INTO user(name, address, age, email, money) VALUES(?, ?, ?, ?, ?)";
			statement = connection.prepareStatement(sql);
			connection.setAutoCommit(false);
			List<UserDTO> users = init();
			for (int i = 0; i < users.size(); i++) {
				setParameters(statement, users.get(i).getName(), users.get(i).getAddress(), users.get(i).getAge(),
						users.get(i).getEmail(), users.get(i).getMoney());
				statement.addBatch();
				if (i % batchSize == 0) {
					statement.executeBatch();
					connection.commit();
				}
			}
			statement.executeBatch();
			connection.commit();
		} catch (SQLException e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		insertWithBatch(20);
	}
}
