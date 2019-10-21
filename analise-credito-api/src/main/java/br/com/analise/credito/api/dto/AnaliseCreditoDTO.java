package br.com.analise.credito.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.analise.credito.api.model.AnaliseCredito;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe de representação(DTO) da entidade {@link AnaliseCredito}
 * @author Leonardo Araújo
 */
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude(content = Include.NON_NULL)
public @Data class AnaliseCreditoDTO {
	private Long id;
	private Long idStatus;
	private String motivo;
	private Double limiteMin;
	private Double limiteMax;
	private Long idCliente;
}
