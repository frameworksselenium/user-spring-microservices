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

import com.example.userspringmicroservices.beans.Country;
import com.example.userspringmicroservices.controllers.AddResponse;
import com.example.userspringmicroservices.repositories.CountryRepository;
import com.example.userspringmicroservices.services.CountryService;

@SpringBootTest(classes=ServiceMockitoTest.class)
@TestMethodOrder(OrderAnnotation.class)
public class ServiceMockitoTest {

	@Mock
	CountryRepository countryRepository;
	
	@InjectMocks
	CountryService countryService;
	
	@Test 
	@Order(1)
	void getAllCountriesnTest() {
		
		
		List<Country> countries = new ArrayList<Country>();
		countries.add(new Country(1, "Srilanka", "Colombo"));
		countries.add(new Country(2, "UK", "London"));
		countries.add(new Country(3, "India", "Delhi"));
		
		when(countryRepository.findAll()).thenReturn(countries);
		int size = countryService.getAllCountries().size();
		System.out.println(size);
		assertEquals(3, size);
		
	}
	
	@Test 
	@Order(2)
	void getCountryByIDTest() {
		
		
		List<Country> countries = new ArrayList<Country>();
		countries.add(new Country(1, "Srilanka", "Colombo"));
		countries.add(new Country(2, "UK", "London"));
		countries.add(new Country(3, "India", "Delhi"));
		
		int countryByID = 1;
		
		when(countryRepository.findAll()).thenReturn(countries);
		int id = countryService.getCountryByID(countryByID).getId();
		System.out.println(id);
		assertEquals(countryByID, id);
		
	}
	
	@Test 
	@Order(3)
	void getCountryByNameTest() {
		
		
		List<Country> countries = new ArrayList<Country>();
		countries.add(new Country(1, "Srilanka", "Colombo"));
		countries.add(new Country(2, "UK", "London"));
		countries.add(new Country(3, "India", "Delhi"));
		
		String countryByName = "India";
		
		when(countryRepository.findAll()).thenReturn(countries);
		String countryName = countryService.getCountryByName(countryByName).getCountryName();
		System.out.println(countryName);
		assertEquals(countryByName, countryName);
		
	}
	
	@Test 
	@Order(4)
	void addCountryTest() {
		
		Country country = new Country(1, "Germany", "Berlin");
		when(countryRepository.save(country)).thenReturn(country);
		System.out.println(countryService.addCountry(country).getCountryCapital());
		assertEquals(country, countryService.addCountry(country));
		
	}
	
	@Test 
	@Order(5)
	void updateCountryTest() {
		
		Country country = new Country(1, "Germany", "Berlin");
		when(countryRepository.save(country)).thenReturn(country);
		System.out.println(countryService.updateCountry(country).getCountryCapital());
		assertEquals(country, countryService.updateCountry(country));
		
	}
	
	@Test 
	@Order(6)
	void deleteCountryTest() {
		
	
	}
	
}
