package br.com.cadastro.api.exception;

/**
 * Enum com os StatusHttp e possivéis respostas do servidor
 * @author Leonardo Araújo
 */
public enum RestMessageCode {

	CAMPOS_OBRIGATORIOS_NAO_INFORMADOS(400, "Campos obrigatórios não informados"),
	NENHUM_RESULTADO_ENCONTRADO(404, "Nenhum resultado encontrado"),
	CPF_INVALIDO(400, "CPF inválido"),
	DUPLICIDADE_CPF(409, "CPF já cadastrado");

	private int status;
	private String message;

	private RestMessageCode(int status, String message) {
		this.status = status;
		this.message = message;
	}

	/**
	 * @return the codeStatus
	 */
	public int getCodeStatus() {
		return status;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
}
