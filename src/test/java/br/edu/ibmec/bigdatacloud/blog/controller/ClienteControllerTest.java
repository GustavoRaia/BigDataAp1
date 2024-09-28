package br.edu.ibmec.bigdatacloud.blog.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.ibmec.bigdatacloud.blog.model.Cliente;
import br.edu.ibmec.bigdatacloud.blog.repository.ClienteRepository;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;

@AutoConfigureMockMvc
@WebMvcTest(controllers = ClienteController.class)
public class ClienteControllerTest {

    @MockBean
    private ClienteRepository clienteRepository;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void should_create_cliente() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNome("Fulano");
        cliente.setCpf("12345678901");
        cliente.setDataNascimento(LocalDateTime.now());

        given(this.clienteRepository.save(cliente)).willReturn(cliente);

        this.mvc
        .perform(MockMvcRequestBuilders
                .post("/cliente")
                .content(this.mapper.writeValueAsString(cliente))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", is("Teste")));

    }

    @Test
    public void should_get_cliente() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNome("Fulano");
        cliente.setCpf("12345678901");
        cliente.setDataNascimento(LocalDateTime.now());

        // Configurando Mock de Banco de Dados
        given(this.clienteRepository.findById((long) 1)).willReturn(Optional.of(cliente));

        this.mvc
        .perform(MockMvcRequestBuilders
                .get("/cliente/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)));
    }

    @Test
    public void should_get_cliente_with_not_found() throws Exception {

        //Configurando mock de banco
        given(this.clienteRepository.findById((long) 1)).willReturn(Optional.empty());

        this.mvc
        .perform(MockMvcRequestBuilders
                .get("/cliente/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

}
