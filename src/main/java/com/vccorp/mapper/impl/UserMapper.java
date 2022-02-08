package com.vccorp.mapper.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.vccorp.dto.UserDTO;
import com.vccorp.mapper.RowMapper;

public class UserMapper implements RowMapper<UserDTO> {

	@Override
	public UserDTO mapRow(ResultSet resultSet) {
		try {
			UserDTO dto = new UserDTO();
			dto.setId(resultSet.getLong("id"));
			dto.setName(resultSet.getString("name"));
			dto.setAddress(resultSet.getString("address"));
			dto.setAge(resultSet.getInt("age"));
			dto.setEmail(resultSet.getString("email"));
			return dto;
		} catch (SQLException e) {
			return null;
		}
	}

}
