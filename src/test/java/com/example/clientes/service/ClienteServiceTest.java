package com.example.clientes.service;

import com.example.clientes.exception.ClienteNaoEncontradoException;
import com.example.clientes.model.Cliente;
import com.example.clientes.repository.ClienteRepository;
import com.example.clientes.utils.FormatadorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        cliente = new Cliente("João", "joao@example.com", "1234567899");
    }

    @Test
    public void testCriarCliente() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente clienteCriado = clienteService.criarCliente(cliente);

        assertNotNull(clienteCriado);
        assertEquals("João", clienteCriado.getNome());
        assertEquals("joao@example.com", clienteCriado.getEmail());
        assertEquals("(12) 3456-7899", clienteCriado.getTelefone());
    }

    @Test
    public void testAlterarCliente() {
        Long id = 1L;
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente clienteAlterado = clienteService.alterarCliente(id, cliente);

        assertNotNull(clienteAlterado);
        assertEquals("João", clienteAlterado.getNome());
        assertEquals("joao@example.com", clienteAlterado.getEmail());
        assertEquals("(12) 3456-7899", clienteAlterado.getTelefone());
    }

    @Test
    public void testAlterarClienteNaoEncontrado() {
        Long id = 1L;
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> clienteService.alterarCliente(id, cliente));
    }

    @Test
    public void testExcluirCliente() {
        Long id = 1L;
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        clienteService.excluirCliente(id);

        verify(clienteRepository, times(1)).deleteById(id);
    }

    @Test
    public void testExcluirClienteNaoEncontrado() {
        Long id = 1L;
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> clienteService.excluirCliente(id));
    }

    @Test
    public void testListarClientes() {
        when(clienteRepository.findAll()).thenReturn(java.util.Collections.singletonList(cliente));

        assertEquals(1, clienteService.listarClientes().size());
    }

    @Test
    public void testBuscarClientePorId() {
        Long id = 1L;
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        Optional<Cliente> clienteEncontrado = clienteService.buscarClientePorId(id);

        assertTrue(clienteEncontrado.isPresent());
        assertEquals("João", clienteEncontrado.get().getNome());
    }
}
