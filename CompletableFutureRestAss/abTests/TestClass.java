package abTests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.testng.IClass;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import abListener.ABListener;
import abPojos.Order;
import abPojos.User;
import abRestMethods.CFMethods;
import abRestMethods.RestMethods;
import abUtil.MyUtil;
import io.restassured.http.Header;
import io.restassured.response.Response;
import junit.framework.Assert;

@Listeners(ABListener.class)
public class TestClass {

	List<Response> resps = new ArrayList<>();
	List<String> tokens = new ArrayList<>();
	int number = 5;

	@Test(priority = 0)
	public void getToken() throws InterruptedException, ExecutionException {

		List<CompletableFuture<Response>> futures = MyUtil.createListOfUsers.execute(number).stream()
				.map(user -> CFMethods.multiReqCreateUser.execute(user)).collect(Collectors.toList());

		CompletableFuture<Void> allResp = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
		allResp.get();

		futures.stream().forEach(fr -> {
			try {
				Response r = fr.get();
				tokens.add(r.jsonPath().get("accessToken").toString());

				Assert.assertEquals(r.statusCode(), 201);
				Assert.assertNotNull(r.jsonPath().get("accessToken").toString());
				// Assert.assertTrue(tokens.stream().filter(t->!t.isEmpty()).count()==number+1);
				resps.add(r);

			} catch (InterruptedException e) {
				System.out.println("token not generated");
			} catch (ExecutionException e) {
				System.out.print("FAILED");
			}
		});
		ABListener.resps = resps;
	}

	@Test(priority = 1)
	public void orderBook() throws InterruptedException, ExecutionException {

		List<Order> orders = MyUtil.createListOfOrders.execute(number);
		Map<Order, String> orderData = IntStream.range(0, Math.min(orders.size(), tokens.size())).boxed()
				.collect(Collectors.toMap(orders::get, tokens::get));

		List<CompletableFuture<Response>> futures = orderData.entrySet().stream().map(entry -> {
			Order order = entry.getKey();
			RestMethods.header = new Header("Authorization", "Bearer " + entry.getValue());
			return CFMethods.multiorderProduct.execute(order);
		}).collect(Collectors.toList());

		CompletableFuture<Void> allResp = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
		allResp.get();

		futures.stream().forEach(fr -> {
			try {
				Response r = fr.get();
				Assert.assertEquals(r.statusCode(), 201);
				Assert.assertEquals(r.jsonPath().getBoolean("created"), true);
				Assert.assertFalse(r.jsonPath().getString("orderId").isEmpty());
				resps.add(r);

			} catch (InterruptedException | ExecutionException e) {
				System.out.println("Error occurs");
			}
		});
		ABListener.resps = resps;
	}

}
