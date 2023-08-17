package com.examen.demoAFP.document;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Document(collection="afps")
@RequiredArgsConstructor
@Data
public class Afp {
	
	@Id
	private String id;
	
	@NotBlank(message="must not be empty")
	private String name;

}
