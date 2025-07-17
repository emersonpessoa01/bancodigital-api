package br.com.cdb.bancodigital_api.dto;

import lombok.Data;

@Data
public class PagamentoCartaoDTO {
    private Double valor;
    private String descricao;
}
