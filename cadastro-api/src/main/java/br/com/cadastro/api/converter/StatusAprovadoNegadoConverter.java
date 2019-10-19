package br.com.cadastro.api.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.com.cadastro.api.enuns.StatusAprovadoNegado;

/**
 * Classe de conversão de {@link StatusAprovadoNegado} para String
 * segundo atributo <b>descricao</b>
 * 
 * @author Leonardo Araújo
 */
@Converter(autoApply = true)
public class StatusAprovadoNegadoConverter implements AttributeConverter<StatusAprovadoNegado, String> {

	/**
	 * Função que converte enum {@link StatusAprovadoNegado} em <b>descricao</b><sub>(String)</sub>
	 */
	@Override
	public String convertToDatabaseColumn(final StatusAprovadoNegado status) {
		return (status == null) ? null : status.getDescricao();
	}

	/**
	 * Retorna um enum de {@link StatusAprovadoNegado} segundo <b>descricao</b><sub>(String)</sub>
	 */
	@Override
	public StatusAprovadoNegado convertToEntityAttribute(final String descricao) {
		return (descricao == null) ? null : StatusAprovadoNegado.getStatusPorDescricao(descricao);
	}
}
