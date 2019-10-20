package br.com.cadastro.api.service;

import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.cadastro.api.exception.RestMessageCode;
import br.com.cadastro.api.exception.RestResponseMessageException;
import br.com.cadastro.api.model.Cliente;
import br.com.cadastro.api.repository.ClienteRepository;
import br.com.cadastro.api.utils.CpfUtil;
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
		validarCpf(cliente.getCpf());

		String cpfFormatado = formatarCpf(cliente.getCpf());
		cliente.setCpf(cpfFormatado);

		if(Objects.isNull(cliente.getId())) {
			validarDuplicidadeCpf(cliente.getCpf());
		}

		return clienteRepository.save(cliente);
	}

	/**
	 * Retorna uma lista de todos os {@link Cliente}
	 * @return {@link List}
	 */
	public List<Cliente> buscarTodos() {
		return clienteRepository.findAll();
	}

	/**
	 * Busca {@link Cliente} segundo o atributo <b>CPF</b>
	 * 
	 * @param cpf
	 * @return {@link Cliente}
	 */
	public Cliente buscarPorCpf(final String cpf) {
		validarCpf(cpf);
		String cpfFormatado = formatarCpf(cpf);

		return clienteRepository.findByCpf(cpfFormatado);
	}

	/**
	 * Realiza a validação dos digitos de CPF
	 * @param cpf
	 */
	private void validarCpf(final String cpf) {
		if(StringUtils.isEmpty(cpf)) {
			throw new RestResponseMessageException(RestMessageCode.CAMPOS_OBRIGATORIOS_NAO_INFORMADOS);
		}

		if(!CpfUtil.isValid(cpf)) {
			throw new RestResponseMessageException(RestMessageCode.CPF_INVALIDO);
		}
	}

	/**
	 * Realiza a validação de duplicidade de <b>CPF</b>
	 * 
	 * @param cpf
	 */
	private void validarDuplicidadeCpf(final String cpf) {
		if(clienteRepository.countByCpf(cpf) > 0) {
			throw new RestResponseMessageException(RestMessageCode.DUPLICIDADE_CPF);
		}
	}

	/**
	 * Realiza a formatação padrão do <b>CPF</b> de {@link Cliente}
	 * <dd><i> Ex: 000.000.000-00</i></dd>
	 * 
	 * @param cpf
	 * @return {@link String}
	 */
	private String formatarCpf(final String cpf) {
		return CpfUtil.formatarCpf(cpf);
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
