package br.com.analise.credito.api.facade;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.analise.credito.api.dto.ClienteDTO;
import br.com.analise.credito.api.exception.RestMessageCode;
import br.com.analise.credito.api.exception.RestResponseMessageException;

/**
 * Interface Facade que encapsula a lógica de negócio de consultas 
 * de {@link ClienteDTO} na API de cadastro
 * 
 * @author Leonardo Araújo
 */
@Component
public class ClienteFacade {
	private final static String uriCadastroCLiente = "http://localhost:8080/v1/clientes";
	
	private RestTemplate restTemplate = new RestTemplate();

	/**
	 * Busca {@link ClienteDTO} por CPF
	 * @param cpf
	 * @return
	 */
	public ClienteDTO buscarClientePorCpf(final String cpf) {
		try {
			StringBuilder URI = new StringBuilder();
			URI.append(uriCadastroCLiente);
			URI.append("/cpf/");
			URI.append(cpf);
	
			ResponseEntity<ClienteDTO> result = restTemplate.getForEntity(URI.toString(), ClienteDTO.class);
			return result.getBody();

		} catch (RestClientException e) {
			throw new RestResponseMessageException(RestMessageCode.ERRO_COMUNICACAO_COM_CADASTRO);
		}
	}

	/**
	 * Busca todos os {@link ClienteDTO} na API de cadastro
	 * @return
	 */
	public List<ClienteDTO> buscarTodos() {
		try {
			StringBuilder URI = new StringBuilder();
			URI.append(uriCadastroCLiente);
	
			ResponseEntity<ClienteDTO[]> result = restTemplate.getForEntity(URI.toString(), ClienteDTO[].class);
	
			return Arrays.asList(result.getBody());

		} catch (RestClientException e) {
			throw new RestResponseMessageException(RestMessageCode.ERRO_COMUNICACAO_COM_CADASTRO);
		}
	}
}