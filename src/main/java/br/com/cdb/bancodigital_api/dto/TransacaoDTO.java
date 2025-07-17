package br.com.cdb.bancodigital_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransacaoDTO {

    @NotNull(message = "Valor é obrigatório")
    @Min(value = 0, message = "Valor deve ser maior que zero")
    private Double valor;
}
