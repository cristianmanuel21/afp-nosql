package com.examen.demoAFP.service;

import java.util.List;
import java.util.Optional;

import com.examen.demoAFP.document.Client;

public interface ClientService {
	
	public List<Client> getAll();
	
	public List<Client> getAllActives(String afpName);
	
	public Optional<Client> getByDni(String dni);
	
	public Client save(Client client);
	
	public Client update(Client client,String dni);
	
	public void deleteByDni(String dni);

}
