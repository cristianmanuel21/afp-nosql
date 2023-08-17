package com.examen.demoAFP.document;

import java.time.LocalDate;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection="clients")
@Data
public class Client {
	
	@Id
	private String id;
	
	@NotBlank(message="must not be empty")
	private String firstname;
	
	@NotBlank(message="must not be empty")
	@Size(min = 8,max=8, message="Size must be 8")
	private String dni;
	
	@NotBlank(message="must not be empty")
	@Size(min = 6,max=9, message="Size must be between 6 and 9")
	private String phone;
	
	@NotBlank(message="must not be empty")
	private String lastname;
	
	@NotNull(message="must not be null")
	private Afp afp;
	
	@NotBlank(message="must not be empty")
	@Email
	private String email;
	
	private String numberAccount;
	
	private Double availableAmount;
	
	private LocalDate  withdrawalDate;
	
	private boolean active;
	
	public Client(String firstname,String lastname,String dni,String phone,String email,Afp afp,String numberAccount,Double availableAmount,LocalDate withdrawalDate,boolean active) {
		this.firstname=firstname;
		this.lastname=lastname;
		this.dni=dni;
		this.phone=phone;
		this.email=email;
		this.afp=afp;
		this.numberAccount=numberAccount;
		this.availableAmount=availableAmount;
		this.withdrawalDate=withdrawalDate;
		this.active=active;
	}

}
