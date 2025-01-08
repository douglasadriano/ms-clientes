package com.example.clientes.controller;

import com.example.clientes.model.Cliente;
import com.example.clientes.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Arrays;
import java.util.Optional;

public class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
        objectMapper = new ObjectMapper();
    }

    // Teste do método POST
    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testCriarCliente() throws Exception {
        Cliente cliente = new Cliente("João", "joao@exemplo.com", "1234567891");
        when(clienteService.criarCliente(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andDo(print())  // Adiciona a impressão da resposta no console para depuração
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.email").value("joao@exemplo.com"))
                .andExpect(jsonPath("$.telefone").value("1234567891"));

        verify(clienteService, times(1)).criarCliente(any(Cliente.class));
    }

    // Teste do método GET (todos os clientes)
    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testListarClientes_ComClientes() throws Exception {
        Cliente cliente1 = new Cliente("João", "joao@exemplo.com", "123456789");
        Cliente cliente2 = new Cliente("Maria", "maria@exemplo.com", "987654321");
        when(clienteService.listarClientes()).thenReturn(Arrays.asList(cliente1, cliente2));

        mockMvc.perform(get("/api/clientes"))
                .andDo(print())  // Adiciona a impressão da resposta no console para depuração
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("João"))
                .andExpect(jsonPath("$[1].nome").value("Maria"));

        verify(clienteService, times(1)).listarClientes();
    }

    // Teste do método GET (todos os clientes - sem clientes)
    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testExcluirCliente() throws Exception {
        // Cria o cliente antes de excluir
        Cliente cliente = new Cliente("João", "joao@exemplo.com", "123456789");
        when(clienteService.criarCliente(any(Cliente.class))).thenReturn(cliente);  // Simula criação do cliente

        // Criação do cliente via POST
        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andDo(print())  // Mostra o resultado da criação no console
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.email").value("joao@exemplo.com"))
                .andExpect(jsonPath("$.telefone").value("123456789"));

        // Exclui o cliente via DELETE
        mockMvc.perform(delete("/api/clientes/1"))
                .andDo(print())  // Mostra o resultado da exclusão no console
                .andExpect(status().isOk())
                .andExpect(content().string("Cliente excluído com sucesso."));

        // Verifica se o cliente foi criado e excluído corretamente
        verify(clienteService, times(1)).criarCliente(any(Cliente.class));
        verify(clienteService, times(1)).excluirCliente(1L);  // Verifica se o método excluirCliente foi chamado uma vez
    }

    // Teste do método GET (cliente por ID)
    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testBuscarClientePorId() throws Exception {
        // Criação do cliente simulado
        Cliente cliente = new Cliente("João", "joao@exemplo.com", "123456789");

        // Simula a busca de cliente por ID
        when(clienteService.buscarClientePorId(1L)).thenReturn(Optional.of(cliente));

        // Realizando a requisição GET e verificando a resposta
        mockMvc.perform(get("/api/clientes/1"))
                .andDo(print())  // Isso vai imprimir o corpo da resposta no console
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.email").value("joao@exemplo.com"))
                .andExpect(jsonPath("$.telefone").value("123456789"));  // Verificando também o telefone

        // Verifica se o método foi chamado uma vez
        verify(clienteService, times(1)).buscarClientePorId(1L);
    }

    // Teste do método PUT (alterar cliente)
    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testAlterarCliente() throws Exception {
        // Criação do cliente original
        Cliente cliente = new Cliente("João", "joao@exemplo.com", "123456789");

        // Cliente alterado
        Cliente clienteAlterado = new Cliente("João Silva", "joao.silva@exemplo.com", "987654321");

        // Simula a criação do cliente
        when(clienteService.criarCliente(any(Cliente.class))).thenReturn(cliente);

        // Simula a alteração do cliente
        when(clienteService.alterarCliente(1L, clienteAlterado)).thenReturn(clienteAlterado);

        // Criação do cliente via POST
        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andDo(print())  // Mostra o resultado da criação no console
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.email").value("joao@exemplo.com"))
                .andExpect(jsonPath("$.telefone").value("123456789"));

        // Alteração do cliente via PUT
        mockMvc.perform(put("/api/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteAlterado)))
                .andDo(print())  // Mostra o resultado da alteração no console
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao.silva@exemplo.com"))
                .andExpect(jsonPath("$.telefone").value("987654321"));

        // Verifica as interações com o serviço
        verify(clienteService, times(1)).criarCliente(any(Cliente.class));
        verify(clienteService, times(1)).alterarCliente(1L, clienteAlterado);
    }

}
