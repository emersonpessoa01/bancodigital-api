package br.com.cdb.bancodigital_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "lancamentos_cartao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LancamentoCartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private Double valor;
    private LocalDate data;
    private Boolean pago;

    @ManyToOne
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;
}
