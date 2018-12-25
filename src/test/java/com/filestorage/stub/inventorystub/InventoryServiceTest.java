package com.filestorage.stub.inventorystub;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.filestorage.stub.model.AuthResponse;
import com.filestorage.stub.model.Item;
import com.filestorage.stub.model.ItemResponse;
import com.filestorage.stub.service.InventoryServiceAPI;
import com.jayway.restassured.response.Response;

import lombok.extern.slf4j.Slf4j;

/**
 * Unit test for API service
 */
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InventoryServiceTest {
	private static final String AUTH_END_POINT = "http://localhost:8080";
	private static final String END_POINT = "http://localhost:8088";
	private static final String CLIENT_ID = "fooClientIdPassword";
	private static final String  SECRET = "secret";
	private static final String USER = "user1";
	private static final String PASSWORD = "password";
	private static final String GRAND_TYPE = "password";
	private static final String FILE_NAME = "/tmp/item.json";
	private static final String ITEM_CODE = "12";
	
	InventoryServiceAPI api;
	AuthResponse response;
	String token;
	
	@Before
	public void beforeClass() throws Exception {
		response = InventoryServiceAPI.authorization(CLIENT_ID, SECRET, AUTH_END_POINT).getSession(GRAND_TYPE, CLIENT_ID, USER, PASSWORD);
		token = response != null ? response.getAccessToken() : "";
	}
	
	@Test
	public void test1Login() throws JsonProcessingException {
		final Map<String, String> params = new HashMap<String, String>();
		params.put("grant_type", "password");
		params.put("client_id", "fooClientIdPassword");
		params.put("username", "user1");
		params.put("password", "password");
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		
		Response response = given().when()
				.auth().preemptive().basic("fooClientIdPassword", "secret").and()
				.with().params(params)
				.log().body(true)
				.expect().statusCode(200)
				.post(AUTH_END_POINT + "/oauth/token")
				.then().extract().response();
		
		System.out.println(JSONUtils.prettyPrintJSON(response.asString()));
		
	}
	
	@Test
	public void test2getLogin() throws JsonProcessingException {
		AuthResponse response = InventoryServiceAPI.authorization(CLIENT_ID, SECRET, AUTH_END_POINT).getSession(GRAND_TYPE, CLIENT_ID, USER, PASSWORD);
		log.info(response.getAccessToken());
	}
	
	@Test
	public void test3getItems() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		Response response = given().header("Authorization","Bearer " + token)
				.get(END_POINT + "/item")
				.then().extract().response();
		System.out.println(JSONUtils.prettyPrintJSON(response.asString()));
	}
	
	@Test
	public void test4getItems() throws JsonProcessingException {
		List<Item> list = InventoryServiceAPI.connect(END_POINT, token, FILE_NAME).getAllItems();
		assertNotNull(list);
		list.forEach(a -> log.info(a.toString()));
	}
	
	@Test
	public void test5AddItem() throws JsonProcessingException {
		Item item = new Item();
		item.setCode(ITEM_CODE);
		item.setDescription("Laptop");
		item.setQuantity(10);
		ItemResponse itemResponse = InventoryServiceAPI.connect(END_POINT, token, FILE_NAME).addItem(item);
		assertNotNull(itemResponse);
		assertEquals("success", itemResponse.getStatus());
		log.info(itemResponse.getMessage());
	}
	
	@Test
	public void test6delete() throws JsonProcessingException {
		ItemResponse itemResponse = InventoryServiceAPI.connect(END_POINT, token, FILE_NAME).delete(ITEM_CODE);
		assertNotNull(itemResponse);
		assertEquals("success", itemResponse.getStatus());
		log.info(itemResponse.getStatus());
	}
}
