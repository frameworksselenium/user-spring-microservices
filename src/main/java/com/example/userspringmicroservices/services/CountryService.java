package com.example.userspringmicroservices.services;

import com.example.userspringmicroservices.beans.Country;
import com.example.userspringmicroservices.controllers.AddResponse;
import com.example.userspringmicroservices.repositories.CountryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@Service
public class CountryService {

	@Autowired
	private CountryRepository countryRepository;
	

    public List<Country> getAllCountries(){
        
        return countryRepository.findAll();
    }

    public Country getCountryByID(int id){
    	
    	List<Country> countries = countryRepository.findAll();
    	Country country = null;
    	for(Country con:countries) {
    		if(con.getId() == id) {
    			country = con;
    		}
    	}
        return country;
    }

    public Country getCountryByName(String countryName){
        
    	List<Country> countries = countryRepository.findAll();
    	Country country = null;
    	for(Country con:countries) {
    		if(con.getCountryName().equalsIgnoreCase(countryName)) {
    			country = con;
    		}
    	}
        return country;
    }

    public Country addCountry(Country country){
        country.setId(getMaxCountryID());
        countryRepository.save(country);
        return country;
    }

    public int getMaxCountryID(){
    	return countryRepository.findAll().size()+1;
    }

    public Country updateCountry(Country country){
    	 countryRepository.save(country);
        return country;
    }

    public AddResponse deleteCountry(int id){
    	countryRepository.deleteById(id);
        AddResponse addResponse = new AddResponse();
        addResponse.setId(id);
        addResponse.setMessage("Deleted Country Successfully...");
        return addResponse;
    }
}
