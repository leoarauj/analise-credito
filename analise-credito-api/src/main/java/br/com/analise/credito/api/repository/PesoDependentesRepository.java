package br.com.analise.credito.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.analise.credito.api.model.PesoDependentes;

/**
 * Interface de comunicação com o banco referente a entidade {@link PesoDependentes}
 * @author Leonardo Araújo
 */
@Repository
public interface PesoDependentesRepository extends JpaRepository<PesoDependentes, Long> {

	/**
	 * Busca a pontuação do Cliente na valiação de risco por {@link PesoDependentes}
	 * 
	 * @param dependentes
	 * @return
	 */
	@Query(" SELECT peso.score FROM PesoDependentes peso "
			+ " WHERE :dependentes BETWEEN peso.classificacaoInicial AND peso.classificacaoFinal ")
	public Double findScoreByClassificacaoInicialAndFinalOfDependentes(@Param("dependentes") final Integer dependentes);
}
