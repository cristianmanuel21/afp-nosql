package com.examen.demoAFP.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examen.demoAFP.document.Afp;
import com.examen.demoAFP.repository.AfpRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AfpServiceImpl implements AfpService{
	
	final private AfpRepository afpRepository;

	@Override
	public List<Afp> getAll() {
		return afpRepository.findAll();
	}


	@Override
	public Afp save(Afp afp) {
		return afpRepository.save(afp);
	}


	@Override
	public Optional<Afp> getbyName(String name) {
		return afpRepository.findByName(name);
	}


	@Override
	public void delete(String id) {
		afpRepository.deleteById(id);
		
	}

}
