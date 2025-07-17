package br.com.cdb.bancodigital_api.controller;

import br.com.cdb.bancodigital_api.dto.AlterarLimiteDTO;
import br.com.cdb.bancodigital_api.dto.AtualizarStatusCartaoDTO;
import br.com.cdb.bancodigital_api.dto.CartaoDTO;
import br.com.cdb.bancodigital_api.dto.PagamentoCartaoDTO;
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



}
