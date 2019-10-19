package br.com.cadastro.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.cadastro.api.dto.ClienteDTO;
import br.com.cadastro.api.model.Cliente;

/**
 * Classe adapter referente a entidade {@link Cliente}.
 * 
 * @author Leonardo Araújo
 */
@Mapper(componentModel = "spring")
public interface ClienteMapper {

	/**
	 * Converte a entidade {@link Cliente} em DTO {@link ClienteDTO}
	 * 
	 * @param cliente
	 * @return
	 */
	@Mapping(target = "idSexo", source = "sexo.id")
	@Mapping(target = "idEstadoCivil", source = "estadoCivil.id")
	public ClienteDTO toDTO(final Cliente cliente);

	/**
	 * Converte o DTO {@link ClienteDTO} para entidade {@link Cliente}
	 * 
	 * @param clienteDTO
	 * @return
	 */
	@Mapping(target = "sexo", expression = "java(br.com.cadastro.api.enuns.Sexo.getSexoPorId(clienteDTO.getIdSexo()))")
	@Mapping(target = "estadoCivil", expression = "java(br.com.cadastro.api.enuns.EstadoCivil.getEstadoCivilPorId(clienteDTO.getIdEstadoCivil()))")
	public Cliente toEntity(final ClienteDTO clienteDTO);
}
