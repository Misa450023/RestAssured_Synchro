package abUtil;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import abInterfaces.Helper;
import abPojos.Order;
import abPojos.User;

public class MyUtil {

	static ObjectMapper om = new ObjectMapper();
	static Faker f = new Faker();

	public static Helper<String, Object> toJson = (Object o) -> {
		try {
			return om.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "error";
	};

	public static Helper<List<User>, Integer> createListOfUsers = (Integer i) -> {
		List<User> list = new ArrayList<>();
		for (int a = 0; a <= i; a++) {
			list.add(new User(f.name().firstName(), f.internet().emailAddress()));
		}
		return list;
	};
	public static Helper<List<Order>, Integer> createListOfOrders = (Integer i) -> {
		List<Order> list = new ArrayList<>();
		for (int a = 0; a <= i; a++) {
			list.add(new Order(5, f.name().firstName()));
		}
		return list;
	};

}
