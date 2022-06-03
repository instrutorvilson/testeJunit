package com.aulas.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aulas.rest.entity.Contato;

@RestController
@RequestMapping("/")
public class ContatoController {
	
	@GetMapping("/ola")
	public String ola() {
		return "Ola mundo";
	}
	
	@PostMapping
	public ResponseEntity<Contato> inserir(@RequestBody Contato contato){
		return ResponseEntity.status(HttpStatus.OK).body(contato);
	}
}
