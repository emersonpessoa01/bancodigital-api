package br.com.cdb.bancodigital_api.controller;

import br.com.cdb.bancodigital_api.dto.ContaDTO;
import br.com.cdb.bancodigital_api.dto.TransacaoDTO;
import br.com.cdb.bancodigital_api.dto.PixRequestDTO;
import br.com.cdb.bancodigital_api.dto.TransferenciaDTO;
import br.com.cdb.bancodigital_api.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

    private final ContaService service;

    public ContaController(ContaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ContaDTO> criar(@RequestBody @Valid ContaDTO dto) {
        return ResponseEntity.ok(service.salvar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ContaDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaDTO> atualizar(@PathVariable Long id, @RequestBody @Valid ContaDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/saldo")
    public ResponseEntity<Double> consultarSaldo(@PathVariable Long id) {
        return ResponseEntity.ok(service.consultarSaldo(id));
    }

    @PostMapping("/{id}/transferencia")
    public ResponseEntity<Void> transferir(@PathVariable Long id, @RequestBody @Valid TransferenciaDTO dto) {
        service.transferir(id, dto);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{id}/pix")
    public ResponseEntity<String> realizarPix(
            @PathVariable Long id,
            @RequestBody @Valid PixRequestDTO dto) {
        service.realizarPix(id, dto);
        return ResponseEntity.ok("Pix realizado com sucesso.");
    }
    @PostMapping("/{id}/deposito")
    public ResponseEntity<String> realizarDeposito(
            @PathVariable Long id,
            @RequestBody @Valid TransacaoDTO dto) {
        service.realizarDeposito(id, dto);
        return ResponseEntity.ok("Depósito realizado com sucesso.");
    }
    @PostMapping("/{id}/saque")
    public ResponseEntity<String> realizarSaque(
            @PathVariable Long id,
            @RequestBody @Valid TransacaoDTO dto) {
        service.realizarSaque(id, dto);
        return ResponseEntity.ok("Saque realizado com sucesso.");
    }
    @PutMapping("/{id}/manutencao")
    public ResponseEntity<String> aplicarTaxaManutencao(@PathVariable Long id) {
        service.aplicarTaxaManutencao(id);
        return ResponseEntity.ok("Taxa de manutenção aplicada com sucesso.");
    }



}
