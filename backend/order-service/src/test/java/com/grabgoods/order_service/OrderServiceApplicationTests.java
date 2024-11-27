package com.grabgoods.order_service;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;

import com.grabgoods.order_service.dto.OrderRequest;

import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceApplicationTests {

	@ServiceConnection
	static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.3.0");
	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mySQLContainer.start();
	}

	@Test
	void shouldSubmitOrder() {
		OrderRequest orderRequest = getOrderRequest();

		var responseBodyString = RestAssured.given()
				.contentType("application/json")
				.body(orderRequest)
				.when()
				.post("/api/orders")
				.then()
				.log().all()
				.statusCode(201)
				.extract()
				.body().asString();

		assertThat(responseBodyString, Matchers.is("Order placed successfully!"));
	}

	private OrderRequest getOrderRequest() {

		return new OrderRequest("iPhone 16 Pro Max", BigDecimal.valueOf(125000), Integer.valueOf(1600));

	}
}
