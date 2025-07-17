package br.com.cdb.bancodigital_api.repository;

import br.com.cdb.bancodigital_api.model.LancamentoFatura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LancamentoFaturaRepository extends JpaRepository<LancamentoFatura, Long> {
    List<LancamentoFatura> findByCartaoId(Long cartaoId);
}

