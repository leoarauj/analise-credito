package br.com.analise.credito.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe de representação(DTO) das entidades Cliente e AnaliseCredito
 * @author Leonardo Araújo
 */
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude(content = Include.NON_NULL)
public @Data class ResultadoAnaliseDTO {

	private ClienteDTO clienteDTO;
	private AnaliseCreditoDTO analiseCreditoDTO;
}
