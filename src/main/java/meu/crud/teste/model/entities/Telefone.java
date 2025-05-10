package meu.crud.teste.model.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "telefone")
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ddd", length = 2, nullable = false)
    private String ddd;
    @Column(name = "numero", length = 9, nullable = false)
    private String numero;

    public void setCliente(Cliente clienteExistente) {
    }
}
