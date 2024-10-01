package br.edu.ibmec.bigdatacloud.blog.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.edu.ibmec.bigdatacloud.blog.model.Cliente;

import br.edu.ibmec.bigdatacloud.blog.repository.ClienteRepository;
import br.edu.ibmec.bigdatacloud.blog.repository.EnderecoRepository;
import br.edu.ibmec.bigdatacloud.blog.exception.ClienteException;

public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_create_cliente() throws ClienteException {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setCpf("142.753.940-54");

        when(clienteRepository.findClienteByCpf(cliente.getCpf())).thenReturn(Optional.empty());
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        // Act
        Cliente result = clienteService.criaCliente(cliente);

        // Assert
        assertNotNull(result);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    public void should_not_create_cliente_because_cliente_is_already_registered() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setCpf("142.753.940-54");

        when(clienteRepository.findClienteByCpf(cliente.getCpf())).thenReturn(Optional.of(cliente));

        // Act & Assert
        assertThrows(ClienteException.class, () -> clienteService.criaCliente(cliente));
        verify(clienteRepository, never()).save(cliente);
    }

    @Test
    public void should_get_all_clientes() {
        // Arrange
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(new Cliente());
        when(clienteRepository.findAll()).thenReturn(clientes);

        // Act
        List<Cliente> result = clienteService.getAllClientes();

        // Assert
        assertFalse(result.isEmpty());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    public void should_delete_cliente() {
        // Arrange
        Long clienteId = 9999L;
        when(clienteRepository.existsById(clienteId)).thenReturn(true);

        // Act
        clienteService.deletaCliente(clienteId);

        // Assert
        verify(clienteRepository, times(1)).deleteById(clienteId);
    }

    @Test
    public void should_not_delete_cliente_because_was_not_found() {
        // Arrange
        Long clienteId = 9999L;
        when(clienteRepository.existsById(clienteId)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> clienteService.deletaCliente(clienteId));
        verify(clienteRepository, never()).deleteById(clienteId);
    }

    @Test
    public void should_update_cliente() {
        // Arrange
        Long clienteId = 9999L;
        Cliente cliente = new Cliente();
        cliente.setNome("Fulano");

        Cliente clienteDetails = new Cliente();
        clienteDetails.setNome("Beltrano");

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        // Act
        Cliente result = clienteService.updateCliente(clienteId, clienteDetails);

        // Assert
        assertEquals("Beltrano", result.getNome());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    public void should_not_update_cliente_because_was_not_found() {
        // Arrange
        Long clienteId = 9999L;
        Cliente clienteDetails = new Cliente();

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> clienteService.updateCliente(clienteId, clienteDetails));
        verify(clienteRepository, never()).save(clienteDetails);
    }
}
