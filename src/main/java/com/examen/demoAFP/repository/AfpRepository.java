package com.examen.demoAFP.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.examen.demoAFP.document.Afp;

public interface AfpRepository extends MongoRepository<Afp, String>{
	
	public Optional<Afp> findByName(String name);
	

}
