package br.com.cadastro.api.service;

import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.cadastro.api.exception.RestMessageCode;
import br.com.cadastro.api.exception.RestResponseMessageException;
import br.com.cadastro.api.model.Cliente;
import br.com.cadastro.api.repository.ClienteRepository;
import ch.qos.logback.core.net.server.Client;

/**
 * Classe de implementação das regras de negócio referente a {@link Cliente}
 * 
 * @author Leonardo Araújo
 */
@Service
@Transactional(rollbackOn = RestResponseMessageException.class)
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	/**
	 * Realiza a validação e persistência de {@link Cliente}
	 * 
	 * @param cliente
	 * @return {@link Cliente}
	 */
	public Cliente salvar(final Cliente cliente) {
		validarCamposObrigatorios(cliente);

		return clienteRepository.save(cliente);
	}

	/**
	 * Realiza a validação de campos obrigatórios da entidade {@link Client}
	 * @param cliente
	 */
	private void validarCamposObrigatorios(final Cliente cliente) {
		if(Objects.isNull(cliente)
				|| Objects.isNull(cliente.getSexo())
				|| Objects.isNull(cliente.getEstadoCivil())
				|| Objects.isNull(cliente.getDependentes())
				|| Objects.isNull(cliente.getRenda())
				|| Objects.isNull(cliente.getIdade())
				|| StringUtils.isEmpty(cliente.getNome())
				|| StringUtils.isEmpty(cliente.getCpf())
				|| StringUtils.isEmpty(cliente.getUf())) {

			throw new RestResponseMessageException(RestMessageCode.CAMPOS_OBRIGATORIOS_NAO_INFORMADOS);
		}
	}
}
