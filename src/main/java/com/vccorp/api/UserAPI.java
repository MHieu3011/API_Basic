package com.vccorp.api;

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vccorp.dto.UserDTO;
import com.vccorp.service.UserService;

@RestController
@RequestMapping(value = "/api-user")
public class UserAPI {

	@Autowired
	private UserService userService;

	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@PostMapping
	public ResponseEntity<String> save(@RequestBody UserDTO userDTO) {
		return new ResponseEntity<>(gson.toJson(userService.save(userDTO)), HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<String> delete(@RequestParam String email) {
		return new ResponseEntity<>(gson.toJson(userService.delete(email)), HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<String> update(@RequestBody UserDTO userDTO) throws NameNotFoundException {
		return new ResponseEntity<>(gson.toJson(userService.update(userDTO)), HttpStatus.OK);
	}

	@GetMapping(params = "name")
	public ResponseEntity<String> findByName(@RequestParam String name) {
		return new ResponseEntity<>(gson.toJson(userService.findByName(name)), HttpStatus.OK);
	}

	@GetMapping(params = "address")
	public ResponseEntity<String> findByAddress(@RequestParam String address) {
		return new ResponseEntity<>(gson.toJson(userService.findByAddress(address)), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<String> findAllBySortName() {
		return new ResponseEntity<>(gson.toJson(userService.findAllBySortName()), HttpStatus.OK);
	}
}
