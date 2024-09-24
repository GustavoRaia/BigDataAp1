package br.edu.ibmec.bigdatacloud.blog.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ibmec.bigdatacloud.blog.exception.EnderecoException;
import br.edu.ibmec.bigdatacloud.blog.model.Endereco;
import br.edu.ibmec.bigdatacloud.blog.repository.EnderecoRepository;
import jakarta.validation.Valid;

@Service
public class EnderecoService {
    
    @Autowired
    private EnderecoRepository enderecoRepository;

    /* Método para criação de Endereco
     * 
     * Recebe como argumento o endereço a ser cadastrado
     * Verifica se o endereço já existe e o cadastra no banco de dados
     * Caso o endereço já exista, lança uma exceção
     */
    public Endereco criaEndereco(Endereco endereco) throws EnderecoException {

        Optional<Endereco> optEndereco = this.enderecoRepository.findEnderecoByCep(endereco.getCep());

        if (optEndereco.isPresent()) {
            throw new EnderecoException("Endereço com CEP informado já cadastrado");
        }

        //INSERE NA BASE DE DADOS
        enderecoRepository.save(endereco);

        return endereco;
    }

    /* Método para alteração dos valores do Endereço
     * 
     * Recebe como argumento o identificador e os atributos do endereço a serem atualizados
     * Verifica se o endereço existe e o atualiza
     */
    public Endereco updateEndereco(Long id, @Valid Endereco enderecoDetails) {
        Endereco endereco = enderecoRepository.findById(id)
                            .orElseThrow(() -> 
                            new IllegalArgumentException("Endereço não encontrado"));
                
        endereco.setRua(enderecoDetails.getRua());
        endereco.setNumero(enderecoDetails.getNumero());
        endereco.setBairro(enderecoDetails.getBairro());
        endereco.setCidade(enderecoDetails.getCidade());
        endereco.setEstado(enderecoDetails.getEstado());
        endereco.setCep(enderecoDetails.getCep());

        return enderecoRepository.save(endereco);
    }
    
    /* Método para busca de Endereço
     * 
     * Recebe como argumento o identificador do endereço
     * Retorna o endereço encontrado
     */
    public Endereco buscaEndereco(Long id) {
        return this.findEndereco(id);
    }
    /* Método auxiliar para buscar um endereço
     * 
     * Verifica se o endereco existe e o retorna
     * Caso não exista, retorna null
     */
    private Endereco findEndereco(Long id) {
        Optional<Endereco> Endereco = enderecoRepository.findById(id);

        if (Endereco.isEmpty())
            return null;

        return Endereco.get();
    }

    /* Método para Excluir um endereço
     *
     * Recebe como argumento o identificador do endereco
     * Verifica se o endereco existe e o exclui
     */
    public void deletaEndereco(Long id) {
        if (enderecoRepository.existsById(id)) {
            enderecoRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Endereço não encontrado");
        }
    }

}
