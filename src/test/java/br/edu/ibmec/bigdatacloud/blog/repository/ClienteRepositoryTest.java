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
import org.springframework.test.context.ActiveProfiles;

import br.edu.ibmec.bigdatacloud.blog.model.Cliente;

@DataJpaTest
@ActiveProfiles("test")
public class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    private Long clienteId;

    @BeforeEach
    void setUp() {
        Cliente cliente = new Cliente();
        cliente.setNome("Derek Jeter");
        cliente.setEmail("derek.jeter@example.com");
        cliente.setCpf("142.753.940-54");
        cliente.setDataNascimento(LocalDate.of(1990, 1, 1));

        Cliente salvaCliente = clienteRepository.save(cliente);
        clienteId = (long) salvaCliente.getId();
    }

    @Test
    void should_pass_when_find_by_id() {
        Optional<Cliente> cliente = clienteRepository.findById(clienteId);
        assertTrue(cliente.isPresent());
        assertEquals("Derek Jeter", cliente.get().getNome());
    }

    @Test
    void should_fail_when_find_by_id() {
        Optional<Cliente> cliente = clienteRepository.findById((long) 9999); // Id Inexistente
        assertFalse(cliente.isPresent());
    }

    @Test
    void should_pass_when_exists_by_email() {
        boolean exists = clienteRepository.existsByEmail("derek.jeter@example.com");
        assertTrue(exists);
    }

    @Test
    void should_fail_when_exists_by_email() {
        boolean exists = clienteRepository.existsByEmail("unknown@example.com");
        assertFalse(exists);
    }

    @Test
    void should_pass_when_exists_by_cpf() {
        boolean exists = clienteRepository.existsByCpf("142.753.940-54");
        assertTrue(exists);
    }

    @Test
    void should_fail_when_exists_by_cpf() {
        boolean exists = clienteRepository.existsByCpf("987.654.321-00");
        assertFalse(exists);
    }

    @Test
    void should_save_cliente() {
        Cliente newCliente = new Cliente();
        newCliente.setNome("Jane Doe");
        newCliente.setEmail("jane.doe@example.com");
        newCliente.setCpf("987.654.321-00");
        newCliente.setDataNascimento(LocalDate.of(1995, 5, 5));

        Cliente salvaCliente = clienteRepository.save(newCliente);

        assertNotNull(salvaCliente.getId());
        assertEquals("Jane Doe", salvaCliente.getNome());
    }

}
