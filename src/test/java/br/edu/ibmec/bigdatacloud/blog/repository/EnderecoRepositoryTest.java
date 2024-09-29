package br.edu.ibmec.bigdatacloud.blog.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.edu.ibmec.bigdatacloud.blog.model.Cliente;
import br.edu.ibmec.bigdatacloud.blog.model.Endereco;

@DataJpaTest
public class EnderecoRepositoryTest {

   @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setNome("John Doe");
        cliente.setEmail("john.doe@example.com");
        cliente.setCpf("142.753.940-54");
        cliente.setDataNascimento(LocalDate.of(1990, 1, 1));
        clienteRepository.save(cliente);
    }

    @Test
    void should_pass_when_find_by_id() {
        Endereco endereco = new Endereco();
        endereco.setRua("Rua Teste");
        endereco.setNumero("123");
        endereco.setBairro("Centro");
        endereco.setCidade("São Paulo");
        endereco.setEstado("SP");
        endereco.setCep("40720-239");
        endereco.setCliente(cliente);
        enderecoRepository.save(endereco);

        Optional<Endereco> foundEndereco = enderecoRepository.findById((long) endereco.getId());

        assertTrue(foundEndereco.isPresent());
        assertEquals("Rua Teste", foundEndereco.get().getRua());
        assertEquals("123", foundEndereco.get().getNumero());
    }

    @Test
    void should_fail_when_find_by_id() {
        Optional<Endereco> foundEndereco = enderecoRepository.findById((long) 9999);
        assertFalse(foundEndereco.isPresent());
    }

    @Test
    void should_fail_when_exists_by_cep() {
        boolean exists = enderecoRepository.existsByCep("12345-678");
        assertFalse(exists);
    }

    @Test
    void should_pass_when_exists_by_cep() {
        Endereco endereco = new Endereco();
        endereco.setRua("Rua Teste");
        endereco.setNumero("123");
        endereco.setBairro("Centro");
        endereco.setCidade("São Paulo");
        endereco.setEstado("SP");
        endereco.setCep("40720-239");
        endereco.setCliente(cliente);
        enderecoRepository.save(endereco);

        boolean exists = enderecoRepository.existsByCep(endereco.getCep());
        assertTrue(exists);
    }

    @Test
    void should_save_endereco() {
        Endereco endereco = new Endereco();
        endereco.setRua("Rua Teste");
        endereco.setNumero("123");
        endereco.setBairro("Centro");
        endereco.setCidade("São Paulo");
        endereco.setEstado("SP");
        endereco.setCep("40720-239");
        endereco.setCliente(cliente);

        Endereco savedEndereco = enderecoRepository.save(endereco);

        assertNotNull(savedEndereco.getId());
        assertEquals("Rua Teste", savedEndereco.getRua());
        assertEquals("123", savedEndereco.getNumero());
        assertEquals(cliente.getId(), savedEndereco.getCliente().getId());
    }

}