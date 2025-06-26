package br.com.cdb.bancodigital_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferenciaDTO {
    private Long contaDestinoId;
    private Double valor;
}
