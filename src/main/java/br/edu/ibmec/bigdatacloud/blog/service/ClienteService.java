package br.edu.ibmec.bigdatacloud.blog.service;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ibmec.bigdatacloud.blog.exception.ClienteException;
import br.edu.ibmec.bigdatacloud.blog.model.Cliente;
import br.edu.ibmec.bigdatacloud.blog.model.Endereco;
import br.edu.ibmec.bigdatacloud.blog.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    /*
     * Função para Cadastro de Cliente
     * 
     * Recebe como argumento o cliente a ser cadastrado
     * Verifica se o cliente já existe e o cadastra no banco de dados
     * Caso o cliente já exista, lança uma exceção
     */
    public Cliente criaCliente(Cliente cliente) throws ClienteException {
       
        Optional<Cliente> optCliente = this.clienteRepository.findClienteByCpf(cliente.getCpf());

        if (optCliente.isPresent()) {
            throw new ClienteException("Cliente com CPF informado já cadastrado");
        }

        clienteRepository.save(cliente);

        return cliente;
    }

    /* Método para tualizar os atributos do cliente
     * 
     * Recebe como argumento o identificador e 
     * os atributos do cliente a serem atualizados.
     * Verifica se o cliente existe e o atualiza.
    */
    public Cliente updateCliente(Long id, @Valid Cliente clienteDetails) {
        Cliente cliente =   clienteRepository.findById(id)
                            .orElseThrow(() -> 
                            new IllegalArgumentException("Cliente não encontrado"));
                
        cliente.setNome(clienteDetails.getNome());
        cliente.setEmail(clienteDetails.getEmail());
        cliente.setCpf(clienteDetails.getCpf());
        cliente.setDataNascimento(clienteDetails.getDataNascimento());
        cliente.setTelefone(clienteDetails.getTelefone());

        return clienteRepository.save(cliente);
    }

    /* Método para busca de Cliente
     * 
     * Recebe como argumento o identificador do cliente
     * Retorna o cliente encontrado
     */
    public Cliente buscaCliente(Long id) {
        return this.findCliente(id);
    }
    /* Método auxiliar para buscar um cliente
     * 
     * Recebe como argumento o identificador do cliente
     * Verifica se o cliente existe e o exclui
     */
    private Cliente findCliente(Long id) {
        Optional<Cliente> Cliente = clienteRepository.findById(id);

        if (Cliente.isEmpty())
            return null;

        return Cliente.get();
    }

    /* Método para Excluir um cliente
     * 
     * Recebe como argumento o identificador do cliente
     * Verifica se o cliente existe e o exclui
     */
    public void deletaCliente(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Cliente não encontrado");
        }
    }

    @Transactional
    public List<Endereco> getAllEnderecos(Long id) {
        Cliente cliente =   clienteRepository.findById(id)
                            .orElseThrow(() -> 
                            new IllegalArgumentException("Cliente não encontrado"));

        // Retornar a lista de endereços do cliente
        return cliente.getEnderecos();
    }

}