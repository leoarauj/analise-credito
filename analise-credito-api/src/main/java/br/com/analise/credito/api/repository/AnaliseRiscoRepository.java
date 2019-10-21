package br.com.analise.credito.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.analise.credito.api.enuns.Risco;
import br.com.analise.credito.api.model.AnaliseRisco;

/**
 * Interface de comunicação com o banco referente a entidade {@link AnaliseRisco}
 * @author Leonardo Araújo
 */
@Repository
public interface AnaliseRiscoRepository extends JpaRepository<AnaliseRisco, Long> {

	/**
	 * Busca a {@link AnaliseRisco} por {@link Risco}
	 * 
	 * @param risco
	 * @return
	 */
	public AnaliseRisco findByRisco(final Risco risco);

	/**
	 * Busca a {@link AnaliseRisco} por Score
	 * 
	 * @param score
	 * @return
	 */
	@Query(" SELECT risco FROM AnaliseRisco risco "
			+ " WHERE :score BETWEEN risco.score_min AND risco.score_max ")
	public AnaliseRisco findByAnaliseRiscoByScore(@Param("score") final Double score);
}
