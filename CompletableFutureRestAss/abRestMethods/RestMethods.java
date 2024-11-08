package abRestMethods;

import abInterfaces.Helper;
import abPojos.User;
import abUtil.MyUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
//import pojos.User;

public class RestMethods {

	static String baseUrl = "https://simple-books-api.glitch.me";
	static String tokenUri = "/api-clients/";
	static String ordesUri = "/orders/";
	public static Header header;

	public static Helper<Response, Object> getToken = (Object user) -> {
		RequestSpecification rs = RestAssured.given();
		rs.baseUri(baseUrl);
		rs.body(MyUtil.toJson.execute(user));
		rs.contentType(ContentType.JSON);
		return rs.post(tokenUri);

	};
	public static Helper<Response, Object> orderProdukt = (Object order) -> {
		RequestSpecification rs = RestAssured.given();
		rs.baseUri(baseUrl);
		rs.body(MyUtil.toJson.execute(order));
		rs.contentType(ContentType.JSON);
		if (header != null) {
			rs.header(header);
		}
		return rs.post(ordesUri);

	};

}
