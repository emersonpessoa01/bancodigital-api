package br.com.cdb.bancodigital_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PixRequestDTO {
    @NotNull
    private Long contaDestinoId;

    @NotNull
    @Min(value = 0, message = "Valor deve ser maior que zero")
    private Double valor;
}
