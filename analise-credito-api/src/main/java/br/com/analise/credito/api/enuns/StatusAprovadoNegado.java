package br.com.analise.credito.api.enuns;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.util.StringUtils;

/**
 * Enum com possíveis valores para Status <b>Aprovado/Negado</b>
 * 
 * @author Leonardo Araújo
 */
public enum StatusAprovadoNegado {

	APROVADO(1L, "Aprovado"),
	NEGADO(2L, "Negado");

	private Long id;
	private String descricao;

	/**
	 * @param id
	 * @param descricao
	 */
	private StatusAprovadoNegado(Long id, String descricao) {
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
	 * Retorna o {@link StatusAprovadoNegado} segundo atributo <b>id</b>.
	 * 
	 * @param descricao
	 * @return {@link StatusAprovadoNegado}
	 */
	public static StatusAprovadoNegado getStatusPorId(final Long id) {
		if(Objects.isNull(id)) return null;

		return Arrays.asList(StatusAprovadoNegado.values()).stream().filter(o -> o.getId() == id).findFirst().orElse(null);
	}

	/**
	 * Retorna o {@link StatusAprovadoNegado} segundo atributo <b>descricao</b>
	 * 
	 * @param descricao
	 * @return {@link StatusAprovadoNegado}
	 */
	public static StatusAprovadoNegado getStatusPorDescricao(final String descricao) {
		if(StringUtils.isEmpty(descricao)) return null;

		return Arrays.asList(StatusAprovadoNegado.values()).stream().filter(o -> o.getDescricao().equalsIgnoreCase(descricao)).findFirst().orElse(null);
	}
}
