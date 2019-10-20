package br.com.analise.credito.api.exception;

import java.time.LocalDateTime;

import lombok.Getter;

/**
 * Classe de resposta para tratamento de exceptions seguindo o padrão {@link RestMessageCode}
 * @author Leonardo Araújo
 */
public @Getter class RestResponseMessageException extends RuntimeException {
	private static final long serialVersionUID = 5121137774654644380L;

	private int status;
	private String message;
	private LocalDateTime date;

	/**
	 * Construtor padrão
	 * @param restMessageCode
	 */
	public RestResponseMessageException(final RestMessageCode restMessageCode) {
		this.status = restMessageCode.getCodeStatus();
		this.message = restMessageCode.getMessage();
		this.date = LocalDateTime.now();
	}
}
