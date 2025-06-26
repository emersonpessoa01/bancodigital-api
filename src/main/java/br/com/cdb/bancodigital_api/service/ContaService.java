package br.com.cdb.bancodigital_api.service;

import br.com.cdb.bancodigital_api.dto.ContaDTO;
import br.com.cdb.bancodigital_api.dto.TransferenciaDTO;
import br.com.cdb.bancodigital_api.exception.ResourceNotFoundException;
import br.com.cdb.bancodigital_api.model.Cliente;
import br.com.cdb.bancodigital_api.model.Conta;
import br.com.cdb.bancodigital_api.repository.ClienteRepository;
import br.com.cdb.bancodigital_api.repository.ContaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;
    private final ModelMapper mapper;

    public ContaService(ContaRepository contaRepository, ClienteRepository clienteRepository) {
        this.contaRepository = contaRepository;
        this.clienteRepository = clienteRepository;
        this.mapper = new ModelMapper();
    }

    public ContaDTO salvar(ContaDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        Conta conta = mapper.map(dto, Conta.class);
        conta.setCliente(cliente);
        return mapper.map(contaRepository.save(conta), ContaDTO.class);
    }

    public ContaDTO buscarPorId(Long id) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada"));
        return mapper.map(conta, ContaDTO.class);
    }

    public List<ContaDTO> listarTodas() {
        return contaRepository.findAll()
                .stream()
                .map(conta -> mapper.map(conta, ContaDTO.class))
                .collect(Collectors.toList());
    }

    public ContaDTO atualizar(Long id, ContaDTO dto) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada"));

        conta.setNumero(dto.getNumero());
        conta.setAgencia(dto.getAgencia());
        conta.setSaldo(dto.getSaldo());

        if (dto.getClienteId() != null && !dto.getClienteId().equals(conta.getCliente().getId())) {
            Cliente novoCliente = clienteRepository.findById(dto.getClienteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
            conta.setCliente(novoCliente);
        }

        return mapper.map(contaRepository.save(conta), ContaDTO.class);
    }

    public void deletar(Long id) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada"));
        contaRepository.delete(conta);
    }

    public Double consultarSaldo(Long contaId) {
        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada"));
        return conta.getSaldo();
    }

    public void transferir(Long contaOrigemId, TransferenciaDTO dto) {
        if (dto.getValor() == null || dto.getValor() <= 0) {
            throw new IllegalArgumentException("Valor inválido");
        }

        Conta origem = contaRepository.findById(contaOrigemId)
                .orElseThrow(() -> new ResourceNotFoundException("Conta de origem não encontrada"));

        Conta destino = contaRepository.findById(dto.getContaDestinoId())
                .orElseThrow(() -> new ResourceNotFoundException("Conta de destino não encontrada"));

        if (origem.getSaldo() < dto.getValor()) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }

        origem.setSaldo(origem.getSaldo() - dto.getValor());
        destino.setSaldo(destino.getSaldo() + dto.getValor());

        contaRepository.save(origem);
        contaRepository.save(destino);
    }
}
