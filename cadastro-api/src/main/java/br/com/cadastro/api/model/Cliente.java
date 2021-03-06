package br.com.cadastro.api.model;

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

import br.com.cadastro.api.converter.EstadoCivilConverter;
import br.com.cadastro.api.converter.SexoConverter;
import br.com.cadastro.api.enuns.EstadoCivil;
import br.com.cadastro.api.enuns.Sexo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe de representação da tabela <b>Cliente</b>
 * 
 * @author Leonardo Araújo
 */
@Entity
@Table(name = "CLIENTE")
@EqualsAndHashCode
@NoArgsConstructor
@JsonInclude(content = Include.NON_NULL)
public @Data class Cliente implements Serializable {
	private static final long serialVersionUID = -6218694485035460336L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_CLIENTE")
	private Long id;

	@Column(name = "NOME", nullable = false, length = 100)
	private String nome;

	@Column(name = "CPF", nullable = false, unique = true, length = 14)
	private String cpf;

	@Column(name = "IDADE", nullable = false)
	private Integer idade;

	@Convert(converter = SexoConverter.class)
	@Column(name = "SEXO", nullable = false, length = 15)
	private Sexo sexo;

	@Convert(converter = EstadoCivilConverter.class)
	@Column(name = "ESTADO_CIVIL", nullable = false)
	private EstadoCivil estadoCivil;

	@Column(name = "UF", nullable = false)
	private String uf;

	@Column(name = "DEPENDENTES", nullable = false)
	private Integer dependentes;

	@Column(name = "RENDA", scale = 2, precision = 2, nullable = false)
	private Double renda;

}