package br.com.cdb.bancodigital_api.repository;

import br.com.cdb.bancodigital_api.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {
}
