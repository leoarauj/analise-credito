package br.com.cadastro.api.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.com.cadastro.api.enuns.Sexo;


/**
 * Classe de conversão do enum {@link Sexo} para String
 * segundo atributo <b>sigla</b>
 * 
 * @author Leonardo Araújo
 */
@Converter(autoApply = true)
public class SexoConverter implements AttributeConverter<Sexo, String> {

	/**
	 * Função que converte enum {@link Sexo} em <b>sigla</b><sub>(String)</sub>
	 */
	@Override
	public String convertToDatabaseColumn(Sexo sexo) {
		return (sexo == null) ? null : sexo.getSigla();
	}

	/**
	 * Retorna um enum de {@link Sexo} segundo <b>sigla</b><sub>(String)</sub>
	 */
	@Override
	public Sexo convertToEntityAttribute(String sigla) {
		return (sigla == null) ? null : Sexo.getSexoPorSigla(sigla);
	}
}
