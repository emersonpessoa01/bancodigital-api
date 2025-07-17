package br.com.cdb.bancodigital_api.repository;

import br.com.cdb.bancodigital_api.model.LancamentoCartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LancamentoCartaoRepository extends JpaRepository<LancamentoCartao, Long> {
    List<LancamentoCartao> findByCartaoId(Long cartaoId);
}
