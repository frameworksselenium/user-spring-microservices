package com.example.userspringmicroservices;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.example.userspringmicroservices.beans.Country;


@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class ControllerIntegrationTestCases {


	@Test @Order(1)
	void getAllCountriesIntegrationTest() throws JSONException {
		
		String expectedValue = "[\r\n"
				+ "    {\r\n"
				+ "        \"id\": 1,\r\n"
				+ "        \"countryName\": \"India\",\r\n"
				+ "        \"countryCapital\": \"Delhi\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        \"id\": 2,\r\n"
				+ "        \"countryName\": \"USA\",\r\n"
				+ "        \"countryCapital\": \"Washington\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        \"id\": 3,\r\n"
				+ "        \"countryName\": \"UK\",\r\n"
				+ "        \"countryCapital\": \"London\"\r\n"
				+ "    }\r\n"
				+ "]";
		
		TestRestTemplate testRestTemplate = new TestRestTemplate();
		ResponseEntity<String> response = testRestTemplate.getForEntity("http://localhost:8088/getCountries", String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		JSONAssert.assertEquals(expectedValue, response.getBody(), false);
	}
	
	@Test @Order(2)
	void getCountryByIdIntegrationTest() throws JSONException {
		
		String expectedValue = "{\r\n"
				+ "    \"id\": 1,\r\n"
				+ "    \"countryName\": \"India\",\r\n"
				+ "    \"countryCapital\": \"Delhi\"\r\n"
				+ "}";
		
		TestRestTemplate testRestTemplate = new TestRestTemplate();
		ResponseEntity<String> response = testRestTemplate.getForEntity("http://localhost:8088/getCountries/1", String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		JSONAssert.assertEquals(expectedValue, response.getBody(), false);
	}
	
	@Test @Order(3)
	void getCountryByNameIntegrationTest() throws JSONException {
		
		String expectedValue = "{\r\n"
				+ "    \"id\": 3,\r\n"
				+ "    \"countryName\": \"UK\",\r\n"
				+ "    \"countryCapital\": \"London\"\r\n"
				+ "}";
		
		TestRestTemplate testRestTemplate = new TestRestTemplate();
		ResponseEntity<String> response = testRestTemplate.getForEntity("http://localhost:8088/getCountries/countryname?name=UK", String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		JSONAssert.assertEquals(expectedValue, response.getBody(), false);
	}
	
	@Test @Order(4)
	void addCountryIntegrationTest() throws JSONException {
		
		Country country = new Country(4, "Italy", "Rome");
		String expectedValue = "{\r\n"
				+ "    \"id\": 4,\r\n"
				+ "    \"countryName\": \"Italy\",\r\n"
				+ "    \"countryCapital\": \"Rome\"\r\n"
				+ "}";
		
		TestRestTemplate testRestTemplate = new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Country> request = new HttpEntity<Country>(country, headers);
		ResponseEntity<String> response = testRestTemplate.postForEntity("http://localhost:8088/addCountry", request, String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		
//		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		System.out.println(response.getBody());
		JSONAssert.assertEquals(expectedValue, response.getBody(), false);
	}
	
	@Test @Order(5)
	void updateCountryIntegrationTest() throws JSONException {
		
		Country country = new Country(4, "Japan", "Tokyo");
		String expectedValue = "{\r\n"
				+ "    \"id\": 4,\r\n"
				+ "    \"countryName\": \"Japan\",\r\n"
				+ "    \"countryCapital\": \"Tokyo\"\r\n"
				+ "}";
		
		TestRestTemplate testRestTemplate = new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Country> request = new HttpEntity<Country>(country, headers);
		ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:8088/updateCountry", HttpMethod.PUT,request, String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		
		//assertEquals(HttpStatus.CREATED, response.getStatusCode());
		JSONAssert.assertEquals(expectedValue, response.getBody(), false);
	}
	
	@Test @Order(6)
	void deleteCountryIntegrationTest() throws JSONException {
		
		Country country = new Country(4, "Japan", "Tokyo");
		String expectedValue = "{\"message\":\"Deleted Country Successfully...\",\"id\":4}";
		
		TestRestTemplate testRestTemplate = new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Country> request = new HttpEntity<Country>(country, headers);
		ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:8088/deleteCountry/4", HttpMethod.DELETE,request, String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		
	//	assertEquals(HttpStatus.CREATED, response.getStatusCode());
		JSONAssert.assertEquals(expectedValue, response.getBody(), false);
	}
	
}
