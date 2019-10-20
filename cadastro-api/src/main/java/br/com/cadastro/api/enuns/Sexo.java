package br.com.cadastro.api.enuns;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Enum com possíveis valores para definição de Gênero.
 * 
 * @author Leonardo Araújo
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Sexo {

	MASCULINO(1L, "Masculino", "M"),
	FEMININO(2L, "Feminino", "F");

	private Long id;
	private String descricao;
	private String sigla;

	/**
	 * Contrutor padrão.
	 * 
	 * @param id
	 * @param descricao
	 * @param sigla
	 */
	private Sexo (final Long id, final String descricao, final String sigla) {
		this.id = id;
		this.descricao = descricao;
		this.sigla = sigla;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the nome
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @return the sigla
	 */
	public String getSigla() {
		return sigla;
	}

	/**
	 * Retorna {@link Sexo} segundo descrição.
	 * 
	 * @param descricao
	 * @return {@link Sexo}
	 */
	public static Sexo getSexoPorDescricao(final String descricao) {
		if(StringUtils.isEmpty(descricao)) return null;

		return Arrays.asList(Sexo.values()).stream().filter(o -> o.getDescricao().equalsIgnoreCase(descricao)).findFirst().orElse(null);
	}

	/**
	 * Retorna {@link Sexo} segundo sigla.
	 * 
	 * @param sigla
	 * @return {@link Sexo}
	 */
	public static Sexo getSexoPorSigla(final String sigla) {
		if(StringUtils.isEmpty(sigla)) return null;

		return Arrays.asList(Sexo.values()).stream().filter(o -> o.getSigla().equalsIgnoreCase(sigla)).findFirst().orElse(null);
	}

	/**
	 * Retorna {@link Sexo} segundo id.
	 * 
	 * @param descricao
	 * @return {@link Sexo}
	 */
	public static Sexo getSexoPorId(final Long id) {
		if(Objects.isNull(id)) return null;

		return Arrays.asList(Sexo.values()).stream().filter(o -> o.getId() == id).findFirst().orElse(null);
	}
}
