package br.com.analise.credito.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe de representação da tabela <b>PESO_DEPENDENTES</b>
 * 
 * <dd>Responsável por especificar os valores de peso para pontuação por Dependentes</dd>
 * 
 * @author Leonardo Araújo
 */
@Entity
@Table(name = "PESO_DEPENDENTES")
@EqualsAndHashCode
@NoArgsConstructor
@JsonInclude(content = Include.NON_NULL)
public @Data class PesoDependentes implements Serializable {
	private static final long serialVersionUID = 4531795945647572382L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_PESO_DEPENDENTES")
	private Long id;

	@Column(name = "CLASS_INICIAL", nullable = false)
	private Integer classificacaoInicial;

	@Column(name = "CLASS_FINAL", nullable = false)
	private Integer classificacaoFinal;

	@Column(name = "SCORE", nullable = false)
	private Double score;

}
