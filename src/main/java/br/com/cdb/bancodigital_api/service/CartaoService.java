package br.com.cdb.bancodigital_api.service;

import br.com.cdb.bancodigital_api.dto.*;
import br.com.cdb.bancodigital_api.exception.ResourceNotFoundException;
import br.com.cdb.bancodigital_api.model.Cartao;
import br.com.cdb.bancodigital_api.model.Conta;
import br.com.cdb.bancodigital_api.model.LancamentoCartao;
import br.com.cdb.bancodigital_api.repository.CartaoRepository;
import br.com.cdb.bancodigital_api.repository.ContaRepository;
import br.com.cdb.bancodigital_api.repository.LancamentoCartaoRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartaoService {
    private final CartaoRepository cartaoRepository;
    private final ContaRepository contaRepository;
    private final LancamentoCartaoRepository lancamentoCartaoRepository;

    @Autowired
    private final ModelMapper mapper;

    public CartaoService(CartaoRepository cartaoRepository, ContaRepository contaRepository, LancamentoCartaoRepository lancamentoCartaoRepository) {
        this.cartaoRepository = cartaoRepository;
        this.contaRepository = contaRepository;
        this.lancamentoCartaoRepository = lancamentoCartaoRepository;
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
    @Transactional
    public void realizarPagamento(Long cartaoId, PagamentoCartaoDTO dto) {
        Cartao cartao = cartaoRepository.findById(cartaoId)
                .orElseThrow(() -> new ResourceNotFoundException("Cartão não encontrado"));

        if (Boolean.FALSE.equals(cartao.getStatus())) {
            throw new IllegalStateException("Cartão está inativo");
        }

        if ("crédito".equalsIgnoreCase(cartao.getTipo())) {
            if (dto.getValor() > cartao.getLimite()) {
                throw new IllegalArgumentException("Valor excede o limite disponível");
            }

            cartao.setLimite(cartao.getLimite() - dto.getValor());
            cartao.setFatura(cartao.getFatura() + dto.getValor());
        } else if ("débito".equalsIgnoreCase(cartao.getTipo())) {
            Conta conta = cartao.getConta();
            if (dto.getValor() > conta.getSaldo()) {
                throw new IllegalArgumentException("Saldo insuficiente na conta");
            }

            conta.setSaldo(conta.getSaldo() - dto.getValor());
            contaRepository.save(conta);
        }

        cartaoRepository.save(cartao);
    }
    @Transactional
    public void alterarLimite(Long cartaoId, AlterarLimiteDTO dto) {
        Cartao cartao = cartaoRepository.findById(cartaoId)
                .orElseThrow(() -> new ResourceNotFoundException("Cartão não encontrado"));

        if (Boolean.FALSE.equals(cartao.getStatus())) {
            throw new IllegalStateException("Cartão está inativo");
        }

        if (!"crédito".equalsIgnoreCase(cartao.getTipo())) {
            throw new IllegalArgumentException("Apenas cartões de crédito podem ter limite alterado");
        }

        if (dto.getNovoLimite() == null || dto.getNovoLimite() <= 0) {
            throw new IllegalArgumentException("Limite inválido");
        }

        cartao.setLimite(dto.getNovoLimite());
        cartaoRepository.save(cartao);
    }
    @Transactional
    public void atualizarStatus(Long cartaoId, AtualizarStatusCartaoDTO dto) {
        Cartao cartao = cartaoRepository.findById(cartaoId)
                .orElseThrow(() -> new ResourceNotFoundException("Cartão não encontrado"));

        if (dto.getStatus() == null) {
            throw new IllegalArgumentException("Status não pode ser nulo");
        }

        cartao.setStatus(dto.getStatus());
        cartaoRepository.save(cartao);
    }
    @Transactional
    public void alterarSenha(Long id, AlterarSenhaCartaoDTO dto) {
        Cartao cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cartão não encontrado"));

        cartao.setSenha(dto.getNovaSenha());
        cartaoRepository.save(cartao);
    }
    public FaturaDTO consultarFatura(Long id) {
        Cartao cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cartão não encontrado"));

        if (!"Crédito".equalsIgnoreCase(cartao.getTipo())) {
            throw new IllegalArgumentException("Fatura disponível apenas para cartões de crédito.");
        }

        List<LancamentoCartao> lancamentos = lancamentoCartaoRepository.findByCartaoId(id);

        List<LancamentoFaturaDTO> dtoList = lancamentos.stream().map(l -> new LancamentoFaturaDTO(
                l.getDescricao(), l.getValor(), l.getData(), l.getPago()
        )).toList();

        double total = lancamentos.stream()
                .filter(l -> !l.getPago())
                .mapToDouble(LancamentoCartao::getValor)
                .sum();

        FaturaDTO fatura = new FaturaDTO();
        fatura.setCartaoId(cartao.getId());
        fatura.setTotal(total);
        fatura.setLancamentos(dtoList);

        return fatura;
    }

}

