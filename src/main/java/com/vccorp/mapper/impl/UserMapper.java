package com.vccorp.mapper.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.vccorp.mapper.RowMapper;
import com.vccorp.model.UserModel;

public class UserMapper implements RowMapper<UserModel> {

	@Override
	public UserModel mapRow(ResultSet resultSet) {
		try {
			UserModel model = new UserModel();
			model.setId(resultSet.getLong("id"));
			model.setName(resultSet.getString("name"));
			model.setAddress(resultSet.getString("address"));
			model.setAge(resultSet.getInt("age"));
			model.setEmail(resultSet.getString("email"));
			return model;
		} catch (SQLException e) {
			return null;
		}
	}

}
