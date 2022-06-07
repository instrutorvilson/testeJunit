package com.aulas.rest.integracao;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.aulas.rest.entity.Contato;
import com.aulas.rest.service.ContatoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ContatoTesteIntegracaoServiceController {
	
		private Long idExistente;
	    private Long idInexistente;
	    
	    private Contato contatoExistente;
	    private Contato contatoNovo;
	    
		@Autowired
		private MockMvc mockMvc;
		
		@Autowired
		private ContatoService service;
		
		@Autowired
		ObjectMapper objectMapper;
		
		@BeforeEach
		void setup() {
			idExistente= 1L;
			idInexistente = 2L;
			
			contatoNovo = new Contato();
			contatoExistente = new Contato(idExistente, "maria", "maria@gmail");		
		}

		@Test
	    void deveRetornarOkQuandoPesquisaIdExistenteIt() throws Exception {
		    ResultActions result =	mockMvc.perform(get("/contato/{idcontato}", idExistente)
			       .accept(MediaType.APPLICATION_JSON));
		    result.andExpect(status().isOk());
		}
		
		@Test
		void deveRetornarStatus404QuandoPesquisaIdInexistenteIt() throws Exception {
			ResultActions obj =	mockMvc.perform(get("/contato/{idcontato}", idInexistente)
				                           .accept(MediaType.APPLICATION_JSON));
			
			    obj.andExpect(status().isNotFound());		    
		}
		
		@Test
		void deveRetornarStatu201QuandoContatoSalvoComSucessoIt() throws Exception {
			contatoNovo.setNome("josefa");
			String jsonBody = objectMapper.writeValueAsString(contatoNovo);
			ResultActions result = mockMvc.perform(post("/contato")
				   .content(jsonBody)
				   .contentType(MediaType.APPLICATION_JSON)
				   .accept(MediaType.APPLICATION_JSON));
			
			result.andExpect(status().isCreated());				
		}
		
		@Test
		void deveRetornarStatus200QuandoAlterarContatoExistenteComSucessoIt() throws Exception {
			contatoNovo.setNome("Josefa");
			String jsonBody = objectMapper.writeValueAsString(contatoExistente);
			ResultActions result = mockMvc.perform(put("/contato/{idcontato}", idExistente)
				   .content(jsonBody)
				   .contentType(MediaType.APPLICATION_JSON)
				   .accept(MediaType.APPLICATION_JSON));
			
			result.andExpect(status().isOk());		       
		}
		
		@Test
		void deveRetornarStatus404QuandoAlterarContatoInexistenteIt() throws Exception {
			contatoNovo.setNome("Josefa");
			String jsonBody = objectMapper.writeValueAsString(contatoNovo);
			ResultActions result = mockMvc.perform(put("/contato/{idcontato}", idInexistente)
				   .content(jsonBody)
				   .contentType(MediaType.APPLICATION_JSON)
				   .accept(MediaType.APPLICATION_JSON));
			
			result.andExpect(status().isNotFound());		       
		}
		
		@Test
		void deveRetornarNadaQuandoExcluirContatoExistenteIt() throws Exception {
			 ResultActions result = mockMvc.perform(delete("/contato/{idcontato}", idExistente));
			
			result.andExpect(status().isNoContent());		       
		}		
	
}
