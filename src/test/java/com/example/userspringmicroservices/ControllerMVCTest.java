package com.example.userspringmicroservices;

import org.junit.jupiter.api.TestMethodOrder;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.example.userspringmicroservices.beans.Country;
import com.example.userspringmicroservices.controllers.ConuntryController;
import com.example.userspringmicroservices.services.CountryService;
import com.fasterxml.jackson.databind.ObjectMapper;


@TestMethodOrder(OrderAnnotation.class)
@ComponentScan(basePackages = "com.example.userspringmicroservices")
@SpringBootTest(classes = {ControllerMVCTest.class})
@ContextConfiguration
@AutoConfigureMockMvc
public class ControllerMVCTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Mock
	CountryService countryService;
	
	@InjectMocks
	ConuntryController conuntryController;

	List<Country> countries;

	Country country;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(conuntryController).build();
		
	}
	
	@Test
	@Order(1)
	public void getAllCountriesTest() throws Exception {

		countries = new ArrayList<Country>();
		countries.add(new Country(1, "Srilanka", "Colombo"));
		countries.add(new Country(2, "UK", "London"));
		countries.add(new Country(3, "India", "Delhi"));
		
		when(countryService.getAllCountries()).thenReturn(countries);
		this.mockMvc.perform(get("/getCountries"))
				.andExpect(status().isFound())
				.andDo(print());

	}

		@Test
	@Order(2)
	public void getCountryById() throws Exception {

		country = new Country(1, "India", "Delhi");
		int countryId = 1;
		when(countryService.getCountryByID(countryId)).thenReturn(country);
		this.mockMvc.perform(get("/getCountries/{id}", countryId))
				.andExpect(status().isFound())
				.andExpect(MockMvcResultMatchers.jsonPath(".id").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("India"))
				.andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Delhi"))
				.andDo(print());

	}
	
	@Test
	@Order(3)
	public void getCountryByName() throws Exception {

		country = new Country(1, "China", "Beging");
		String countryName = "China";
		when(countryService.getCountryByName(countryName)).thenReturn(country);
		
		this.mockMvc.perform(get("/getCountries/countryname").param("name","China"))
				.andExpect(status().isFound())
				.andExpect(MockMvcResultMatchers.jsonPath(".id").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("China"))
				.andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Beging"))
				.andDo(print());

	}
	

	@Test
	@Order(4)
	public void addCountryTest() throws Exception {

		country = new Country(5, "UK", "London");
		when(countryService.addCountry(country)).thenReturn(country);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonbody = mapper.writeValueAsString(country);
		
		this.mockMvc.perform(post("/addCountry")
				.content(jsonbody)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andDo(print());

	}
	
	
}
