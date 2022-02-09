package com.vccorp.service;

import java.util.List;

import javax.naming.NameNotFoundException;

import com.vccorp.dto.UserDTO;

public interface UserService {

	List<UserDTO> findAll();

	UserDTO save(UserDTO userDTO);

	String delete(String email);

	UserDTO update(UserDTO userDTO) throws NameNotFoundException;

	List<UserDTO> findByName(String name);

	List<UserDTO> findByAddress(String address);

	List<UserDTO> findAllBySortName();
}
