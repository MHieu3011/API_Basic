package com.vccorp.configuration;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariConfiguration {

	private static HikariConfig config = new HikariConfig();

	private static HikariDataSource hikari;

	static {
		config.setDriverClassName(DBConfiguration.DB_DRIVER);
		config.setJdbcUrl(DBConfiguration.CONNECTION_URL);
		config.setUsername(DBConfiguration.USER_NAME);
		config.setPassword(DBConfiguration.PASSWORD);
		config.setMinimumIdle(DBConfiguration.DB_MIN_CONNECTIONS);
		config.setMaximumPoolSize(DBConfiguration.DB_MAX_CONNECTIONS);
	}

	public static Connection getConnection() throws SQLException {
		if (hikari == null) {
			hikari = new HikariDataSource(config);
		}
		return hikari.getConnection();
	}
}
