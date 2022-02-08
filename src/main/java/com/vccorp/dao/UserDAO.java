package com.vccorp.dao;

import java.util.List;

import com.vccorp.dto.UserDTO;

public interface UserDAO {

	UserDTO findOneById(Long id);

	UserDTO findOneByEmail(String email);

	List<UserDTO> findAll();

	UserDTO save(UserDTO userDTO);

	String delete(String email);

	UserDTO update(UserDTO userDTO);

	List<UserDTO> findByName(String name);

	List<UserDTO> findByAddress(String address);

	List<UserDTO> findAllBySortName();
}
