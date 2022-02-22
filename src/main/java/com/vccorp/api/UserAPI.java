package com.vccorp.api;

import java.util.zip.DataFormatException;

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
import com.vccorp.exception.AddressNotFoundException;
import com.vccorp.exception.DataExistException;
import com.vccorp.exception.MoneyAException;
import com.vccorp.exception.NotEnoughMoneyException;
import com.vccorp.service.UserService;

@RestController
@RequestMapping(value = "/api-user")
public class UserAPI {

	@Autowired
	private UserService userService;

	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@PostMapping
	public ResponseEntity<String> save(@RequestBody UserDTO userDTO)
			throws NameNotFoundException, AddressNotFoundException, DataFormatException, DataExistException {
		return new ResponseEntity<>(gson.toJson(userService.save(userDTO)), HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<String> delete(@RequestParam String email) {
		return new ResponseEntity<>(gson.toJson(userService.delete(email)), HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<String> update(@RequestBody UserDTO userDTO)
			throws NameNotFoundException, AddressNotFoundException, DataFormatException {
		return new ResponseEntity<>(gson.toJson(userService.update(userDTO)), HttpStatus.OK);
	}

	@GetMapping(params = "name")
	public ResponseEntity<String> findByName(@RequestParam String name) {
		return new ResponseEntity<>(gson.toJson(userService.findAllByName(name)), HttpStatus.OK);
	}

	@GetMapping(params = "address")
	public ResponseEntity<String> findByAddress(@RequestParam String address) {
		return new ResponseEntity<>(gson.toJson(userService.findByAddress(address)), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<String> findAllBySortName() {
		return new ResponseEntity<>(gson.toJson(userService.findAllBySortName()), HttpStatus.OK);
	}

	@GetMapping(params = "startname")
	public ResponseEntity<String> findAllByNameStart(@RequestParam String startname) {
		return new ResponseEntity<>(gson.toJson(userService.findAllByStartName(startname)), HttpStatus.OK);
	}

	@GetMapping(params = "inname")
	public ResponseEntity<String> findAllByInName(@RequestParam String inname) {
		return new ResponseEntity<>(gson.toJson(userService.findAllByInName(inname)), HttpStatus.OK);
	}

	@GetMapping(value = "/ids")
	public ResponseEntity<String> findAllByListID(@RequestBody UserDTO userDTO) {
		return new ResponseEntity<>(gson.toJson(userService.findAllByListID(userDTO.getIds())), HttpStatus.OK);
	}

	@PutMapping(params = { "id", "money" })
	public ResponseEntity<String> addMoney(@RequestParam Long id, @RequestParam Long money) {
		return new ResponseEntity<>(gson.toJson(userService.addMoney(id, money)), HttpStatus.OK);
	}

	@PutMapping(params = { "ida", "idb", "money" })
	public ResponseEntity<String> transMoney(@RequestParam Long ida, @RequestParam Long idb, @RequestParam Long money)
			throws NotEnoughMoneyException, MoneyAException {
		return new ResponseEntity<>(gson.toJson(userService.transMoney(ida, idb, money)), HttpStatus.OK);
	}
}
