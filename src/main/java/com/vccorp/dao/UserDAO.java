package com.vccorp.dao;

import java.util.List;

import com.vccorp.dto.UserDTO;
import com.vccorp.model.UserModel;

public interface UserDAO {

	UserModel findOneById(Long id);

	UserModel findOneByEmail(String email);

	List<UserModel> findAll();

	UserModel save(UserDTO userDTO);

	String delete(String email);

	UserModel update(UserDTO userDTO);

	List<UserModel> findByName(String name);

	List<UserModel> findByAddress(String address);

	List<UserModel> findAllBySortName();
}
