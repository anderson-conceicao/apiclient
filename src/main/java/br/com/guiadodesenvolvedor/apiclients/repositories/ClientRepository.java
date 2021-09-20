package br.com.guiadodesenvolvedor.apiclients.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.guiadodesenvolvedor.apiclients.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
