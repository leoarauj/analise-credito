package br.com.analise.credito.api.enuns;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Enum com possíveis valores para <b>Estado Civil</b>
 * 
 * @author Leonardo Araújo
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EstadoCivil {

	SOLTEIRO(1L, "Solteiro"),
	CASADO(2L, "Casado"),
	DIVORCIADO(3L, "Divorciado"),
	VIUVO(4L, "Viúvo");

	private Long id;
	private String descricao;

	/**
	 * Contrutor padrão.
	 * 
	 * @param id
	 * @param descricao
	 */
	private EstadoCivil(Long id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Retorna o {@link EstadoCivil} segundo atributo <b>id</b>.
	 * 
	 * @param descricao
	 * @return {@link EstadoCivil}
	 */
	public static EstadoCivil getEstadoCivilPorId(final Long id) {
		if(Objects.isNull(id)) return null;

		return Arrays.asList(EstadoCivil.values()).stream().filter(o -> o.getId() == id).findFirst().orElse(null);
	}

	/**
	 * Retorna o {@link EstadoCivil} segundo atributo <b>descricao</b>
	 * 
	 * @param descricao
	 * @return {@link EstadoCivil}
	 */
	public static EstadoCivil getEstadoCivilPorDescricao(final String descricao) {
		if(StringUtils.isEmpty(descricao)) return null;

		return Arrays.asList(EstadoCivil.values()).stream().filter(o -> o.getDescricao().equalsIgnoreCase(descricao)).findFirst().orElse(null);
	}
}
