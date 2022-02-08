package com.vccorp.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.vccorp.dao.UserDAO;
import com.vccorp.dto.UserDTO;
import com.vccorp.mapper.impl.UserMapper;

@Repository
public class UserDAOImpl extends AbstractDAO<UserDTO> implements UserDAO {

	@Override
	public UserDTO findOneById(Long id) {
		String sql = "SELECT * FROM user WHERE id = ?";
		List<UserDTO> users = query(sql, new UserMapper(), id);
		return users.isEmpty() ? null : users.get(0);
	}

	@Override
	public UserDTO findOneByEmail(String email) {
		String sql = "SELECT * FROM user WHERE email = ?";
		List<UserDTO> users = query(sql, new UserMapper(), email);
		return users.isEmpty() ? null : users.get(0);
	}

	@Override
	public List<UserDTO> findAll() {
		String sql = "SELECT * FROM user";
		return query(sql, new UserMapper());
	}

	@Override
	public UserDTO save(UserDTO userDTO) {
		StringBuilder sql = new StringBuilder("INSERT INTO user(name, address, age, email) ");
		sql.append("VALUES(?, ?, ?, ?)");
		Long id = insert(sql.toString(), userDTO.getName(), userDTO.getAddress(), userDTO.getAge(), userDTO.getEmail());
		return findOneById(id);
	}

	@Override
	public String delete(String email) {
		String sql = "DELETE FROM user WHERE email = ?";
		update(sql, email);
		return "delete success " + email;
	}

	@Override
	public UserDTO update(UserDTO userDTO) {
		StringBuilder sql = new StringBuilder("UPDATE user SET name = ?, address = ?, ");
		sql.append("age = ? WHERE email = ?");
		update(sql.toString(), userDTO.getName(), userDTO.getAddress(), userDTO.getAge(), userDTO.getEmail());
		return findOneByEmail(userDTO.getEmail());
	}

	@Override
	public List<UserDTO> findByName(String name) {
		String sql = "SELECT * FROM user WHERE name = ?";
		return query(sql, new UserMapper(), name);
	}

	@Override
	public List<UserDTO> findByAddress(String address) {
		String sql = "SELECT * FROM user WHERE address = ?";
		return query(sql, new UserMapper(), address);
	}

	@Override
	public List<UserDTO> findAllBySortName() {
		String sql = "SELECT * FROM user ORDER BY name ASC";
		return query(sql, new UserMapper());
	}

}
