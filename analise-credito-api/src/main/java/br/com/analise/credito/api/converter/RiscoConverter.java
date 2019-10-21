package br.com.analise.credito.api.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.com.analise.credito.api.enuns.Risco;

/**
 * Classe de conversão de {@link Risco} para String
 * segundo atributo <b>descricao</b>
 * 
 * @author Leonardo Araújo
 */
@Converter(autoApply = true)
public class RiscoConverter implements AttributeConverter<Risco, String>  {

	/**
	 * Função que converte enum {@link Risco} em <b>descricao</b><sub>(String)</sub>
	 */
	@Override
	public String convertToDatabaseColumn(final Risco status) {
		return (status == null) ? null : status.getDescricao();
	}

	/**
	 * Retorna um enum de {@link Risco} segundo <b>descricao</b><sub>(String)</sub>
	 */
	@Override
	public Risco convertToEntityAttribute(final String descricao) {
		return (descricao == null) ? null : Risco.getRiscoPorDescricao(descricao);
	}
}
