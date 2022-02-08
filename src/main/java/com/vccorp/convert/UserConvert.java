package com.vccorp.convert;

import com.vccorp.model.UserModel;

public class UserConvert {

	public static String toString(UserModel model) {
		StringBuilder result = new StringBuilder("{");
		result.append("\n\t\"id\": " + model.getId());
		result.append("\n\t\"name: " + model.getName());
		result.append("\n\t\"address\": " + model.getAddress());
		result.append("\n\t\"age\": " + model.getAge());
		result.append("\n\t\"email\": " + model.getEmail());
		result.append("\n}");
		return result.toString();
	}
}
