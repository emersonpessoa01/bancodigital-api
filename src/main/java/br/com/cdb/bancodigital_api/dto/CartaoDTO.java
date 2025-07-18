package br.com.cdb.bancodigital_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartaoDTO {
    private Long id;
    private String numero;
    private String bandeira;
    private String tipo; // Ex: "Crédito", "Débito"
    private Double limite;
    private Long contaId; // ID da conta bancária a que o cartão pertence
    private Boolean status = true;
    private Double limiteDiario;

}

