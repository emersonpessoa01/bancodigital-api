package br.com.cdb.bancodigital_api.dto;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

// DTO
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LancamentoDTO {
    private String descricao;
    private Double valor;
    private LocalDate data; // <-- antes era LocalDateTime
    private Boolean pago;
}
