package com.aulas.rest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.aulas.rest.entity.Contato;
import com.aulas.rest.service.ContatoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ContatoControllerTest {
	private Long idExistente;
	private Long idNaoExistente;
	private List<Contato> lista;
	private Contato contatoNovo;
	private Contato contatoExistente;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ContatoService service;
	
	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setup() throws Exception {
		idExistente = 1L;
		idNaoExistente = 2L;
		contatoNovo = new Contato();
		contatoExistente = new Contato(1L, "maria", "maria@gmail");
		lista = new ArrayList<>();

		Mockito.when(service.pesquisar(idExistente)).thenReturn(contatoExistente);
		Mockito.when(service.pesquisar(idNaoExistente)).thenThrow(EntityNotFoundException.class);
		Mockito.when(service.pesquisarTodos()).thenReturn(lista);
		Mockito.when(service.salvar(any())).thenReturn(contatoExistente);
		Mockito.when(service.alterar(eq(idExistente), any())).thenReturn(contatoExistente);
		Mockito.when(service.alterar(eq(idNaoExistente), any())).thenThrow(EntityNotFoundException.class);
		Mockito.doNothing().when(service).delete(idExistente);
		Mockito.doThrow(EntityNotFoundException.class).when(service).delete(idNaoExistente);
	}

	@Test
	void deveRetornarStatusOKQuandoIdExistente() throws Exception {
		ResultActions result = mockMvc
				.perform(get("/contato/{idcontato}", idExistente).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk());
	}

	// @Test
	void deveRetornarStatus404QuandoIdNaoExistente() throws Exception {
		ResultActions result = mockMvc
				.perform(get("/contato/{idcontato}", idNaoExistente).accept(MediaType.APPLICATION_JSON));
		result.andExpect(res -> assertTrue(res.getResolvedException() instanceof EntityNotFoundException));

	}

	@Test
	void deveRetornarOkQuandoConsultarTodos() throws Exception {
		ResultActions result = mockMvc.perform(get("/contato").accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk());
	}

	@Test
	void deveRetornar201QuandoContatoSalvoComSucesso() throws Exception {
	    String jsonBody = objectMapper.writeValueAsString(contatoNovo);
	    
		ResultActions result = mockMvc.perform(post("/contato")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
		        .accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isCreated());
	}
	
	@Test
	void deveRetornarStatus200QuandoAlterarContatoExistenteComSucesso() throws Exception {
		String jsonBody = objectMapper.writeValueAsString(contatoExistente);
		ResultActions result = mockMvc.perform(put("/contato/{idcontato}", idExistente)
			   .content(jsonBody)
			   .contentType(MediaType.APPLICATION_JSON)
			   .accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());		       
	}
	
	@Test
	void deveRetornarStatus404QuandoAlterarContatoInexistente() throws Exception {
		String jsonBody = objectMapper.writeValueAsString(contatoNovo);
		ResultActions result = mockMvc.perform(put("/contato/{idcontato}", idNaoExistente)
			   .content(jsonBody)
			   .contentType(MediaType.APPLICATION_JSON)
			   .accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());		       
	}

	@Test
	void deveRetornarNadaQuandoExcluirContatoExistente() throws Exception {
		 ResultActions result = mockMvc.perform(delete("/contato/{idcontato}", idExistente)
			 			               .accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNoContent());		       
	}
}
