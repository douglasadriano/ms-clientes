package com.example.clientes.service;

import com.example.clientes.exception.ClienteNaoEncontradoException;
import com.example.clientes.model.Cliente;
import com.example.clientes.repository.ClienteRepository;
import com.example.clientes.utils.FormatadorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Serviço de Cliente, responsável pela lógica de negócio.
 */
@Service
public class ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Cria um novo cliente no banco de dados.
     * @param cliente Cliente a ser criado.
     * @return Cliente criado.
     */
    @Transactional
    public Cliente criarCliente(Cliente cliente) {
        // Formatar e-mail e telefone antes de salvar
        cliente.setEmail(FormatadorUtils.formatarEmail(cliente.getEmail()));
        cliente.setTelefone(FormatadorUtils.formatarTelefone(cliente.getTelefone()));

        try {
            Cliente clienteCriado = clienteRepository.save(cliente);
            logger.info("Cliente criado com sucesso: {}", clienteCriado);
            return clienteCriado;
        } catch (Exception e) {
            logger.error("Erro ao criar cliente: {}", e.getMessage());
            throw new RuntimeException("Erro ao criar cliente: " + e.getMessage());
        }
    }

    /**
     * Altera os dados de um cliente existente.
     *
     * @param id      ID do cliente a ser alterado.
     * @param cliente Cliente com dados atualizados.
     * @return Cliente atualizado.
     */
    @Transactional
    public Cliente alterarCliente(Long id, Cliente cliente) {
        Optional<Cliente> clienteExistente = clienteRepository.findById(id);

        if (clienteExistente.isPresent()) {
            Cliente clienteAtualizado = clienteExistente.get();
            // Formatar e-mail e telefone antes de atualizar
            clienteAtualizado.setEmail(FormatadorUtils.formatarEmail(cliente.getEmail()));
            clienteAtualizado.setTelefone(FormatadorUtils.formatarTelefone(cliente.getTelefone()));

            try {
                Cliente clienteAlterado = clienteRepository.save(clienteAtualizado);
                logger.info("Cliente com ID {} alterado com sucesso", id);
                return clienteAlterado;
            } catch (Exception e) {
                logger.error("Erro ao alterar cliente com ID {}: {}", id, e.getMessage());
                throw new RuntimeException("Erro ao alterar cliente: " + e.getMessage());
            }
        } else {
            logger.error("Cliente com ID {} não encontrado para alteração", id);
            throw new ClienteNaoEncontradoException("Cliente com ID " + id + " não encontrado.");
        }
    }

    /**
     * Exclui um cliente do banco de dados.
     * @param id ID do cliente a ser excluído.
     */
    @Transactional
    public void excluirCliente(Long id) {
        Optional<Cliente> clienteExistente = clienteRepository.findById(id);

        if (clienteExistente.isPresent()) {
            try {
                clienteRepository.deleteById(id);
                logger.info("Cliente com ID {} excluído com sucesso", id);
            } catch (Exception e) {
                logger.error("Erro ao excluir cliente com ID {}: {}", id, e.getMessage());
                throw new RuntimeException("Erro ao excluir cliente: " + e.getMessage());
            }
        } else {
            logger.error("Cliente com ID {} não encontrado para exclusão", id);
            throw new ClienteNaoEncontradoException("Cliente com ID " + id + " não encontrado.");
        }
    }

    /**
     * Busca todos os clientes no banco de dados.
     * @return Lista de clientes.
     */
    public List<Cliente> listarClientes() {
        try {
            List<Cliente> clientes = clienteRepository.findAll();
            logger.info("Clientes listados com sucesso, total: {}", clientes.size());
            return clientes;
        } catch (Exception e) {
            logger.error("Erro ao listar clientes: {}", e.getMessage());
            throw new RuntimeException("Erro ao listar clientes: " + e.getMessage());
        }
    }

    /**
     * Busca um cliente por seu ID.
     * @param id ID do cliente.
     * @return Cliente encontrado, ou Optional.empty() se não encontrado.
     */
    public Optional<Cliente> buscarClientePorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);

        if (cliente.isPresent()) {
            logger.info("Cliente com ID {} encontrado", id);
        } else {
            logger.warn("Cliente com ID {} não encontrado", id);
        }

        return cliente;
    }

}
