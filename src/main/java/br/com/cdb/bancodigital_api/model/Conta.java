package br.com.cdb.bancodigital_api.model;

import jakarta.persistence.*;
import lombok.*;
import br.com.cdb.bancodigital_api.enums.TipoConta;


@Entity
@Table(name = "contas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    private String agencia;
    private Double saldo;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoConta tipo;

    // valores possíveis: "corrente" ou "poupanca"


    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
