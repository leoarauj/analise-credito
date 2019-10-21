package br.com.analise.credito.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.analise.credito.api.enuns.EstadoCivil;
import br.com.analise.credito.api.model.PesoEstadoCivil;

/**
 * Interface de comunicação com o banco referente a entidade {@link PesoEstadoCivil}
 * @author Leonardo Araújo
 */
@Repository
public interface PesoEstadoCivilRepository extends JpaRepository<PesoEstadoCivil, Long> {

	/**
	 * Busca a pontuação do Cliente na avaliação de risco por {@link PesoEstadoCivil}
	 * 
	 * @param estadoCivilCliente
	 * @return
	 */
	@Query(" SELECT peso.score FROM PesoEstadoCivil peso WHERE peso.estadoCivil = :estadoCivilCliente")
	public Double findScoreByEstadoCivil(@Param("estadoCivilCliente") final EstadoCivil estadoCivilCliente);
}
