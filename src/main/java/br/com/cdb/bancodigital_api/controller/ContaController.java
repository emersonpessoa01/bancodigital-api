package br.com.cdb.bancodigital_api.controller;

import br.com.cdb.bancodigital_api.dto.ContaDTO;
import br.com.cdb.bancodigital_api.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/{id}")
    public ResponseEntity<ContaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}/saldo")
    public ResponseEntity<Double> consultarSaldo(@PathVariable Long id) {
        Double saldo = service.consultarSaldo(id);
        return ResponseEntity.ok(saldo);
    }
    @PostMapping("/{id}/transferencia")
    public ResponseEntity<Void> transferir(@PathVariable Long id, @RequestBody @Valid TrasnferenciaDTO dto) {
        service.transferir(id, dto);
        return ResponseEntity.noContent().build();
    }
}
