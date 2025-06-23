package br.com.cdb.bancodigital_api.service;

import br.com.cdb.bancodigital_api.dto.ClienteDTO;
import br.com.cdb.bancodigital_api.exception.ResourceNotFoundException;
import br.com.cdb.bancodigital_api.model.Cliente;
import br.com.cdb.bancodigital_api.repository.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository repository;
    private final ModelMapper mapper;

    @Autowired
    public ClienteService(ClienteRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    public ClienteDTO salvar(ClienteDTO dto) {
        Cliente cliente = mapper.map(dto, Cliente.class);
        return mapper.map(repository.save(cliente), ClienteDTO.class);
    }

    public ClienteDTO buscarPorId(Long id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        return mapper.map(cliente, ClienteDTO.class);
    }

    public List<ClienteDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(cliente -> mapper.map(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
    }

    public ClienteDTO atualizar(Long id, ClienteDTO dto) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        cliente.setNome(dto.getNome());
        cliente.setCpf(dto.getCpf());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefone(dto.getTelefone());
        return mapper.map(repository.save(cliente), ClienteDTO.class);
    }

    public void deletar(Long id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        repository.delete(cliente);
    }
}
