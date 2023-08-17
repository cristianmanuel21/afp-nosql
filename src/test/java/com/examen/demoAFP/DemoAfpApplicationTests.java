package com.examen.demoAFP;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.examen.demoAFP.document.Afp;
import com.examen.demoAFP.document.Client;
import com.examen.demoAFP.dto.ClientDto;
import com.examen.demoAFP.service.AfpService;
import com.examen.demoAFP.service.ClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DemoAfpApplicationTests {
	
	@Autowired
	private WebTestClient client;
	
	@Autowired
	private AfpService afpService;
	
	@Autowired
	private ClientService clientService;
	
	ObjectMapper objectMapper;

	@BeforeEach
	    void setUp() {
		objectMapper= new ObjectMapper();
	}
	
	@Order(1)
	@Test
	void getAllAfps() {
		client.get().uri("/afp")
		.exchange()
		.expectStatus().isOk()
		.expectBodyList(Afp.class)
		.consumeWith(response->{
			List<Afp> list=response.getResponseBody();
			assertNotNull(list);
			assertEquals(4,list.size());
		});
	}
	
	
	
	@Order(2)
	@Test
	void saveNewAfp() {
		Afp afp=new Afp();afp.setName("AFP_TEST");
		client.post().uri("/afp")
		.accept(MediaType.APPLICATION_JSON)
		.contentType(MediaType.APPLICATION_JSON)
		.bodyValue(afp)
		.exchange()
		.expectStatus().isCreated()
		.expectBody(Afp.class)
		.consumeWith(response->{
			Afp a=response.getResponseBody();
			assertNotNull(a);
			assertEquals(a.getName(), "AFP_TEST");
		});
	}
	
	@Order(3)
	@Test
	void deleteAfp() {
		Afp afp=afpService.getbyName("AFP_TEST").get();
		client.get().uri("/afp/{name}",Collections.singletonMap("name", afp.getName()))
		.exchange()
		.expectStatus().isOk();
		
		client.delete().uri("/afp/{name}",afp.getName())
		.exchange()
		.expectStatus().isNoContent()
		.expectBody().isEmpty();
		
		client.get().uri("/afp/{name}",Collections.singletonMap("name", afp.getName()))
		.exchange()
		.expectStatus().isNotFound()
		.expectBody().isEmpty();
		
	}
	
	@Order(4)
	@Test
	void getClient() {
		Client clientAfp=clientService.getByDni("11223355").get();
		client.get().uri("/client/{dni}",clientAfp.getDni())
		.accept(MediaType.APPLICATION_JSON)
		.exchange()
		.expectStatus().isOk()
		.expectBody()
		.jsonPath("$.nombre","Susana");
		 jsonPath("$.availableAmount", 150000.0);
	}
	
	@Order(5)
	@Test
	void saveClient() throws JsonProcessingException{
		Afp afp=afpService.getbyName("INTEGRA").get();
		Client clientAfp=new Client("Gabriel", "Quispe", "77451236", "9915652", "gabo@gmail.com", afp, "a39511", 350000.0, LocalDate.now(), true);
		client.post().uri("/client")
		.accept(MediaType.APPLICATION_JSON)
		.contentType(MediaType.APPLICATION_JSON)
		.bodyValue(clientAfp)
		.exchange()
		//then
		.expectStatus().isCreated()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBody()
		.consumeWith(response->{
			try {
				JsonNode json = objectMapper.readTree(response.getResponseBody());
				assertEquals("Client created successful", json.path("message").asText());
				assertEquals("77451236", json.path("Client").path("dni").asText());
				assertEquals("Gabriel",json.path("Client").path("firstname").asText());
				assertEquals(350000.0,json.path("Client").path("availableAmount").asDouble());
			}catch (IOException e) {
                e.printStackTrace();
			}
			
		}).jsonPath("$.message").isEqualTo("Client created successful");
		
	}
	
	@Order(6)
	@Test
	void updateClient() {
		ClientDto clientDto=new ClientDto();
		clientDto.setNumberAccount("a39518");
		clientDto.setAvailableAmount(475000.0);
		Client clientAfp=clientService.getByDni("77451236").get();
		client.put().uri("/client/{dni}",clientAfp.getDni())
		.accept(MediaType.APPLICATION_JSON)
		.contentType(MediaType.APPLICATION_JSON)
		.bodyValue(clientDto)
		.exchange()
		//then
		.expectStatus().isCreated()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBody(Client.class)
		.consumeWith(response->{
			Client clientw=response.getResponseBody();
			assertNotNull(clientw);
			assertEquals(clientw.getDni(),clientAfp.getDni());
			assertEquals(clientw.getAvailableAmount(),475000.0);
			assertEquals(clientw.getNumberAccount(), "a39518");
		});
	}
	
	@Order(7)
	@Test
	void deleteClient() {
		
		Client clientAfp=clientService.getByDni("77451236").get();
		client.delete().uri("/client/{dni}",Collections.singletonMap("dni",clientAfp.getDni()))
		.exchange()
		.expectStatus().isNoContent()
		.expectBody().isEmpty();
		
		client.get().uri("/client/{dni}", Collections.singletonMap("dni",clientAfp.getDni()))
		.exchange()
		.expectStatus().isNotFound()
		.expectBody().isEmpty();
	}
	
}
