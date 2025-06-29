package br.com.cdb.bancodigital_api.service;

import br.com.cdb.bancodigital_api.dto.CartaoDTO;
import br.com.cdb.bancodigital_api.exception.ResourceNotFoundException;
import br.com.cdb.bancodigital_api.model.Cartao;
import br.com.cdb.bancodigital_api.model.Conta;
import br.com.cdb.bancodigital_api.repository.CartaoRepository;
import br.com.cdb.bancodigital_api.repository.ContaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartaoService {
    private final CartaoRepository cartaoRepository;
    private final ContaRepository contaRepository;

    @Autowired
    private final ModelMapper mapper;

    public CartaoService(CartaoRepository cartaoRepository, ContaRepository contaRepository) {
        this.cartaoRepository = cartaoRepository;
        this.contaRepository = contaRepository;
        this.mapper = new ModelMapper();
    }
    public CartaoDTO salvar(CartaoDTO dto){
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada"));

        Cartao cartao = mapper.map(dto, Cartao.class);
        cartao.setConta(conta);
        return mapper.map(cartaoRepository.save(cartao), CartaoDTO.class);
    }
    public CartaoDTO buscarPorId(Long id){
        Cartao cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cartão não encontrado"));
        return mapper.map(cartao, CartaoDTO.class);
    }
    public List<CartaoDTO> listarTodos() {
        return cartaoRepository.findAll()
                .stream()
                .map(cartao -> mapper.map(cartao, CartaoDTO.class))
                .collect(Collectors.toList());
    }
    public CartaoDTO atualizar(Long id, CartaoDTO dto) {
        Cartao cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cartão não encontrado"));

        cartao.setNumero(dto.getNumero());
        cartao.setBandeira(dto.getBandeira());
        cartao.setTipo(dto.getTipo());
        cartao.setLimite(dto.getLimite());

        if (dto.getContaId() != null && !dto.getContaId().equals(cartao.getConta().getId())) {
            Conta novaConta = contaRepository.findById(dto.getContaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada"));
            cartao.setConta(novaConta);
        }

        return mapper.map(cartaoRepository.save(cartao), CartaoDTO.class);
    }
    public void deletar(Long id) {
        Cartao cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cartão não encontrado"));
        cartaoRepository.delete(cartao);
    }
}
