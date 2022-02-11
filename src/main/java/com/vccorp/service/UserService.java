package com.vccorp.service;

import com.vccorp.api.ResponseAPICustom;
import com.vccorp.dto.UserDTO;

public interface UserService {

	ResponseAPICustom findAll();

	ResponseAPICustom save(UserDTO userDTO);

	ResponseAPICustom delete(String email);

	ResponseAPICustom update(UserDTO userDTO);

	ResponseAPICustom findByName(String name);

	ResponseAPICustom findByAddress(String address);

	ResponseAPICustom findAllBySortName();
}
