package br.edu.ibmec.bigdatacloud.blog.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.List;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.edu.ibmec.bigdatacloud.blog.exception.EnderecoException;
import br.edu.ibmec.bigdatacloud.blog.model.Cliente;
import br.edu.ibmec.bigdatacloud.blog.model.Endereco;
import br.edu.ibmec.bigdatacloud.blog.repository.ClienteRepository;
import br.edu.ibmec.bigdatacloud.blog.repository.EnderecoRepository;

class EnderecoServiceTest {

    @InjectMocks
    private EnderecoService enderecoService;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    private Endereco endereco;
    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        endereco = new Endereco();
        endereco.setRua("Rua A");
        endereco.setNumero("123");
        endereco.setBairro("Centro");
        endereco.setCidade("São Paulo");
        endereco.setEstado("SP");
        endereco.setCep("12345-678");

        cliente = new Cliente();
        cliente.setId(1);
        endereco.setCliente(cliente);
    }

    @Test
    public void should_create_endereco() throws EnderecoException {
        // Mocking
        when(enderecoRepository.findEnderecoByCep(anyString())).thenReturn(Optional.empty());
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);

        // Teste
        Endereco result = enderecoService.criaEndereco(endereco, 1L);

        // Verificações
        assertNotNull(result);
        assertEquals("Rua A", result.getRua());
        verify(enderecoRepository, times(1)).save(endereco);
    }

    @Test
    public void should_not_create_duplicate_endereco() {
        // Mocking
        when(enderecoRepository.findEnderecoByCep(anyString())).thenReturn(Optional.of(endereco));

        // Teste
        assertThrows(EnderecoException.class, () -> enderecoService.criaEndereco(endereco, 1L));

        // Verificações
        verify(enderecoRepository, never()).save(any(Endereco.class));
    }

    @Test
    public void should_update_endereco() {
        // Arrange
        Long enderecoId = 9999L;
        Endereco endereco = new Endereco();
        endereco.setRua("Rua A");

        Endereco enderecoDetails = new Endereco();
        enderecoDetails.setRua("Rua B");

        when(enderecoRepository.findById(enderecoId)).thenReturn(Optional.of(endereco));
        when(enderecoRepository.save(endereco)).thenReturn(endereco);

        // Act
        Endereco result = enderecoService.updateEndereco(enderecoId, enderecoDetails);

        // Assert
        assertEquals("Rua B", result.getRua());
        verify(enderecoRepository, times(1)).save(endereco);
    }


    @Test
    public void should_get_all_enderecos() {
        // Mocking
        when(enderecoRepository.findAll()).thenReturn(Collections.singletonList(endereco));

        // Teste
        List<Endereco> enderecos = enderecoService.getAllEnderecos();

        // Verificações
        assertFalse(enderecos.isEmpty());
        verify(enderecoRepository, times(1)).findAll();
    }

    @Test
    public void should_delete_endereco() {
        // Mocking
        when(enderecoRepository.existsById(anyLong())).thenReturn(true);

        // Teste
        enderecoService.deletaEndereco(1L);

        // Verificações
        verify(enderecoRepository, times(1)).deleteById(1L);
    }

    @Test
    public void should_throw_exception_when_endereco_not_found() {
        // Mocking
        when(enderecoRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Teste
        assertThrows(IllegalArgumentException.class, () -> enderecoService.updateEndereco(1L, endereco));

        // Verificações
        verify(enderecoRepository, never()).save(any(Endereco.class));
    }
}
