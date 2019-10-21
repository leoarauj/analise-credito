package br.com.analise.credito.api.enuns;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.analise.credito.api.model.AnaliseRisco;

/**
 * Enum com possíveis valores para categorizar {@link AnaliseRisco}
 * 
 * @author Leonardo Araújo
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Risco {

	BAIXO(1L, "Baixo"),
	MEDIO(2L, "Médio"),
	ALTO(3L, "Alto");

	private Long id;
	private String descricao;

	/**
	 * @param id
	 * @param descricao
	 */
	private Risco(Long id, String descricao) {
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
	 * Retorna o {@link Risco} segundo atributo <b>id</b>.
	 * 
	 * @param id
	 * @return {@link Risco}
	 */
	public static Risco getRiscoPorId(final Long id) {
		if(Objects.isNull(id)) return null;

		return Arrays.asList(Risco.values()).stream().filter(o -> o.getId() == id).findFirst().orElse(null);
	}

	/**
	 * Retorna o {@link Risco} segundo atributo <b>descricao</b>
	 * 
	 * @param descricao
	 * @return {@link Risco}
	 */
	public static Risco getRiscoPorDescricao(final String descricao) {
		if(StringUtils.isEmpty(descricao)) return null;

		return Arrays.asList(Risco.values()).stream().filter(o -> o.getDescricao().equalsIgnoreCase(descricao)).findFirst().orElse(null);
	}
}
