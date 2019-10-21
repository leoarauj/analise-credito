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

import br.com.analise.credito.api.converter.EstadoCivilConverter;
import br.com.analise.credito.api.enuns.EstadoCivil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe de representação da tabela <b>PESO_EST_CIVIL</b>
 * 
 * <dd>Responsável por especificar os valores de peso para pontuação por Estado Civil</dd>
 * 
 * @author Leonardo Araújo
 */
@Entity
@Table(name = "PESO_EST_CIVIL")
@EqualsAndHashCode
@NoArgsConstructor
@JsonInclude(content = Include.NON_NULL)
public @Data class PesoEstadoCivil implements Serializable {
	private static final long serialVersionUID = -5855592396952194824L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_PESO_EST_CIVIL")
	private Long id;

	@Convert(converter = EstadoCivilConverter.class)
	@Column(name = "ESTADO_CIVIL", nullable = false)
	private EstadoCivil estadoCivil;

	@Column(name = "SCORE", nullable = false)
	private Double score;
}
