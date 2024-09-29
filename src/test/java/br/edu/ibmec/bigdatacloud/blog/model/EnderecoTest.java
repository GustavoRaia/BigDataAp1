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

public class EnderecoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void should_fail_when_rua_is_blank() {
        // Arrange
        Endereco endereco = new Endereco();
        endereco.setRua(""); // Rua em branco
        endereco.setNumero("123");
        endereco.setBairro("Bairro Válido");
        endereco.setCidade("Cidade Válida");
        endereco.setEstado("RJ");
        endereco.setCep("12345-678");

        // Act
        Set<ConstraintViolation<Endereco>> violations = validator.validate(endereco);

        // Assert
        assertEquals(2, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Rua é obrigatório")));
    }

    @Test
    public void should_fail_when_rua_length_is_less_than_3() {
        Endereco endereco = new Endereco();
        endereco.setRua("AB"); // Rua com menos de 3 caracteres
        endereco.setNumero("123");
        endereco.setBairro("Bairro Válido");
        endereco.setCidade("Cidade Válida");
        endereco.setEstado("RJ");
        endereco.setCep("12345-678");

        Set<ConstraintViolation<Endereco>> violations = validator.validate(endereco);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Rua deve ter entre 3 e 255 caracteres")));
    }

    @Test
    public void should_fail_when_rua_length_is_more_than_255() {
        Endereco endereco = new Endereco();
        endereco.setRua("A".repeat(256)); // Rua com mais de 255 caracteres
        endereco.setNumero("123");
        endereco.setBairro("Bairro Válido");
        endereco.setCidade("Cidade Válida");
        endereco.setEstado("RJ");
        endereco.setCep("12345-678");

        Set<ConstraintViolation<Endereco>> violations = validator.validate(endereco);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Rua deve ter entre 3 e 255 caracteres")));
    }

    @Test
    public void should_fail_when_cep_format_is_invalid() {
        // Arrange
        Endereco endereco = new Endereco();
        endereco.setCep("1234567"); // CEP fora do formato esperado
        endereco.setRua("Rua Válida");
        endereco.setNumero("123");
        endereco.setBairro("Bairro Válido");
        endereco.setCidade("Cidade Válida");
        endereco.setEstado("RJ");

        // Act
        Set<ConstraintViolation<Endereco>> violations = validator.validate(endereco);

        // Assert
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("CEP deve seguir o formato XXXXX-XXX")));
    }

    @Test
    public void should_fail_when_bairro_is_blank() {
        // Arrange
        Endereco endereco = new Endereco();
        endereco.setBairro(""); // Bairro em branco
        endereco.setRua("Rua Válida");
        endereco.setNumero("123");
        endereco.setCidade("Cidade Válida");
        endereco.setEstado("RJ");
        endereco.setCep("12345-678");

        // Act
        Set<ConstraintViolation<Endereco>> violations = validator.validate(endereco);

        // Assert
        assertEquals(2, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Bairro é obrigatório")));
    }

    @Test
    public void should_fail_when_bairro_length_is_less_than_3() {
        Endereco endereco = new Endereco();
        endereco.setBairro("AB"); // Bairro com menos de 3 caracteres
        endereco.setRua("Rua Válida");
        endereco.setNumero("123");
        endereco.setCidade("Cidade Válida");
        endereco.setEstado("RJ");
        endereco.setCep("12345-678");

        Set<ConstraintViolation<Endereco>> violations = validator.validate(endereco);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Bairro deve ter entre 3 e 100 caracteres")));
    }

    @Test
    public void should_fail_when_bairro_length_is_more_than_100() {
        Endereco endereco = new Endereco();
        endereco.setBairro("A".repeat(101)); // Bairro com mais de 100 caracteres
        endereco.setRua("Rua Válida");
        endereco.setNumero("123");
        endereco.setCidade("Cidade Válida");
        endereco.setEstado("RJ");
        endereco.setCep("12345-678");

        Set<ConstraintViolation<Endereco>> violations = validator.validate(endereco);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Bairro deve ter entre 3 e 100 caracteres")));
    }


    @Test
    public void should_fail_when_cidade_length_is_invalid() {
        // Arrange
        Endereco endereco = new Endereco();
        endereco.setCidade("A"); // Cidade com menos de 2 caracteres
        endereco.setRua("Rua Válida");
        endereco.setNumero("123");
        endereco.setBairro("Bairro Válido");
        endereco.setEstado("RJ");
        endereco.setCep("12345-678");

        // Act
        Set<ConstraintViolation<Endereco>> violations = validator.validate(endereco);

        // Assert
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Cidade deve ter entre 2 e 100 caracteres")));
    }

    @Test
    public void should_fail_when_cidade_length_is_less_than_2() {
        Endereco endereco = new Endereco();
        endereco.setCidade("A"); // Cidade com menos de 2 caracteres
        endereco.setRua("Rua Válida");
        endereco.setNumero("123");
        endereco.setBairro("Bairro Válido");
        endereco.setEstado("RJ");
        endereco.setCep("12345-678");

        Set<ConstraintViolation<Endereco>> violations = validator.validate(endereco);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Cidade deve ter entre 2 e 100 caracteres")));
    }

    @Test
    public void should_fail_when_cidade_length_is_more_than_100() {
        Endereco endereco = new Endereco();
        endereco.setCidade("A".repeat(256)); // Cidade com mais de 100 caracteres
        endereco.setRua("Rua Válida");
        endereco.setNumero("123");
        endereco.setBairro("Bairro Válido");
        endereco.setEstado("RJ");
        endereco.setCep("12345-678");

        Set<ConstraintViolation<Endereco>> violations = validator.validate(endereco);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Cidade deve ter entre 2 e 100 caracteres")));
    }

    @Test
    public void should_fail_when_estado_is_blank() {
        // Arrange
        Endereco endereco = new Endereco();
        endereco.setEstado(""); // Estado em branco
        endereco.setRua("Rua Válida");
        endereco.setNumero("123");
        endereco.setBairro("Bairro Válido");
        endereco.setCidade("Cidade Válida");
        endereco.setCep("12345-678");

        // Act
        Set<ConstraintViolation<Endereco>> violations = validator.validate(endereco);

        // Assert
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Estado é obrigatório")));
    }

    @Test
    public void should_pass_when_all_fields_are_valid() {
        // Arrange
        Endereco endereco = new Endereco();
        endereco.setRua("Rua Válida");
        endereco.setNumero("123");
        endereco.setBairro("Bairro Válido");
        endereco.setCidade("Cidade Válida");
        endereco.setEstado("RJ");
        endereco.setCep("12345-678");

        // Act
        Set<ConstraintViolation<Endereco>> violations = validator.validate(endereco);

        // Assert
        assertEquals(0, violations.size()); // Nenhuma violação deve ocorrer
    }
}
