package br.edu.ibmec.bigdatacloud.blog.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClienteTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // public void should_fail_when_name_is_blank() {
    //     // Arrange
    //     Cliente cliente = new Cliente();
    //     cliente.setNome(""); // Nome em branco
    //     cliente.setEmail("email@valido.com");
    //     cliente.setCpf("142.753.940-54");

    //     // Act
    //     Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);

    //     // Assert
    //     assertEquals(1, violations.size());
    //     assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Nome é obrigatório")));
    // }

    @Test
    public void should_fail_when_name_length_is_less_than_3_caracs() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setNome("Jo"); // Nome com menos de 3 caracteres
        cliente.setEmail("email@valido.com");
        cliente.setCpf("142.753.940-54");
    
        // Act
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
    
        // Assert
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Nome deve ter entre 3 e 100 caracteres")));
    }

    @Test
    public void should_fail_when_name_length_is_more_than_100_caracs() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setNome("j".repeat(101)); // Nome com mais de 100 caracteres
        cliente.setEmail("email@valido.com");
        cliente.setCpf("142.753.940-54");
    
        // Act
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
    
        // Assert
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Nome deve ter entre 3 e 100 caracteres")));
    }

    @Test
    public void should_fail_when_name_is_blank() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setNome(""); // Nome em branco
        cliente.setEmail("email@valido.com");
        cliente.setCpf("142.753.940-54");

        // Act
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);

        // Assert
        assertEquals(2, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Nome é obrigatório")));
    }

    @Test
    public void should_fail_when_email_is_invalid() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setNome("Cliente Teste");
        cliente.setEmail("emailinvalido"); // Email inválido
        cliente.setCpf("142.753.940-54");

        // Act
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);

        // Assert
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Email inválido")));
    }

    @Test
    public void should_fail_when_cpf_is_invalid() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setNome("Cliente Teste");
        cliente.setEmail("email@valido.com");
        cliente.setCpf("12345678900"); // CPF inválido, sem formatação

        // Act
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);

        // Assert
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("CPF inválido")));
    }

    @Test
    public void should_fail_when_tel_is_invalid() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setNome("Cliente Teste");
        cliente.setEmail("email@valido.com");
        cliente.setCpf("142.753.940-54");
        cliente.setTelefone("12345-6789"); // Telefone inválido, formato incorreto

        // Act
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);

        // Assert
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Telefone deve seguir o formato:(XX) XXXXX-XXXX")));
    }
}