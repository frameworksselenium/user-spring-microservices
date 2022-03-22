package com.example.userspringmicroservices.controllers;

import com.example.userspringmicroservices.beans.Country;
import com.example.userspringmicroservices.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ConuntryController {

    @Autowired
    private CountryService countryService;


    @GetMapping("/getCountries")
    public ResponseEntity<List<Country>> getAllCountries(){
       
        try {
    		List<Country> country = countryService.getAllCountries();
    		return new ResponseEntity<List<Country>>(country, HttpStatus.FOUND);
    	}catch(Exception e) {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
        
    }

    @GetMapping(path="/getCountries/{id}")
    public ResponseEntity<Country> getCountryByID(@PathVariable(value="id") int id){
    	try {
    		Country country = countryService.getCountryByID(id);
    		return new ResponseEntity<Country>(country, HttpStatus.FOUND);
    	}catch(Exception e) {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    }

    @GetMapping(path="/getCountries/countryname")
    public ResponseEntity<Country> getCountryByName(@RequestParam(value="name") String countryName){
    	try {
    		Country country = countryService.getCountryByName(countryName);
    		return new ResponseEntity<Country>(country, HttpStatus.FOUND);
    	}catch(Exception e) {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    }

    @PostMapping("/addCountry")
    public ResponseEntity<Country> addNewCountry(@RequestBody Country country){
        try {
    		country = countryService.addCountry(country);
    		return new ResponseEntity<Country>(country, HttpStatus.CREATED);
    	}catch(Exception e) {
    		return new ResponseEntity<>(HttpStatus.CONFLICT);
    	}
        
    }

    @PutMapping("/updateCountry")
    public ResponseEntity<Country> updateCountry(@RequestBody Country country){
    	try {
    		Country updatedCountry = countryService.updateCountry(country);
    		return new ResponseEntity<Country>(updatedCountry, HttpStatus.OK);
    	}catch(Exception e) {
    		return new ResponseEntity<>(HttpStatus.CONFLICT);
    	}
    }

    @DeleteMapping("/deleteCountry/{id}")
    public AddResponse deleteUser(@PathVariable(value="id") int id){
    	
        return countryService.deleteCountry(id);
    }
}
