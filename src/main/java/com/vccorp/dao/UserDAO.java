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

	List<UserModel> findAllByName(String name);

	List<UserModel> findByAddress(String address);

	List<UserModel> findAllBySortName();

	List<UserModel> findAllByLikeName(String name);

	List<UserModel> findAllByMatchName(String name);

	List<UserModel> findAllByListID(String s, long[] ids);
}
