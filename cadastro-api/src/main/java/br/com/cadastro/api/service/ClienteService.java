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
import br.com.cadastro.api.utils.CpfUltil;
import ch.qos.logback.core.net.server.Client;

/**
 * Classe de implementação das regras de negócio referente a {@link Cliente}
 * 
 * @author Leonardo Araújo
 */
@Service
@Transactional
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
		formatarCpf(cliente);

		if(Objects.isNull(cliente.getId())) {
			validarDuplicidadeCpf(cliente.getCpf());
		}

		return clienteRepository.save(cliente);
	}

	/**
	 * Busca {@link Cliente} segundo o atributo <b>CPF</b>
	 * 
	 * @param cpf
	 * @return
	 */
	public Cliente buscarPorCpf(final String cpf) {
		if(StringUtils.isEmpty(cpf)) {
			throw new RestResponseMessageException(RestMessageCode.CAMPOS_OBRIGATORIOS_NAO_INFORMADOS);
		}

		return clienteRepository.findByCpf(cpf);
	}

	/**
	 * Realiza a validação dos digitos de CPF
	 * @param cpf
	 */
	private void validarCpf(final String cpf) {
		if(!CpfUltil.isValid(cpf)) {
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
	 * @return
	 */
	private void formatarCpf(final Cliente cliente) {
		String cpfFormatado = CpfUltil.formatarCpf(cliente.getCpf());
		cliente.setCpf(cpfFormatado);
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
