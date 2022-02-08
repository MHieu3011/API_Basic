package com.vccorp.service;

import org.springframework.http.ResponseEntity;

import com.vccorp.dto.UserDTO;

public interface UserService {

	ResponseEntity<String> findAll();

	ResponseEntity<String> save(UserDTO userDTO);

	ResponseEntity<String> delete(String email);

	ResponseEntity<String> update(UserDTO userDTO);

	ResponseEntity<String> findByName(String name);

	ResponseEntity<String> findByAddress(String address);

	ResponseEntity<String> findAllBySortName();
}
