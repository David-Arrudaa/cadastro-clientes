package meu.crud.teste.service;

import lombok.RequiredArgsConstructor;
import meu.crud.teste.exptions.ConflictException;
import meu.crud.teste.exptions.ResourceNotFoundException;
import meu.crud.teste.model.entities.Cliente;
import meu.crud.teste.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente salvarCliente(Cliente cliente) {
        if (clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new ConflictException("CPF " + cliente.getCpf() + " já cadastrado");
        }
        return clienteRepository.save(cliente);
    }

    public Cliente atualizarCliente(String cpf, Cliente clienteAtualizado) {
        Cliente clienteExistente = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com CPF: " + cpf));
        if (!cpf.equals(clienteAtualizado.getCpf())) {
            throw new IllegalArgumentException("CPF da URL não corresponde ao corpo da requisição");
        }
        clienteExistente.setNome(clienteAtualizado.getNome());
        if (clienteAtualizado.getTelefones() != null) {
            clienteExistente.getTelefones().clear();
            clienteExistente.getTelefones().addAll(clienteAtualizado.getTelefones());
            clienteExistente.getTelefones().forEach(t -> t.setCliente(clienteExistente));
        }
        return clienteRepository.save(clienteExistente);
    }

    public void deletarClientePorCpf(String cpf) {
        String cpfSanitizado = cpf.replaceAll("[^0-9]", "");
        Cliente cliente = clienteRepository.findByCpf(cpfSanitizado)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com CPF: " + cpf));
        clienteRepository.delete(cliente);
    }

    public List<Cliente> buscarClientesPorCpfParcial(String cpf) {
        String cpfSanitizado = cpf.replaceAll("[^0-9]", "");
        if (cpfSanitizado.length() < 3) {
            throw new IllegalArgumentException("Informe pelo menos 3 dígitos para busca");
        }
        return clienteRepository.findByCpfContaining(cpfSanitizado);
    }

    public Optional<Cliente> buscarPorCpfExato(String cpf) {
        String cpfSanitizado = cpf.replaceAll("[^0-9]", "");
        return clienteRepository.findByCpf(cpfSanitizado);
    }
}