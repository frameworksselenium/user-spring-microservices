package com.example.userspringmicroservices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.userspringmicroservices.beans.Country;
import com.example.userspringmicroservices.controllers.ConuntryController;
import com.example.userspringmicroservices.repositories.CountryRepository;
import com.example.userspringmicroservices.services.CountryService;

@SpringBootTest(classes=ControllerMockitoTest.class)
@TestMethodOrder(OrderAnnotation.class)
public class ControllerMockitoTest {

	@Mock
	CountryService countryService;
	
	@InjectMocks
	ConuntryController conuntryController;
	
	@Test 
	@Order(1)
	void getAllCountriesnTest() {
		
		
		List<Country> countries = new ArrayList<Country>();
		countries.add(new Country(1, "Srilanka", "Colombo"));
		countries.add(new Country(2, "UK", "London"));
		countries.add(new Country(3, "India", "Delhi"));
		
		when(countryService.getAllCountries()).thenReturn(countries);
		ResponseEntity<List<Country>> res = conuntryController.getAllCountries();
		
		assertEquals(HttpStatus.FOUND, res.getStatusCode());
		assertEquals(3, res.getBody().size());
		
	}
	
}
