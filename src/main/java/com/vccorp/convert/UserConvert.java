package com.vccorp.convert;

import com.vccorp.dto.UserDTO;
import com.vccorp.entity.UserEntity;

public class UserConvert {

	public static UserDTO toDTO(UserEntity entity) {
		UserDTO dto = new UserDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setAddress(entity.getAddress());
		dto.setAge(entity.getAge());
		dto.setEmail(entity.getEmail());
		dto.setCreatedDate(entity.getCreatedDate());
		dto.setCreatedBy(entity.getCreatedBy());
		dto.setModifiedDate(entity.getModifiedDate());
		dto.setModifiedBy(entity.getModifiedBy());
		return dto;
	}

	public static UserEntity toEntity(UserDTO dto) {
		UserEntity entity = new UserEntity();
		entity.setName(dto.getName());
		entity.setAddress(dto.getAddress());
		entity.setAge(dto.getAge());
		entity.setEmail(dto.getEmail());
		return entity;
	}

	public static UserEntity toEntity(UserDTO dto, UserEntity entity) {
		entity.setName(dto.getName());
		entity.setAddress(dto.getAddress());
		entity.setAge(dto.getAge());
		return entity;
	}
}
