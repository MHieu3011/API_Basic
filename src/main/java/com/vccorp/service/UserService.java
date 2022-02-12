package com.vccorp.service;

import java.util.zip.DataFormatException;

import javax.naming.NameNotFoundException;

import com.vccorp.api.ResponseAPICustom;
import com.vccorp.dto.UserDTO;
import com.vccorp.exception.AddressNotFoundException;
import com.vccorp.exception.DataExistException;

public interface UserService {

	ResponseAPICustom findAll();

	ResponseAPICustom save(UserDTO userDTO)
			throws NameNotFoundException, AddressNotFoundException, DataFormatException, DataExistException;

	ResponseAPICustom delete(String email);

	ResponseAPICustom update(UserDTO userDTO)
			throws NameNotFoundException, AddressNotFoundException, DataFormatException;

	ResponseAPICustom findByName(String name);

	ResponseAPICustom findByAddress(String address);

	ResponseAPICustom findAllBySortName();
}
