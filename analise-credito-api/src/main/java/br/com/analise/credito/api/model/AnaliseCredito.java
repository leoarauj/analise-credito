package br.com.analise.credito.api.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.analise.credito.api.converter.StatusAprovadoNegadoConverter;
import br.com.analise.credito.api.enuns.StatusAprovadoNegado;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe de representação da tabela <b>Análise Crédito</b>
 * 
 * @author Leonardo Araújo
 */
@Entity
@Table(name = "ANALISE_CRED")
@EqualsAndHashCode
@NoArgsConstructor
@JsonInclude(content = Include.NON_NULL)
public @Data class AnaliseCredito implements Serializable {
	private static final long serialVersionUID = 4551218502752617555L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_ANALISE_CRED")
	private Long id;

	@NotNull
	@Convert(converter = StatusAprovadoNegadoConverter.class)
	@Column(name = "RES_ANALISE", nullable = false)
	private StatusAprovadoNegado resultado;

	@Min(value = 0)
	@Column(name = "LIMITE_MIN")
	private Double limiteMin;

	@Min(value = 0)
	@Column(name = "LIMITE_MAX")
	private Double limiteMax;

	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	@Column(name = "DATA_ANALISE", nullable = false)
	private LocalDateTime dataAnalise;

	@NotNull
	@Column(name = "TOTAL_PONTOS", nullable = false)
	private Long pontuacao;

	@NotNull
	@Column(name = "FK_CLIENTE", nullable = false)
	private Long idCliente;
}
