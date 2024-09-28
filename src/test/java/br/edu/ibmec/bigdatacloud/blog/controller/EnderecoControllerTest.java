package br.edu.ibmec.bigdatacloud.blog.controller;

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

import br.edu.ibmec.bigdatacloud.blog.model.Endereco;
import br.edu.ibmec.bigdatacloud.blog.repository.EnderecoRepository;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;

@AutoConfigureMockMvc
@WebMvcTest(controllers = PostController.class)
public class EnderecoControllerTest {

    @MockBean
    private EnderecoRepository enderecoRepository;

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
    public void should_create_endereco() throws Exception {
        Endereco endereco = new Endereco();
        endereco.setRua("Rua Teste");
        endereco.setNumero("123");
        endereco.setBairro("Bairro Teste");
        endereco.setCidade("Cidade Teste");
        endereco.setEstado("Estado Teste");
        endereco.setCep("12345-123");

        given(this.enderecoRepository.save(endereco)).willReturn(endereco);

        this.mvc
        .perform(MockMvcRequestBuilders
                .post("/endereco")
                .content(this.mapper.writeValueAsString(endereco))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", is("Teste")));

    }

    @Test
    public void should_get_endereco() throws Exception {
        Endereco endereco = new Endereco();
        endereco.setRua("Rua Teste");
        endereco.setNumero("123");
        endereco.setBairro("Bairro Teste");
        endereco.setCidade("Cidade Teste");
        endereco.setEstado("Estado Teste");
        endereco.setCep("12345-123");

        // Configurando Mock de Banco de Dados
        given(this.enderecoRepository.findById((long) 1)).willReturn(Optional.of(endereco));

        this.mvc
        .perform(MockMvcRequestBuilders
                .get("/endereco/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)));
    }

    @Test
    public void should_get_endereco_with_not_found() throws Exception {

        //Configurando mock de banco
        given(this.enderecoRepository.findById((long) 1)).willReturn(Optional.empty());

        this.mvc
        .perform(MockMvcRequestBuilders
                .get("/endereco/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

}
