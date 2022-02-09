package com.vccorp.convert;

import com.vccorp.dto.UserDTO;
import com.vccorp.model.UserModel;

public class UserConvert {

	public static UserDTO toDTO(UserModel model) {
		UserDTO dto = new UserDTO();
		dto.setId(model.getId());
		dto.setName(model.getName());
		dto.setAddress(model.getAddress());
		dto.setAge(model.getAge());
		dto.setEmail(model.getEmail());
		return dto;
	}

}
