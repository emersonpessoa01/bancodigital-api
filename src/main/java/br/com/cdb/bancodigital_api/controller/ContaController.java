package br.com.cdb.bancodigital_api.controller;

import br.com.cdb.bancodigital_api.dto.ContaDTO;
import br.com.cdb.bancodigital_api.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contas")
public class ContaController {
    private final ContaService service;
    public ContaController(ContaService service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<ContaDTO> criar(@RequestBody @Valid ContaDTO dto){
        return ResponseEntity.ok(service.salvar(dto));
    }
}
