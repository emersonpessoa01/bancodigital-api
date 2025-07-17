package br.com.cdb.bancodigital_api.dto;

import lombok.Data;

import java.util.List;

@Data
public class FaturaDTO {
    private Long cartaoId;
    private Double total;
    private List<LancamentoFaturaDTO> lancamentos;
}
