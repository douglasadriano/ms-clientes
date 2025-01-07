package com.example.clientes.controller;

import com.example.clientes.exception.ClienteNaoEncontradoException;
import com.example.clientes.model.Cliente;
import com.example.clientes.service.ClienteService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para expor as APIs do cliente.
 */
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    @Autowired
    private ClienteService clienteService;

    /**
     * Cria um novo cliente.
     *
     * @param cliente Dados do cliente a ser criado.
     * @return Cliente criado.
     */
    @PostMapping
    public ResponseEntity<Cliente> criarCliente(@Valid @RequestBody Cliente cliente) {
        logger.info("Requisição para criar cliente recebida: {}", cliente);
        Cliente novoCliente = clienteService.criarCliente(cliente);
        return ResponseEntity.ok(novoCliente);
    }

    /**
     * Altera um cliente existente.
     *
     * @param id      ID do cliente a ser alterado.
     * @param cliente Dados do cliente a ser alterado.
     * @return Cliente alterado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> alterarCliente(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
        logger.info("Requisição para alterar cliente com ID {} recebida", id);
        Cliente clienteAtualizado = clienteService.alterarCliente(id, cliente);
        return ResponseEntity.ok(clienteAtualizado);
    }

    /**
     * Exclui um cliente pelo ID.
     *
     * @param id ID do cliente a ser excluído.
     * @return Mensagem de confirmação.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirCliente(@PathVariable Long id) {
        logger.info("Requisição para excluir cliente com ID {} recebida", id);
        clienteService.excluirCliente(id);
        return ResponseEntity.ok("Cliente excluído com sucesso.");
    }

    /**
     * Lista todos os clientes.
     *
     * @return Lista de clientes.
     */
    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        logger.info("Requisição para listar todos os clientes recebida");
        List<Cliente> clientes = clienteService.listarClientes();

        if (clientes.isEmpty()) {
            logger.warn("Nenhum cliente encontrado na lista");
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(clientes);
    }

    /**
     * Busca um cliente pelo ID.
     *
     * @param id ID do cliente.
     * @return Cliente encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarClientePorId(@PathVariable Long id) {
        logger.info("Requisição para buscar cliente com ID {} recebida", id);
        return clienteService.buscarClientePorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente com ID " + id + " não encontrado."));
    }
}
