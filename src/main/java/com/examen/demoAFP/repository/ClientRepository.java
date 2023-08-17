package com.examen.demoAFP.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.examen.demoAFP.document.Client;

public interface ClientRepository extends MongoRepository<Client, String>{
	
	public Optional<Client> findByDni(String dni);
	
	@Query(value = "{'active': true, 'afp.name': ?0 }", fields = "{'dni': 1, 'numberAccount': 1, 'availableAmount': 1, 'withdrawalDate': 1, 'afp.id':1 'afp.name':1, 'active':1 }")
    public List<Client> getAllAcives(String afpName);
	

}
