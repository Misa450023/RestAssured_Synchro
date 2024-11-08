package abRestMethods;

import java.util.concurrent.CompletableFuture;

import abInterfaces.Helper;
import io.restassured.response.Response;

public class CFMethods {

	public static Helper<CompletableFuture<Response>, Object> multiReqCreateUser = (Object o) -> {
		return CompletableFuture.supplyAsync(() -> {
			return RestMethods.getToken.execute(o);
		});
	};
	public static Helper<CompletableFuture<Response>, Object> multiorderProduct = (Object o) -> {
		return CompletableFuture.supplyAsync(() -> {
			return RestMethods.orderProdukt.execute(o);
		});
	};



}
