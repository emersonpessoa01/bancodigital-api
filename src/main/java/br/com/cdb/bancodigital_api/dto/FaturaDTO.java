package br.com.cdb.bancodigital_api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder // ✅ ESSA ANOTAÇÃO ESTÁ FALTANDO
public class FaturaDTO {
    private Long cartaoId;
    private Double total;
    private List<LancamentoDTO> lancamentos;
}
