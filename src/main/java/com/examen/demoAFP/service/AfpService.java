package com.examen.demoAFP.service;

import java.util.List;
import java.util.Optional;

import com.examen.demoAFP.document.Afp;

public interface AfpService {
	
	public List<Afp> getAll();
	
	public Optional<Afp> getbyName(String name);
	
	public Afp save(Afp afp);
	
	public void delete(String id);

}
