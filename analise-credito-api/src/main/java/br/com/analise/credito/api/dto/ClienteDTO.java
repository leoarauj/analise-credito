package br.com.analise.credito.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe de representação(DTO) da entidade Cliente
 * @author Leonardo Araújo
 */
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude(content = Include.NON_NULL)
public @Data class ClienteDTO {
	private Long id;
	private String nome;
	private String cpf;
	private Integer idade;
	private Long idSexo;
	private Long idEstadoCivil;
	private String uf;
	private Integer dependentes;
	private Double renda;

}
