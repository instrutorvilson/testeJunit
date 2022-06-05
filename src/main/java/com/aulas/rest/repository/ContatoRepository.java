package com.aulas.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aulas.rest.entity.Contato;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long>{

}
