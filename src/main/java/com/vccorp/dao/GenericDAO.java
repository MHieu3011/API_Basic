package com.vccorp.dao;

import java.util.List;

import com.vccorp.mapper.RowMapper;

public interface GenericDAO<T> {

	List<T> query(String sql, RowMapper<T> mapper, Object... parameters);

	Long insert(String sql, Object... parameters);

	void update(String sql, Object... parameters);

}
