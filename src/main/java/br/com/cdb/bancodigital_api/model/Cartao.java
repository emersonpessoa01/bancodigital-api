package br.com.cdb.bancodigital_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    private Double fatura = 0.0;  // Soma dos pagamentos realizados com cartão de crédito


    @ManyToOne
    @JoinColumn(name = "conta_id")
    private Conta conta; // Associação com a conta bancária a que o cartão pertence

    private Boolean status; // true = ativo, false = inativo
    private String senha;
    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LancamentoFatura> lancamentos = new ArrayList<>();


}
