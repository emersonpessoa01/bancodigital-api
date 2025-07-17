package br.com.cdb.bancodigital_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AlterarSenhaCartaoDTO {
    @NotBlank(message = "A nova senha é obrigatória")
    @Pattern(regexp = "\\d{4,6}", message = "A senha deve conter entre 4 e 6 dígitos numéricos")
    private String novaSenha;
}
