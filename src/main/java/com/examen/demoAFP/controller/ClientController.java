package com.examen.demoAFP.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.examen.demoAFP.document.Afp;
import com.examen.demoAFP.document.Client;
import com.examen.demoAFP.dto.ClientDto;
import com.examen.demoAFP.service.AfpService;
import com.examen.demoAFP.service.ClientService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
	
	final private ClientService clientService;
	final private AfpService afpService;
	
	@GetMapping("/find/actives/{afpName}")
	public ResponseEntity<?> getAllActives(@PathVariable("afpName") String afpName ){
		return ResponseEntity.ok(clientService.getAllActives(afpName));
	}
	
	@GetMapping
	public ResponseEntity<?> getAll(){
		return ResponseEntity.ok(clientService.getAll());
	}
	
	@GetMapping("/{dni}")
	public ResponseEntity<?> getByDni(@PathVariable("dni") String dni){
		Client client=this.clientByDni(dni);
		if(client!=null) return ResponseEntity.ok(client);
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> saveClient(@Valid @RequestBody Client client,BindingResult result){
		Map<String,Object> response= new HashMap<>();
		Client clientFinal=null;
		if(result.hasErrors()) {
			return validar(result,response);
		}
		
		if(this.getbyName(client.getAfp().getName())==null) {
			response.put("message", "No se encontro la afp en el sistema");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		
		}else if(this.clientByDni(client.getDni())!=null) {
			response.put("message", "El cliente ya se encuentra registrado en el sistema");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
		
		}else {
			
			try {
				if((client.getAvailableAmount()!=null && client.getAvailableAmount()>0.0) && client.getWithdrawalDate()!=null && client.getNumberAccount()!=null && !client.getNumberAccount().isEmpty() )client.setActive(true);
				client.setAfp(this.getbyName(client.getAfp().getName()));
				clientFinal=clientService.save(client);
			}
			catch(DataAccessException ex) {
				response.put("message", "Error al insertar en base de datos");
				response.put("error",ex.getMessage().concat(ex.getMostSpecificCause()+" "+ex.getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			response.put("message", "Client created successful");
			response.put("Client", clientFinal);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);

		}
	}
	
	@PutMapping("/{dni}")
	public ResponseEntity<?> editCustomer(@Valid @RequestBody ClientDto clientdto,BindingResult result, @PathVariable("dni") String dni){
		
		Map<String,Object> response= new HashMap<>();
		Client finalClient=null;
		if(result.hasErrors()) {
			return validar(result,response);
		}
		
		try {
			finalClient= this.clientByDni(dni);
			if(finalClient!=null) {
				if(clientdto.getNumberAccount()!=null && !clientdto.getNumberAccount().isEmpty())finalClient.setNumberAccount(clientdto.getNumberAccount());
				if(clientdto.getWithdrawalDate()!=null)finalClient.setWithdrawalDate(clientdto.getWithdrawalDate());
				if(clientdto.getAvailableAmount()!=null && clientdto.getAvailableAmount()>0.0)finalClient.setAvailableAmount(clientdto.getAvailableAmount());
				if((finalClient.getAvailableAmount()!=null && finalClient.getAvailableAmount()>0.0) && finalClient.getWithdrawalDate()!=null && finalClient.getNumberAccount()!=null && !finalClient.getNumberAccount().isEmpty() )finalClient.setActive(true);
				return ResponseEntity.status(HttpStatus.CREATED).body(clientService.save(finalClient));
			}
		}catch(DataAccessException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(Collections.singletonMap("message","Error en la comunicacion o en la insercion en la BD"));
		}
		
		return ResponseEntity.notFound().build();		
	}
	
	@DeleteMapping("/{dni}")
	public ResponseEntity<?> deleteCustomer(@PathVariable("dni") String dni){
		Client c=this.clientByDni(dni);
		if(c!=null) { clientService.deleteByDni(c.getId()); return  ResponseEntity.noContent().build();}   
		else{return  ResponseEntity.notFound().build();}
	}
	
		
	private Afp getbyName(String name){
		Optional<Afp> o=afpService.getbyName(name);
		return o.orElse(null);
	}
	
	
	private Client clientByDni(String dni) {
		Optional<Client> client=clientService.getByDni(dni);
		return client.orElse(null);
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
