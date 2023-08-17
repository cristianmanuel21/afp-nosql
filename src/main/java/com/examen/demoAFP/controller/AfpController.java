package com.examen.demoAFP.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examen.demoAFP.document.Afp;
import com.examen.demoAFP.service.AfpService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/afp")
@RequiredArgsConstructor
public class AfpController {
	
	private final AfpService afpService;
	
	@GetMapping()
	public ResponseEntity<?> getAll(){
		return ResponseEntity.ok(afpService.getAll());
	}
	
	@GetMapping("/{name}")
	public ResponseEntity<?> getAfpByName(@PathVariable String name){
		Afp afp=this.getbyName(name);
		if(afp==null) return ResponseEntity.notFound().build();
		return ResponseEntity.ok(afp);	
	}
	
	@PostMapping
	public ResponseEntity<?> saveAfp(@Valid @RequestBody  Afp afp, BindingResult result){
		Map<String,Object> response= new HashMap<>();
		if(result.hasErrors()) {
			return validar(result,response);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(afpService.save(afp));
	}
	
	@DeleteMapping("/{name}")
	public ResponseEntity<?> deleteAfp(@PathVariable String name){
		Afp afp=this.getbyName(name);
		if(afp!=null) { afpService.delete(afp.getId()); return  ResponseEntity.noContent().build();}   
		else{return  ResponseEntity.notFound().build();}
	}
	
	private Afp getbyName(String name){
		Optional<Afp> o=afpService.getbyName(name.toUpperCase());
		return o.orElse(null);
	}
	
	private ResponseEntity<?> validar(BindingResult result, Map<String,Object> response) {
		if(result.hasErrors()) {
			result.getFieldErrors().stream()
			.map(k->{
				return response.put(k.getField(), "El campo "+k.getField()+" "+k.getDefaultMessage());
			}).collect(Collectors.toList());
		}
		return ResponseEntity.badRequest().body(response);
	}
	
}
