package com.examen.demoAFP.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ClientDto {
	
	private String numberAccount;
	
	private Double availableAmount;
	
	private LocalDate  withdrawalDate;

}
