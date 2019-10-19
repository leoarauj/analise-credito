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

	/**
	 * Busca {@link Cliente} por atributo CPF
	 * @param cpf
	 * @return
	 */
	Cliente findByCpf(final String cpf);

	/**
	 * Realiza a contagem de CPF's repetidos para validação de duplicidade
	 * 
	 * @param cpf
	 * @return
	 */
	Long countByCpf(final String cpf);
}
