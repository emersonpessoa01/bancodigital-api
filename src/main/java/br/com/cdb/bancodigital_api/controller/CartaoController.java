package br.com.cdb.bancodigital_api.controller;

import br.com.cdb.bancodigital_api.dto.*;
import br.com.cdb.bancodigital_api.service.CartaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    private final CartaoService service;

    public CartaoController(CartaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CartaoDTO> criar(@RequestBody @Valid CartaoDTO dto) {
        return ResponseEntity.ok(service.salvar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartaoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<CartaoDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartaoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid CartaoDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{id}/pagamento")
    public ResponseEntity<String> realizarPagamento(@PathVariable Long id, @RequestBody PagamentoCartaoDTO dto) {
        service.realizarPagamento(id, dto);
        return ResponseEntity.ok("Pagamento realizado com sucesso.");
    }
    @PutMapping("/{id}/limite")
    public ResponseEntity<String> alterarLimite(@PathVariable Long id, @RequestBody AlterarLimiteDTO dto) {
        service.alterarLimite(id, dto);
        return ResponseEntity.ok("Limite do cartão alterado com sucesso.");
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<String> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusCartaoDTO dto) {
        service.atualizarStatus(id, dto);
        return ResponseEntity.ok("Status do cartão atualizado com sucesso.");
    }

    @PutMapping("/{id}/senha")
    public ResponseEntity<String> alterarSenha(@PathVariable Long id, @RequestBody @Valid AlterarSenhaCartaoDTO dto) {
        service.alterarSenha(id, dto);
        return ResponseEntity.ok("Senha do cartão atualizada com sucesso.");
    }
    @GetMapping("/{id}/fatura")
    public ResponseEntity<FaturaDTO> consultarFatura(@PathVariable Long id) {
        return ResponseEntity.ok(service.consultarFatura(id));
    }


}
