package com.vccorp.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.vccorp.convert.UserConvert;
import com.vccorp.dto.UserDTO;
import com.vccorp.entity.UserEntity;
import com.vccorp.repository.UserRepository;
import com.vccorp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<UserDTO> findAll() {
		List<UserDTO> results = new ArrayList<>();
		List<UserEntity> entities = userRepository.findAll();
		for (UserEntity userEntity : entities) {
			results.add(UserConvert.toDTO(userEntity));
		}
		return results;
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
	@Transactional
	public UserDTO save(UserDTO userDTO) {
		List<UserDTO> users = findAll();
		String email = userDTO.getEmail();
		if (checkEmail(email, users)) {
			return UserConvert.toDTO(userRepository.findOneByEmail(email));
		} else {
			UserEntity entity = UserConvert.toEntity(userDTO);
			return UserConvert.toDTO(userRepository.save(entity));
		}
	}

	@Override
	@Transactional
	public String delete(String email) {
		List<UserDTO> users = findAll();
		UserEntity entity = userRepository.findOneByEmail(email);
		String result;
		if (checkEmail(email, users)) {
			userRepository.delete(entity);
			result = "Xóa thành công user: " + email;
		} else {
			result = "Không tìm thấy user: " + email;
		}
		return result;
	}

	@Override
	@Transactional
	public UserDTO update(UserDTO userDTO) throws Exception {
		List<UserDTO> users = findAll();
		String email = userDTO.getEmail();
		if (checkEmail(email, users)) {
			UserEntity entity = userRepository.findOneByEmail(userDTO.getEmail());
			entity = UserConvert.toEntity(userDTO, entity);
			userRepository.save(entity);
			return UserConvert.toDTO(entity);
		} else {
			throw new Exception("User không tồn tại");
		}
	}

	@Override
	public List<UserDTO> findByName(String name) {
		List<UserDTO> results = new ArrayList<>();
		List<UserEntity> entities = userRepository.findByName(name);
		for (UserEntity userEntity : entities) {
			results.add(UserConvert.toDTO(userEntity));
		}
		return results;
	}

	@Override
	public List<UserDTO> findByAddress(String address) {
		List<UserDTO> results = new ArrayList<>();
		List<UserEntity> entities = userRepository.findByAddress(address);
		for (UserEntity userEntity : entities) {
			results.add(UserConvert.toDTO(userEntity));
		}
		return results;
	}

	@Override
	public List<UserDTO> findAllBySortName() {
		Sort sort = Sort.by("name").ascending();
		List<UserDTO> results = new ArrayList<>();
		List<UserEntity> entities = userRepository.findAll(sort);
		for (UserEntity userEntity : entities) {
			results.add(UserConvert.toDTO(userEntity));
		}
		return results;
	}
}
