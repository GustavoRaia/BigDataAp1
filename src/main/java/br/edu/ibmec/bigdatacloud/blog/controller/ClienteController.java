package br.edu.ibmec.bigdatacloud.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ibmec.bigdatacloud.blog.model.Cliente;
import br.edu.ibmec.bigdatacloud.blog.service.ClienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping()
    public ResponseEntity<Cliente> criarCliente(@Valid @RequestBody Cliente cliente) throws Exception { 
        clienteService.criaCliente(cliente);
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }
    
}
