package com.examen.demoAFP;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.examen.demoAFP.document.Afp;
import com.examen.demoAFP.document.Client;
import com.examen.demoAFP.service.AfpService;
import com.examen.demoAFP.service.ClientService;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class DemoAfpApplication implements CommandLineRunner{
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private AfpService afpService;
	
	@Autowired
	private ClientService clientService;

	public static void main(String[] args) {
		SpringApplication.run(DemoAfpApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mongoTemplate.dropCollection("afps");
		mongoTemplate.dropCollection("clients");
		
		Afp afp1=new Afp();
		afp1.setName("INTEGRA");
		afpService.save(afp1);
		
		
		Afp afp2=new Afp();
		afp2.setName("PRIMA");
		afpService.save(afp2);
		
		Afp afp3=new Afp();
		afp3.setName("HABITAT");
		afpService.save(afp3);
		
		Afp afp4=new Afp();
		afp4.setName("PROFUTURO");
		afpService.save(afp4);
		
		//public Client(firstname, lastname, dni, phone, email,Afp afp, numberAccount, availableAmount, withdrawalDate,boolean active) {
		Client client1=new Client("Paul","Diaz","44556677","666333","paul@gmail.com",afp1,"a1234",50000.0,LocalDate.now(),true);
		clientService.save(client1);
		Client client2=new Client("Diego","Perez","44556600","666111","diegp@gmail.com",afp2,"a1235",75000.0,LocalDate.now(),true);
		clientService.save(client2);
		Client client3=new Client("Susana","Velez","11223355","666000","susana@gmail.com",afp1,"a1236",150000.0,LocalDate.now(),true);
		clientService.save(client3);
		Client client4=new Client("Manuel","Quispe","11336655","666222","manuel@gmail.com",afp3,"b1234",250000.0,LocalDate.now(),true);
		clientService.save(client4);
		Client client5=new Client("Lorena","Vilca","11002233","666444","lorena@gmail.com",afp2,"b1566",350000.0,LocalDate.now(),true);
		clientService.save(client5);
		Client client6=new Client("Alan","Carpio","44665522","7544333","alan@gmail.com",afp2,"c1253",18500.0,LocalDate.now(),true);
		clientService.save(client6);
		Client client7=new Client("Ayleen","Salas","44556678","5104333","ayleen@gmail.com",afp4,null,0.0,null,false);
		clientService.save(client7);
		
	}

}
