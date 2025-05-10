package meu.crud.teste.repository;

import meu.crud.teste.model.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByCpf(String cpf);

    Optional<Cliente> findByCpf(String cpf);
    void deleteByCpf(String cpf);
    List<Cliente> findByCpfStartingWith(String cpf);
    List<Cliente> findByCpfContaining(String cpfSanitizado);
}
