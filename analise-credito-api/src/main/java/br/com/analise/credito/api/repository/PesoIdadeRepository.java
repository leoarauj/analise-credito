package br.com.analise.credito.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.analise.credito.api.model.PesoIdade;

/**
 * Interface de comunicação com o banco referente a entidade {@link PesoIdade}
 * @author Leonardo Araújo
 */
@Repository
public interface PesoIdadeRepository extends JpaRepository<PesoIdade, Long> {

	/**
	 * Busca a pontuação do Cliente na avaliação de risco por {@link PesoIdade}
	 * 
	 * @param idadeCliente
	 * @return
	 */
	@Query(" SELECT peso.score FROM PesoIdade peso WHERE :idadeCliente BETWEEN peso.classificacaoInicial AND peso.classificacaoFinal ")
	public Double findScoreByClassificacaoInicialAndFinalOfIdadeCliente(@Param("idadeCliente") final Integer idadeCliente);

	@Query(" SELECT min(peso.classificacaoInicial) FROM PesoIdade peso ")
	public int findIdadeMinima();
	
	@Query(" SELECT max(peso.classificacaoFinal) FROM PesoIdade peso ")
	public int findIdadeMaxima();
}
