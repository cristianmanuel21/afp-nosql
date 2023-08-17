package com.examen.demoAFP.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examen.demoAFP.document.Client;
import com.examen.demoAFP.repository.ClientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService{
	
	final private ClientRepository clientRepository;
	
	@Override
	public List<Client> getAll() {
		return clientRepository.findAll();
	}

	@Override
	public Optional<Client> getByDni(String dni) {
		return clientRepository.findByDni(dni);
	}

	@Override
	public Client save(Client client) {
		return clientRepository.save(client);
	}

	@Override
	public Client update(Client client, String dni) {
		return null;
	}

	@Override
	public void deleteByDni(String dni) {
		clientRepository.deleteById(dni);
		
	}

	@Override
	public List<Client> getAllActives(String afpName) {
		return clientRepository.getAllAcives(afpName.toUpperCase());
	}

}
