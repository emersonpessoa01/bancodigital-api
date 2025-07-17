package br.com.cdb.bancodigital_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LancamentoFaturaDTO {
    private String descricao;
    private Double valor;
    private LocalDate data;
    private Boolean pago;
}
