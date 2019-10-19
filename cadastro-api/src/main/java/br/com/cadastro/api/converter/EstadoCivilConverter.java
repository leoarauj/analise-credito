package br.com.cadastro.api.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.com.cadastro.api.enuns.EstadoCivil;

/**
 * Classe de conversão de {@link EstadoCivil} para String
 * segundo atributo <b>descricao</b>
 * 
 * @author Leonardo Araújo
 */
@Converter(autoApply = true)
public class EstadoCivilConverter implements AttributeConverter<EstadoCivil, String> {

	/**
	 * Função que converte enum {@link EstadoCivil} em <b>descricao</b><sub>(String)</sub>
	 */
	@Override
	public String convertToDatabaseColumn(final EstadoCivil estadoCivil) {
		return (estadoCivil == null) ? null : estadoCivil.getDescricao();
	}

	/**
	 * Retorna um enum de {@link EstadoCivil} segundo <b>descricao</b><sub>(String)</sub>
	 */
	@Override
	public EstadoCivil convertToEntityAttribute(final String descricao) {
		return (descricao == null) ? null : EstadoCivil.getEstadoCivilPorDescricao(descricao);
	}

}
