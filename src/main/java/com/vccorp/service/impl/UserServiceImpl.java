package com.vccorp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vccorp.convert.UserConvert;
import com.vccorp.dao.UserDAO;
import com.vccorp.dto.UserDTO;
import com.vccorp.model.UserModel;
import com.vccorp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Override
	public ResponseEntity<String> findAll() {
		StringBuilder result = new StringBuilder();
		List<UserModel> users = userDAO.findAll();
		result.append("[");
		for (UserModel user : users) {
			result.append("\n" + UserConvert.toString(user));
		}
		result.append("\n]");
		return new ResponseEntity<>(result.toString(), HttpStatus.OK);
	}

	public boolean checkEmail(String email, List<UserModel> users) {
		for (UserModel user : users) {
			if (user.getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public ResponseEntity<String> save(UserDTO userDTO) {
		List<UserModel> users = userDAO.findAll();
		String email = userDTO.getEmail();
		if (checkEmail(email, users)) { // user da ton tai tra ve user cu
			UserModel model = userDAO.findOneByEmail(email);
			return new ResponseEntity<>(UserConvert.toString(model), HttpStatus.BAD_REQUEST);
		} else { // them user moi
			UserModel model = userDAO.save(userDTO);
			return new ResponseEntity<>(UserConvert.toString(model), HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<String> delete(String email) {
		List<UserModel> users = userDAO.findAll();
		String result;
		if (checkEmail(email, users)) { // user da ton tai -> delete
			userDAO.delete(email);
			result = "delete success" + email;
		} else { // no user -> error
			result = "no user has " + email;
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> update(UserDTO userDTO) {
		List<UserModel> users = userDAO.findAll();
		String email = userDTO.getEmail();
		if (checkEmail(email, users)) { // user da ton tai thi update
			UserModel model = userDAO.update(userDTO);
			return new ResponseEntity<>(UserConvert.toString(model), HttpStatus.OK);
		} else { // chua ton tai user -> error
			return new ResponseEntity<>("NOT FOUND", HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<String> findByName(String name) {
		StringBuilder result = new StringBuilder();
		List<UserModel> users = userDAO.findByName(name);
		result.append("[");
		for (UserModel user : users) {
			result.append("\n" + UserConvert.toString(user));
		}
		result.append("\n]");
		return new ResponseEntity<>(result.toString(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> findByAddress(String address) {
		StringBuilder result = new StringBuilder();
		List<UserModel> users = userDAO.findByAddress(address);
		result.append("[");
		for (UserModel user : users) {
			result.append("\n" + UserConvert.toString(user));
		}
		result.append("\n]");
		return new ResponseEntity<>(result.toString(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> findAllBySortName() {
		StringBuilder result = new StringBuilder();
		List<UserModel> users = userDAO.findAllBySortName();
		result.append("[");
		for (UserModel user : users) {
			result.append("\n" + UserConvert.toString(user));
		}
		result.append("\n]");
		return new ResponseEntity<>(result.toString(), HttpStatus.OK);
	}
}
