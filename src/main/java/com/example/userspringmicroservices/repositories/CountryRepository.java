package com.example.userspringmicroservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.userspringmicroservices.beans.Country;

public interface CountryRepository extends JpaRepository<Country, Integer>{
	//also called as data access object DAO

}
