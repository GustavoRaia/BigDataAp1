package br.edu.ibmec.bigdatacloud.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ibmec.bigdatacloud.blog.model.Endereco;
import br.edu.ibmec.bigdatacloud.blog.service.EnderecoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @PostMapping
    public ResponseEntity<Endereco> criarEndereco(@Valid @RequestBody Endereco endereco) throws Exception {
        enderecoService.criaEndereco(endereco);
        return new ResponseEntity<>(endereco, HttpStatus.CREATED);
    }

}
