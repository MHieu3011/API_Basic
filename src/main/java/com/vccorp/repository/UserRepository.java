package com.vccorp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vccorp.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findOneByEmail(String email);

	List<UserEntity> findByName(String name);

	List<UserEntity> findByAddress(String address);

}
