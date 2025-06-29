package br.com.cdb.bancodigital_api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cartoes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cartao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    private String bandeira;
    private String tipo; // Ex: "Crédito", "Débito", "Múltiplo"
    private Double limite;

    @ManyToOne
    @JoinColumn(name = "conta_id")
    private Conta conta; // Associação com a conta bancária a que o cartão pertence

}
