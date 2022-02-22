package com.vccorp.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

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

	@Override
	public List<UserModel> transMoney(Long idA, Long idB, Long money) {
		String sql1 = "UPDATE user SET money = money - ? WHERE id = ? ";
		update(sql1, money, idA, idA);
		String sql2 = "UPDATE user SET money = money + ? WHERE id = ? ";
		update(sql2, money, idB);
		String sql = "SELECT id, name, address, age, email, money FROM user WHERE id IN (?, ?)";
		return query(sql, new UserMapper(), idA, idB);
	}

}
