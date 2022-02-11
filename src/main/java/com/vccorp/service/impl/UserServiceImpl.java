package com.vccorp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vccorp.api.ResponseAPICustom;
import com.vccorp.dao.UserDAO;
import com.vccorp.dto.UserDTO;
import com.vccorp.model.UserModel;
import com.vccorp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	private static final String USERNF = "User not found";
	private static final String SUCCESS = "Success";

	@Override
	public ResponseAPICustom findAll() {
		ResponseAPICustom response;
		List<UserModel> users = userDAO.findAll();
		if (users.isEmpty()) {
			response = new ResponseAPICustom(0, USERNF, 404, "");
		} else {
			response = new ResponseAPICustom(1, SUCCESS, 200, users);
		}
		return response;
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
	public ResponseAPICustom save(UserDTO userDTO) {
		ResponseAPICustom response;
		if (userDTO.getName().isEmpty()) {
			response = new ResponseAPICustom(0, "Name not empty", 900, "");
			return response;
		}
		if (userDTO.getAddress().isEmpty()) {
			response = new ResponseAPICustom(0, "Address not empty", 900, "");
			return response;
		}
		if (userDTO.getAge() < 1 || userDTO.getAge() > 100) {
			response = new ResponseAPICustom(0, "0 < age < 100", 900, "");
			return response;
		}
		List<UserModel> users = userDAO.findAll();
		String email = userDTO.getEmail();
		if (checkEmail(email, users)) { // user da ton tai tra ve user cu
			UserModel model = userDAO.findOneByEmail(email);
			response = new ResponseAPICustom(0, "User da ton tai", 902, model);
		} else { // them user moi
			UserModel model = userDAO.save(userDTO);
			response = new ResponseAPICustom(1, SUCCESS, 200, model);
		}
		return response;
	}

	@Override
	public ResponseAPICustom delete(String email) {
		ResponseAPICustom response;
		List<UserModel> users = userDAO.findAll();
		if (checkEmail(email, users)) { // user da ton tai -> delete
			userDAO.delete(email);
			response = new ResponseAPICustom(1, SUCCESS, 200, email);
		} else { // no user -> error
			response = new ResponseAPICustom(0, USERNF, 404, email);
		}
		return response;
	}

	@Override
	public ResponseAPICustom update(UserDTO userDTO) {
		ResponseAPICustom response;
		if (userDTO.getName().isEmpty()) {
			response = new ResponseAPICustom(0, "Name not empty", 900, "");
			return response;
		}
		if (userDTO.getAddress().isEmpty()) {
			response = new ResponseAPICustom(0, "Address not empty", 900, "");
			return response;
		}
		if (userDTO.getAge() < 1 || userDTO.getAge() > 100) {
			response = new ResponseAPICustom(0, "0 < age < 100", 900, "");
			return response;
		}
		List<UserModel> users = userDAO.findAll();
		String email = userDTO.getEmail();
		if (checkEmail(email, users)) { // user da ton tai thi update
			UserModel model = userDAO.update(userDTO);
			response = new ResponseAPICustom(1, SUCCESS, 200, model);
		} else { // chua ton tai user -> error
			response = new ResponseAPICustom(0, USERNF, 404, userDTO);
		}
		return response;
	}

	@Override
	public ResponseAPICustom findByName(String name) {
		ResponseAPICustom response;
		List<UserModel> users = userDAO.findByName(name);
		if (users.isEmpty()) {
			response = new ResponseAPICustom(0, USERNF, 404, name);
		} else {
			response = new ResponseAPICustom(1, SUCCESS, 200, users);
		}
		return response;
	}

	@Override
	public ResponseAPICustom findByAddress(String address) {
		ResponseAPICustom response;
		List<UserModel> users = userDAO.findByAddress(address);
		if (users.isEmpty()) {
			response = new ResponseAPICustom(0, USERNF, 404, address);
		} else {
			response = new ResponseAPICustom(1, SUCCESS, 200, users);
		}
		return response;
	}

	@Override
	public ResponseAPICustom findAllBySortName() {
		ResponseAPICustom response;
		List<UserModel> users = userDAO.findAllBySortName();
		if (users.isEmpty()) {
			response = new ResponseAPICustom(0, USERNF, 404, "");
		} else {
			response = new ResponseAPICustom(1, SUCCESS, 200, users);
		}
		return response;
	}
}
