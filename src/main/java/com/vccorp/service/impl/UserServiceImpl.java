package com.vccorp.service.impl;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vccorp.dao.UserDAO;
import com.vccorp.dto.UserDTO;
import com.vccorp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

//	@Autowired
//	private UserRepository userRepository;

	@Autowired
	private UserDAO userDAO;

	@Override
	public List<UserDTO> findAll() {
//		List<UserDTO> results = new ArrayList<>();
//		List<UserEntity> entities = userRepository.findAll();
//		for (UserEntity userEntity : entities) {
//			results.add(UserConvert.toDTO(userEntity));
//		}
//		return results;
		return userDAO.findAll();
	}

	public boolean checkEmail(String email, List<UserDTO> users) {
		for (UserDTO userDTO : users) {
			if (userDTO.getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}

	@Override
//	@Transactional
	public UserDTO save(UserDTO userDTO) {
		List<UserDTO> users = findAll();
		String email = userDTO.getEmail();
		if (checkEmail(email, users)) {	//user da ton tai tra ve user cu
//			return UserConvert.toDTO(userRepository.findOneByEmail(email));
			return userDAO.findOneByEmail(email);
		} else {	//them user moi
//			UserEntity entity = UserConvert.toEntity(userDTO);
//			return UserConvert.toDTO(userRepository.save(entity));
			return userDAO.save(userDTO);
		}
	}

	@Override
//	@Transactional
	public String delete(String email) {
		List<UserDTO> users = findAll();
//		UserEntity entity = userRepository.findOneByEmail(email);
		String result;
		if (checkEmail(email, users)) {
//			userRepository.delete(entity);
			userDAO.delete(email);
			result = "delete success" + email;
		} else {
			result = "no user has " + email;
		}
		return result;
	}

	@Override
//	@Transactional
	public UserDTO update(UserDTO userDTO) throws FileNotFoundException {
		List<UserDTO> users = findAll();
		String email = userDTO.getEmail();
		if (checkEmail(email, users)) {	//user da ton tai thi update
//			UserEntity entity = userRepository.findOneByEmail(userDTO.getEmail());
//			entity = UserConvert.toEntity(userDTO, entity);
//			userRepository.save(entity);
//			return UserConvert.toDTO(entity);
			return userDAO.update(userDTO);
		} else {	//chua ton tai user -> error
			throw new FileNotFoundException("User not found");
		}
	}

	@Override
	public List<UserDTO> findByName(String name) {
//		List<UserDTO> results = new ArrayList<>();
//		List<UserEntity> entities = userRepository.findByName(name);
//		for (UserEntity userEntity : entities) {
//			results.add(UserConvert.toDTO(userEntity));
//		}
//		return results;
		return userDAO.findByName(name);
	}

	@Override
	public List<UserDTO> findByAddress(String address) {
//		List<UserDTO> results = new ArrayList<>();
//		List<UserEntity> entities = userRepository.findByAddress(address);
//		for (UserEntity userEntity : entities) {
//			results.add(UserConvert.toDTO(userEntity));
//		}
//		return results;
		return userDAO.findByAddress(address);
	}

	@Override
	public List<UserDTO> findAllBySortName() {
//		Sort sort = Sort.by("name").ascending();
//		List<UserDTO> results = new ArrayList<>();
//		List<UserEntity> entities = userRepository.findAll(sort);
//		for (UserEntity userEntity : entities) {
//			results.add(UserConvert.toDTO(userEntity));
//		}
//		return results;
		return userDAO.findAllBySortName();
	}
}
