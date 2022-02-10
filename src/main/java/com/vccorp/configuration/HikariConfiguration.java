package com.vccorp.configuration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariConfiguration {

	private static HikariConfiguration configuration;

	private static HikariConfig config = new HikariConfig();

	private static HikariDataSource hikari;

	private HikariConfiguration() {
	}

	private static ResourceBundle bundle = ResourceBundle.getBundle("dbconfig");

	static {
		config.setDriverClassName(bundle.getString("DB_DRIVER"));
		config.setJdbcUrl(bundle.getString("CONNECTION_URL"));
		config.setUsername(bundle.getString("USER_NAME"));
		config.setPassword(bundle.getString("PASSWORD"));
		config.setMinimumIdle(Integer.parseInt(bundle.getString("DB_MIN_CONNECTIONS")));
		config.setMaximumPoolSize(Integer.parseInt(bundle.getString("DB_MAX_CONNECTIONS")));
		hikari = new HikariDataSource(config);
	}

	public Connection getConnection() throws SQLException {
		return hikari.getConnection();
	}

	public static HikariConfiguration getInstance() {
		if (configuration == null) {
			configuration = new HikariConfiguration();
		}
		return configuration;
	}
}
