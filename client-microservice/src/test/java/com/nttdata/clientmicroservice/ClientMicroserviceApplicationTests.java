package com.nttdata.clientmicroservice;

import com.nttdata.clientmicroservice.business.ClientServiceImpl;
import com.nttdata.clientmicroservice.entity.Client;
import com.nttdata.clientmicroservice.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class ClientMicroserviceApplicationTests {

	@InjectMocks
	private ClientServiceImpl clientService;

	@Mock
	private ClientRepository clientRepository;

	@Test
	@DisplayName("Test for find por Id from client")
	public void testFindClientById() {
		Client espero = new Client("2", "Ronald", "Roca", "40154899", "01");


		Mockito.when(clientService.findById(Mockito.anyString()))
				.thenReturn(Mono.just(new Client("2", "Ronald", "Roca", "40154899", "01")));

		Mono<Client> recibo = clientRepository.findById("2");

		Assertions.assertEquals(espero.getIdClient(),recibo.block().getIdClient());
		Assertions.assertEquals(espero.getName(),recibo.block().getName());
		Assertions.assertEquals(espero.getSurnames(),recibo.block().getSurnames());
		Assertions.assertEquals(espero.getDni(),recibo.block().getDni());
		Assertions.assertEquals(espero.getClientType(),recibo.block().getClientType());

		Mockito.verify(clientRepository, Mockito.times(1)).findById(Mockito.anyString());

	}

	@Test
	@DisplayName("Test for find all  clients")
	public void testClientAll() {
		ArrayList<Client> expected = new ArrayList<>();
		expected.add(new Client("1", "Ronald", "Roca", "40154899", "01"));
		expected.add(new Client("2", "Oscar", "Caldas", "23154899", "01"));

		Flux<Client> clientsFluxEsperado = Flux.fromIterable(expected);

		ArrayList  clients = new ArrayList<>();
		clients.add(new Client("1", "Ronald", "Roca", "40154899", "01"));
		clients.add(new Client("2", "Oscar", "Caldas", "23154899", "01"));

		Flux<Client> clientsFlux = Flux.fromIterable(clients);

		Mockito.when( clientRepository.findAll())
				.thenReturn(clientsFlux);

		Flux<Client> clientsActual = clientService.findAll();

		Assertions.assertEquals(clientsFluxEsperado.blockFirst() .getIdClient(),clientsActual.blockFirst().getIdClient());
		Assertions.assertEquals(clientsFluxEsperado.blockFirst() .getName(),clientsActual.blockFirst().getName());


		Mockito.verify(clientRepository, Mockito.times(1)).findAll();

	}


}
