package br.com.analise.credito.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.analise.credito.api.dto.AnaliseCreditoDTO;
import br.com.analise.credito.api.model.AnaliseCredito;

/**
 * Classe adapter referente a entidade {@link AnaliseCredito}.
 * 
 * @author Leonardo Araújo
 */
@Mapper(componentModel = "spring")
public interface AnaliseCreditoMapper {

	/**
	 * Converte a entidade {@link AnaliseCredito} em DTO {@link AnaliseCreditoDTO}
	 * 
	 * @param analiseCredito
	 * @return
	 */
	@Mapping(target = "idStatus", source = "status.id")
	public AnaliseCreditoDTO toDTO(final AnaliseCredito analiseCredito);

	/**
	 * Converte o DTO {@link AnaliseCreditoDTO} para entidade {@link AnaliseCredito}
	 * 
	 * @param analiseCreditoDTO
	 * @return
	 */
	@Mapping(target = "status", expression = "java(br.com.analise.credito.api.enuns.StatusAprovadoNegado.getStatusPorId(analiseCreditoDTO.getIdStatus()))")
	public AnaliseCredito toEntity(final AnaliseCreditoDTO analiseCreditoDTO);
}
