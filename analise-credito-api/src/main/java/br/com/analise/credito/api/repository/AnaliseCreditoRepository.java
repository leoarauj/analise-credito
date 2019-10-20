package br.com.analise.credito.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.analise.credito.api.enuns.StatusAprovadoNegado;
import br.com.analise.credito.api.model.AnaliseCredito;

/**
 * Interface de comunicação com o banco referente a entidade {@link AnaliseCredito}
 * @author Leonardo Araújo
 */
@Repository
public interface AnaliseCreditoRepository extends JpaRepository<AnaliseCredito, Long> {

	/**
	 * Retorna uma lista de {@link AnaliseCredito} por {@link StatusAprovadoNegado}
	 * 
	 * @param status
	 * @return
	 */
	List<AnaliseCredito> findByStatus(final StatusAprovadoNegado status);

	/**
	 * Retorna a lista de {@link AnaliseCredito} associada ao cliente
	 * 
	 * @param idCliente
	 * @return
	 */
	List<AnaliseCredito> findByIdCliente(final Long idCliente);
}
