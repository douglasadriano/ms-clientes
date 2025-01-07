package com.example.clientes.service;

import com.example.clientes.model.Cliente;
import com.example.clientes.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Optional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    // Injeção automática do mock no serviço
    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        // Inicializa os mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriarCliente() {
        // Cria um cliente para o teste
        Cliente cliente = new Cliente("João", "joao@example.com", "123456789");

        // Simula a resposta do repositório
        Mockito.when(clienteRepository.save(Mockito.any(Cliente.class))).thenReturn(cliente);

        // Chama o método do serviço
        Cliente createdCliente = clienteService.criarCliente(cliente);

        // Verifica o resultado
        assertNotNull(createdCliente);
        assertEquals("João", createdCliente.getNome());
        assertEquals("joao@example.com", createdCliente.getEmail());
        assertEquals("123456789", createdCliente.getTelefone());
    }

    @Test
    void testAlterarCliente() {
        // Cliente existente para alteração
        Cliente cliente = new Cliente("Carlos", "carlos@example.com", "456789123");
        cliente.setId(1L);

        // Cliente alterado
        Cliente updatedCliente = new Cliente("Carlos Silva", "carlos.silva@example.com", "456789124");

        // Simula o comportamento do repositório
        Mockito.when(clienteRepository.save(Mockito.any(Cliente.class))).thenReturn(updatedCliente);

        // Chama o método do serviço
        Cliente result = clienteService.alterarCliente(id, updatedCliente);

        // Verifica o resultado
        assertNotNull(result);
        assertEquals("Carlos Silva", result.getNome());
        assertEquals("456789124", result.getTelefone());
    }

    @Test
    void testExcluirCliente() {
        Long clienteId = 1L;

        // Simula a exclusão
        Mockito.doNothing().when(clienteRepository).deleteById(clienteId);

        // Chama o método de exclusão
        clienteService.excluirCliente(clienteId);

        // Verifica se o repositório de fato tentou excluir o cliente
        Mockito.verify(clienteRepository, Mockito.times(1)).deleteById(clienteId);
    }

    @Test
    void testListarClientes() {
        // Lista de clientes simulada
        Cliente cliente1 = new Cliente("João", "joao@example.com", "123456789");
        Cliente cliente2 = new Cliente("Maria", "maria@example.com", "987654321");

        // Simula a resposta do repositório
        Mockito.when(clienteRepository.findAll()).thenReturn(List.of(cliente1, cliente2));

        // Chama o método do serviço
        List<Cliente> clientes = clienteService.listarClientes();

        // Verifica o resultado
        assertNotNull(clientes);
        assertEquals(2, clientes.size());
    }

    @Test
    void testBuscarClientePorId() {
        Long clienteId = 1L;

        // Cliente simulado
        Cliente cliente = new Cliente("Maria", "maria@example.com", "987654321");
        cliente.setId(clienteId);

        // Simula a resposta do repositório
        Mockito.when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));

        // Chama o método do serviço
        Optional<Cliente> foundCliente = clienteService.buscarClientePorId(clienteId);

        // Verifica o resultado
        assertTrue(foundCliente.isPresent());
        assertEquals("Maria", foundCliente.get().getNome());
    }
}
