package com.vccorp.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NameNotFoundException;

import org.hibernate.validator.internal.engine.messageinterpolation.parser.MessageDescriptorFormatException;
import org.springframework.beans.factory.annotation.Autowired;
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
	public List<UserDTO> findAll() {
		List<UserDTO> results = new ArrayList<>();
		List<UserModel> users = userDAO.findAll();
		for (UserModel user : users) {
			results.add(UserConvert.toDTO(user));
		}
		return results;
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
	public UserDTO save(UserDTO userDTO) {
		if (userDTO.getName().isEmpty())
			throw new MessageDescriptorFormatException("Name not empty");
		if (userDTO.getAddress().isEmpty())
			throw new MessageDescriptorFormatException("Address not empty");
		if (userDTO.getAge() < 1 || userDTO.getAge() > 100)
			throw new MessageDescriptorFormatException(" 1 < age < 100");
		List<UserModel> users = userDAO.findAll();
		String email = userDTO.getEmail();
		if (checkEmail(email, users)) { // user da ton tai tra ve user cu
			UserModel model = userDAO.findOneByEmail(email);
			return UserConvert.toDTO(model);
		} else { // them user moi
			UserModel model = userDAO.save(userDTO);
			return UserConvert.toDTO(model);
		}
	}

	@Override
	public String delete(String email) {
		List<UserModel> users = userDAO.findAll();
		String result;
		if (checkEmail(email, users)) { // user da ton tai -> delete
			userDAO.delete(email);
			result = "delete success" + email;
		} else { // no user -> error
			result = "no user has " + email;
		}
		return result;
	}

	@Override
	public UserDTO update(UserDTO userDTO) throws NameNotFoundException {
		if (userDTO.getName().isEmpty())
			throw new MessageDescriptorFormatException("Name not empty");
		if (userDTO.getAddress().isEmpty())
			throw new MessageDescriptorFormatException("Address not empty");
		if (userDTO.getAge() < 1 || userDTO.getAge() > 100)
			throw new MessageDescriptorFormatException(" 1 < age < 100");
		List<UserModel> users = userDAO.findAll();
		String email = userDTO.getEmail();
		if (checkEmail(email, users)) { // user da ton tai thi update
			UserModel model = userDAO.update(userDTO);
			return UserConvert.toDTO(model);
		} else { // chua ton tai user -> error
			throw new NameNotFoundException();
		}
	}

	@Override
	public List<UserDTO> findByName(String name) {
		List<UserDTO> results = new ArrayList<>();
		List<UserModel> users = userDAO.findByName(name);
		for (UserModel user : users) {
			results.add(UserConvert.toDTO(user));
		}
		return results;
	}

	@Override
	public List<UserDTO> findByAddress(String address) {
		List<UserDTO> results = new ArrayList<>();
		List<UserModel> users = userDAO.findByAddress(address);
		for (UserModel user : users) {
			results.add(UserConvert.toDTO(user));
		}
		return results;
	}

	@Override
	public List<UserDTO> findAllBySortName() {
		List<UserDTO> results = new ArrayList<>();
		List<UserModel> users = userDAO.findAllBySortName();
		for (UserModel user : users) {
			results.add(UserConvert.toDTO(user));
		}
		return results;
	}
}
