package br.com.analise.credito.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.analise.credito.api.converter.RiscoConverter;
import br.com.analise.credito.api.enuns.Risco;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe de representação da tabela <b>ANALISE_RISCO</b>
 * 
 * <dd>Responsável por especificar os valores para a classificação de risco</dd>
 * 
 * @author Leonardo Araújo
 */
@Entity
@Table(name = "ANALISE_RISCO")
@EqualsAndHashCode
@NoArgsConstructor
@JsonInclude(content = Include.NON_NULL)
public @Data class AnaliseRisco implements Serializable {
	private static final long serialVersionUID = 5153450213703670366L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_ANALISE_RISCO")
	private Long id;

	@Column(name = "SCORE_MIN", nullable = false)
	private Double score_min;
	
	@Column(name = "SCORE_MAX", nullable = false)
	private Double score_max;

	@Convert(converter = RiscoConverter.class)
	@Column(name = "RISCO", nullable = false)
	private Risco risco;

	@Column(name = "RENDA_MIN", nullable = false)
	private Double rendaMinima;

	@Column(name = "LIMITE_MIN", nullable = false)
	private Double limiteMin;

	@Column(name = "LIMITE_MAX", nullable = false)
	private Double limiteMax;
}
