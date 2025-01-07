package com.example.clientes.repository;

import com.example.clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório JPA para realizar operações no banco de dados.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
