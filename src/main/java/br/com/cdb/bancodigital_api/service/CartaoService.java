package br.com.cdb.bancodigital_api.service;

import br.com.cdb.bancodigital_api.dto.*;
import br.com.cdb.bancodigital_api.exception.ResourceNotFoundException;
import br.com.cdb.bancodigital_api.model.Cartao;
import br.com.cdb.bancodigital_api.model.Conta;
import br.com.cdb.bancodigital_api.model.LancamentoFatura;
import br.com.cdb.bancodigital_api.repository.CartaoRepository;
import br.com.cdb.bancodigital_api.repository.ContaRepository;
import br.com.cdb.bancodigital_api.repository.LancamentoFaturaRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartaoService {
    private final CartaoRepository cartaoRepository;
    private final ContaRepository contaRepository;
    private final LancamentoFaturaRepository lancamentoFaturaRepository;

    private final ModelMapper mapper;

    @Autowired
    public CartaoService(
            CartaoRepository cartaoRepository,
            ContaRepository contaRepository,
            LancamentoFaturaRepository lancamentoFaturaRepository
    ) {
        this.cartaoRepository = cartaoRepository;
        this.contaRepository = contaRepository;
        this.lancamentoFaturaRepository = lancamentoFaturaRepository;
        this.mapper = new ModelMapper();
    }

    public CartaoDTO salvar(CartaoDTO dto) {
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada"));

        Cartao cartao = mapper.map(dto, Cartao.class);
        cartao.setConta(conta);

        // ✅ Garante que status nunca seja null
        if (cartao.getStatus() == null) {
            cartao.setStatus(true); // ou false, se desejar criar inativo por padrão
        }

        // ✅ Garante que fatura comece em 0.0
        if (cartao.getFatura() == null) {
            cartao.setFatura(0.0);
        }

        return mapper.map(cartaoRepository.save(cartao), CartaoDTO.class);
    }

    public CartaoDTO buscarPorId(Long id) {
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

            LancamentoFatura lancamento = LancamentoFatura.builder()
                    .descricao(dto.getDescricao())
                    .valor(dto.getValor())
                    .data(LocalDate.now())
                    .pago(false)
                    .cartao(cartao)
                    .build();

            lancamentoFaturaRepository.save(lancamento);
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

    public FaturaDTO consultarFatura(Long cartaoId) {
        Cartao cartao = cartaoRepository.findById(cartaoId)
                .orElseThrow(() -> new ResourceNotFoundException("Cartão não encontrado"));

        List<LancamentoFatura> lancamentos = lancamentoFaturaRepository.findByCartaoId(cartaoId);

        List<LancamentoDTO> lancamentoDTOs = lancamentos.stream().map(l -> LancamentoDTO.builder()
                        .descricao(l.getDescricao())
                        .valor(l.getValor())
                        .data(l.getData())
                        .pago(l.getPago())
                        .build())
                .collect(Collectors.toList());


        double total = lancamentos.stream().mapToDouble(LancamentoFatura::getValor).sum();

        return FaturaDTO.builder()
                .cartaoId(cartao.getId())
                .total(total)
                .lancamentos(lancamentoDTOs)
                .build();
    }
    @Transactional
    public void pagarFatura(Long cartaoId) {
        Cartao cartao = cartaoRepository.findById(cartaoId)
                .orElseThrow(() -> new ResourceNotFoundException("Cartão não encontrado"));

        if (!"crédito".equalsIgnoreCase(cartao.getTipo())) {
            throw new IllegalArgumentException("Pagamento de fatura é permitido apenas para cartões de crédito.");
        }

        List<LancamentoFatura> lancamentos = lancamentoFaturaRepository.findByCartaoId(cartaoId);

        double totalFatura = lancamentos.stream()
                .filter(l -> Boolean.FALSE.equals(l.getPago()))
                .mapToDouble(LancamentoFatura::getValor)
                .sum();

        Conta conta = cartao.getConta();

        if (conta.getSaldo() < totalFatura) {
            throw new IllegalArgumentException("Saldo insuficiente para pagar a fatura.");
        }

        // Debita da conta
        conta.setSaldo(conta.getSaldo() - totalFatura);
        contaRepository.save(conta);

        // Marca os lançamentos como pagos
        lancamentos.stream()
                .filter(l -> Boolean.FALSE.equals(l.getPago()))
                .forEach(l -> {
                    l.setPago(true);
                    lancamentoFaturaRepository.save(l);
                });

        // Zera o valor da fatura acumulada no cartão
        cartao.setFatura(0.0);
        cartaoRepository.save(cartao);
    }
    public CartaoDTO alterarLimiteDiario(Long id, Double novoLimite) {
        Cartao cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cartão não encontrado"));

        if (!"Débito".equalsIgnoreCase(cartao.getTipo())) {
            throw new IllegalArgumentException("Apenas cartões de débito podem ter limite diário alterado.");
        }

        cartao.setLimiteDiario(novoLimite);
        return mapper.map(cartaoRepository.save(cartao), CartaoDTO.class);
    }


}
