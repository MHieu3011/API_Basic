package com.vccorp.service.impl;

import java.util.List;
import java.util.zip.DataFormatException;

import javax.naming.NameNotFoundException;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vccorp.api.ResponseAPICustom;
import com.vccorp.dao.UserDAO;
import com.vccorp.dto.UserDTO;
import com.vccorp.exception.AddressNotFoundException;
import com.vccorp.exception.DataExistException;
import com.vccorp.exception.NotEnoughMoneyException;
import com.vccorp.model.UserModel;
import com.vccorp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	private static final String SUCCESS = "Success";

	@Override
	public ResponseAPICustom findAll() {
		ResponseAPICustom response;
		List<UserModel> users = userDAO.findAll();
		if (users.isEmpty()) {
			throw new NoResultException();
		}
		response = new ResponseAPICustom(1, SUCCESS, 200, users);
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
	public ResponseAPICustom save(UserDTO userDTO)
			throws NameNotFoundException, AddressNotFoundException, DataFormatException, DataExistException {
		ResponseAPICustom response;
		String str = "[a-zA-Z0-9]";
		if (userDTO.getName().isEmpty()) {
			throw new NameNotFoundException();
		}
		if (!userDTO.getName().matches(str)) {
			throw new DataFormatException();
		}
		if (userDTO.getAddress().isEmpty()) {
			throw new AddressNotFoundException();
		}
		if (userDTO.getAge() < 1 || userDTO.getAge() > 100) {
			throw new DataFormatException();
		}
		List<UserModel> users = userDAO.findAll();
		String email = userDTO.getEmail();
		if (checkEmail(email, users)) { // user da ton tai
			throw new DataExistException();
		}
		// them user moi
		UserModel model = userDAO.save(userDTO);
		response = new ResponseAPICustom(1, SUCCESS, 200, model);
		return response;
	}

	@Override
	public ResponseAPICustom delete(String email) {
		ResponseAPICustom response;
		List<UserModel> users = userDAO.findAll();
		if (checkEmail(email, users)) { // user da ton tai -> delete
			userDAO.delete(email);
			response = new ResponseAPICustom(1, SUCCESS, 200, email);
			return response;
		}
		// no user -> error
		throw new NoResultException();
	}

	@Override
	public ResponseAPICustom update(UserDTO userDTO)
			throws NameNotFoundException, AddressNotFoundException, DataFormatException {
		ResponseAPICustom response;
		String str = "[a-zA-Z0-9]";
		if (userDTO.getName().isEmpty()) {
			throw new NameNotFoundException();
		}
		if (!userDTO.getName().matches(str)) {
			throw new DataFormatException();
		}
		if (userDTO.getAddress().isEmpty()) {
			throw new AddressNotFoundException();
		}
		if (userDTO.getAge() < 1 || userDTO.getAge() > 100) {
			throw new DataFormatException();
		}
		List<UserModel> users = userDAO.findAll();
		String email = userDTO.getEmail();
		if (checkEmail(email, users)) { // user da ton tai thi update
			UserModel model = userDAO.update(userDTO);
			response = new ResponseAPICustom(1, SUCCESS, 200, model);
			return response;
		}
		// chua ton tai user -> error
		throw new NoResultException();
	}

	@Override
	public ResponseAPICustom findAllByName(String name) {
		ResponseAPICustom response;
		List<UserModel> users = userDAO.findAllByName(name);
		if (users.isEmpty()) {
			throw new NoResultException();
		}
		response = new ResponseAPICustom(1, SUCCESS, 200, users);
		return response;

	}

	@Override
	public ResponseAPICustom findByAddress(String address) {
		ResponseAPICustom response;
		List<UserModel> users = userDAO.findByAddress(address);
		if (users.isEmpty()) {
			throw new NoResultException();
		}
		response = new ResponseAPICustom(1, SUCCESS, 200, users);
		return response;
	}

	@Override
	public ResponseAPICustom findAllBySortName() {
		ResponseAPICustom response;
		List<UserModel> users = userDAO.findAllBySortName();
		if (users.isEmpty()) {
			throw new NoResultException();
		}
		response = new ResponseAPICustom(1, SUCCESS, 200, users);
		return response;
	}

	@Override
	public ResponseAPICustom findAllByStartName(String startName) {
		ResponseAPICustom response;
		String parameter = startName + "%";
		List<UserModel> users = userDAO.findAllByLikeName(parameter);
		if (users.isEmpty()) {
			throw new NoResultException();
		}
		response = new ResponseAPICustom(1, SUCCESS, 200, users);
		return response;
	}

	@Override
	public ResponseAPICustom findAllByInName(String inName) {
		ResponseAPICustom response;
		List<UserModel> users = userDAO.findAllByMatchName(inName);
		if (users.isEmpty()) {
			throw new NoResultException();
		}
		response = new ResponseAPICustom(1, SUCCESS, 200, users);
		return response;
	}

	@Override
	public ResponseAPICustom findAllByListID(long[] ids) {
		if (ids.length == 0) {
			throw new NoResultException();
		}
		ResponseAPICustom response;
		StringBuilder s = new StringBuilder("(");
		for (int i = 1; i < ids.length; i++) {
			s.append("?, ");
		}
		s.append("?)");
		List<UserModel> users = userDAO.findAllByListID(s.toString(), ids);
		response = new ResponseAPICustom(1, SUCCESS, 200, users);
		return response;
	}

	@Override
	public ResponseAPICustom addMoney(Long id, Long money) {
		UserModel user = userDAO.findOneById(id);
		if (user == null) {
			throw new NoResultException();
		}
		user = userDAO.addMoney(id, money);
		return new ResponseAPICustom(1, SUCCESS, 200, user);
	}

	@Override
	public ResponseAPICustom transMoney(Long idA, Long idB, Long money) throws NotEnoughMoneyException {
		UserModel userA = userDAO.findOneById(idA);
		UserModel userB = userDAO.findOneById(idB);
		if (userA == null || userB == null) {
			throw new NoResultException();
		}
		if (userA.getMoney() < money) {
			throw new NotEnoughMoneyException();
		}
		List<UserModel> users = userDAO.transMoney(idA, idB, money);
		return new ResponseAPICustom(1, SUCCESS, 200, users);
	}

}
