package br.com.cdb.bancodigital_api.repository;

import br.com.cdb.bancodigital_api.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    // Aqui você pode adicionar métodos personalizados de consulta, se necessário

}
