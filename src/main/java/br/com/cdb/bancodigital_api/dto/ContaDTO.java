package br.com.cdb.bancodigital_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContaDTO {
    private Long id;
    private String numero;
    private String agencia;
    private Double saldo;
    private Long clienteId; // ID do cliente associado à conta
}
