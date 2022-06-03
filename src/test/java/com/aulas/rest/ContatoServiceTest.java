package com.aulas.rest;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.aulas.rest.entity.Contato;
import com.aulas.rest.repository.ContatoRepository;
import com.aulas.rest.service.ContatoService;

@ExtendWith(SpringExtension.class)
public class ContatoServiceTest {
    private Long idExistente;
    private Long idNaoExistente;
    private Contato contato;
    
	@InjectMocks
	private ContatoService service;
	
	@Mock
	private ContatoRepository repository;
	
	@BeforeEach
	void setup() {
		idExistente = 1L;
		idNaoExistente = 1000L;
		contato = new Contato(1L,"Maria","maria@teste");
		/*Comportamento simulado usando mockito*/
		Mockito.doNothing().when(repository).deleteById(idExistente);
		
		Mockito.doThrow(EntityNotFoundException.class).when(repository).deleteById(idNaoExistente);
		
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(contato);
		
		Mockito.when(repository.findById(idExistente)).thenReturn(Optional.of(contato));
	}	
	
	@Test
	public void naoFazNadaDeleteQuandoIdExiste() {
		Assertions.assertDoesNotThrow(() -> {
			service.delete(idExistente);
		});	
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(idExistente);
	}
	
	@Test
	public void lancaEntityNotFoundExcpetionQuandoDeleteIdNaoExistente() {
		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			service.delete(idNaoExistente);
		});			
		Mockito.verify(repository, Mockito.times(1)).deleteById(idNaoExistente);
	}
	
	@Test
	public void retornaContatoAoSalvar() {
	    Assertions.assertNotNull(service.salvar(contato));
	    Mockito.verify(repository, Mockito.times(1)).save(contato);
	}
	
	@Test
	public void retornaContatoAoPesquisarPorIdExistente() {
	    Assertions.assertNotNull(service.pesquisar(idExistente));
	    Mockito.verify(repository, Mockito.times(1)).findById(idExistente);
	}
	
}
