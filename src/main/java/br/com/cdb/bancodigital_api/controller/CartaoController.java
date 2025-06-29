package br.com.cdb.bancodigital_api.controller;

import br.com.cdb.bancodigital_api.dto.CartaoDTO;
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
}
