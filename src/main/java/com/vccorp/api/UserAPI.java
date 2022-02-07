package com.vccorp.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vccorp.dto.UserDTO;
import com.vccorp.service.UserService;

@RestController
@RequestMapping(value = "/api-user")
public class UserAPI {

	@Autowired
	private UserService userService;

	@PostMapping
	public UserDTO save(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			throw new Exception("Dữ liệu nhập vào không đúng vui lòng kiểm tra lại!");
		}
		return userService.save(userDTO);
	}

	@DeleteMapping
	public String delete(@RequestParam String email) {
		return userService.delete(email);
	}

	@PutMapping
	public UserDTO update(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			throw new Exception("Dữ liệu nhập vào không đúng vui lòng kiểm tra lại!");
		}
		return userService.update(userDTO);
	}

	@GetMapping(params = "name")
	public List<UserDTO> findByName(@RequestParam String name) {
		return userService.findByName(name);
	}

	@GetMapping(params = "address")
	public List<UserDTO> findByAddress(@RequestParam String address) {
		return userService.findByAddress(address);
	}

	@GetMapping
	public List<UserDTO> findAllBySortName() {
		return userService.findAllBySortName();
	}
}
