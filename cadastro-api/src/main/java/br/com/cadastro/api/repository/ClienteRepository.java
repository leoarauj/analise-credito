package br.com.cadastro.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cadastro.api.model.Cliente;

/**
 * Interface de comunicação com o banco referente a entidade {@link Cliente}
 * @author Leonardo Araújo
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	Cliente findByCpf(final String cpf);
}
