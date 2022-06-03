package com.aulas.rest;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.aulas.rest.entity.Contato;
import com.aulas.rest.repository.ContatoRepository;
import com.aulas.rest.service.ContatoService;

@SpringBootTest
class TesteMockitoApplicationTests {

	@Test
	void contextLoads() {
	}
	private Long idExistente;
    private Long idNaoExistente;
    private Contato contato;
    
	@Autowired
	private ContatoService service;
	
	@Autowired
	private ContatoRepository repository;
	
	@BeforeEach
	void setup() {
		idExistente = 1L;
		idNaoExistente = 1000L;
		contato = new Contato(1L,"Maria","maria@teste");
		
	}	
	
	@Test
	public void naoFazNadaDeleteQuandoIdExistez() {
		Assertions.assertDoesNotThrow(() -> {
			service.delete(idExistente);
		});	
		
	}
	
	@Test
	public void lancaEntityNotFoundExcpetionQuandoDeleteIdNaoExistentez() {
		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			service.delete(idNaoExistente);
		});			
		
	}
	
	@Test
	public void retornaContatoAoSalvarz() {
	    Assertions.assertNotNull(service.salvar(contato));
	   
	}
	
	@Test
	public void retornaContatoAoPesquisarPorIdExistentez() {
	    Assertions.assertNotNull(service.pesquisar(idExistente));
	   
	}
	
}
