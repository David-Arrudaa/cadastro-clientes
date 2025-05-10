package meu.crud.teste.controller;

import lombok.RequiredArgsConstructor;
import meu.crud.teste.model.entities.Cliente;
import meu.crud.teste.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Cliente> cadastrar(@RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteService.salvarCliente(cliente));
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<Cliente> atualizarCliente(
            @PathVariable String cpf,
            @RequestBody Cliente clienteAtualizado) {

        if (!cpf.equals(clienteAtualizado.getCpf())) {
            throw new IllegalArgumentException("CPF da URL não corresponde ao corpo");
        }

        Cliente clienteAtualizadoSalvo = clienteService.atualizarCliente(cpf, clienteAtualizado);
        return ResponseEntity.ok(clienteAtualizadoSalvo);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<?> buscarPorCpf(@PathVariable String cpf) {
        if (cpf.length() >= 3) {
            if (cpf.length() == 12) {
                Optional<Cliente> cliente = clienteService.buscarPorCpfExato(cpf);
                return cliente.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
            } else {
                List<Cliente> clientes = clienteService.buscarClientesPorCpfParcial(cpf);
                return ResponseEntity.ok(clientes);
            }
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("Informe pelo menos 3 dígitos do CPF.");
        }
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deletarCliente(@PathVariable String cpf) {
        clienteService.deletarClientePorCpf(cpf);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }


}