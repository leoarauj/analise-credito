package br.com.analise.credito.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
	public List<AnaliseCredito> findByStatus(final StatusAprovadoNegado status);

	/**
	 * Retorna a lista de {@link AnaliseCredito} associada ao cliente
	 * 
	 * @param idCliente
	 * @return
	 */
	public List<AnaliseCredito> findByIdCliente(final Long idCliente);

	/**
	 * Busca {@link AnaliseCredito} por idCliente e com data de análise mais recente
	 * 
	 * @param idCliente
	 * @return
	 */
	@Query(" SELECT analise FROM AnaliseCredito analise "
			+ " WHERE analise.idCliente = :idCliente "
			+ " AND analise.dataAnalise = ( SELECT MAX(dataAnalise) FROM AnaliseCredito analise_2 WHERE analise_2.idCliente = :idCliente) ")
	public AnaliseCredito findByIdClienteMaiorData(@Param("idCliente") final Long idCliente);

	/**
	 * Busca a lista de {@link AnaliseCredito} segundo lista de idsClientes e com data de análise mais recente
	 * 
	 * @param idsCliente
	 * @return
	 */
	@Query(" SELECT analise FROM AnaliseCredito analise "
			+ " WHERE analise.idCliente IN (:idsCliente) "
			+ " AND analise.dataAnalise = ( SELECT MAX(dataAnalise) FROM AnaliseCredito analise_2 WHERE analise_2.idCliente = analise.idCliente) ")
	public List<AnaliseCredito> findAnaliseCreditoByIdClienteAndMaxDateDataAnalise(@Param("idsCliente") final List<Long> idsCliente);
}
