package br.com.analise.credito.api.facade;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.com.analise.credito.api.dto.AnaliseCreditoDTO;
import br.com.analise.credito.api.dto.ClienteDTO;
import br.com.analise.credito.api.dto.ResultadoAnaliseDTO;
import br.com.analise.credito.api.mapper.AnaliseCreditoMapper;
import br.com.analise.credito.api.model.AnaliseCredito;
import br.com.analise.credito.api.repository.AnaliseCreditoRepository;

/**
 * Interface Facade que encapsula a logica de negócio de resultado de {@link ResultadoAnaliseDTO}
 * 
 * @author Leonardo Araújo
 */
@Component
public class ResultadoAnaliseFacade {

	@Autowired
	private AnaliseCreditoRepository analiseCreditoRepository;

	@Autowired
	private AnaliseCreditoMapper analiseCreditoMapper;

	@Autowired
	private ClienteFacade clienteFacade;


	/**
	 * Interface que encapsula a busca por {@link ResultadoAnaliseDTO}
	 * @return {@link List}
	 */
	public List<ResultadoAnaliseDTO> buscarTodasAnalisesCreditoVigentes() {
		List<ClienteDTO> clientesDTO = clienteFacade.buscarTodos();

		if(Objects.isNull(clientesDTO) || clientesDTO.isEmpty()) {
			return null;
		}

		List<Long> idsCliente = clientesDTO.stream().map(o -> o.getId()).collect(Collectors.toList());

		List<AnaliseCredito> collectAnalise = analiseCreditoRepository.findAnaliseCreditoByIdClienteAndMaxDateDataAnalise(idsCliente);

		if(Objects.isNull(collectAnalise) || collectAnalise.isEmpty()) {
			return null;
		}

		return collectAnalise.stream().map(o -> {
			AnaliseCreditoDTO analiseCreditoDTO = analiseCreditoMapper.toDTO(o);
			ResultadoAnaliseDTO resultado = new ResultadoAnaliseDTO();

			for(ClienteDTO dto : clientesDTO) {
				if(dto.getId() == o.getIdCliente()) {
					resultado.setClienteDTO(dto);
					resultado.setAnaliseCreditoDTO(analiseCreditoDTO);
				}
			}

			return resultado;
		}).collect(Collectors.toList());
	}

	/**
	 * Retorna o resultado da análise de crédito segundo Cpf do Cliente informado
	 * 
	 * @param cpf
	 * @return
	 */
	public ResultadoAnaliseDTO buscarResultadoAnalisePorCpfCliente(final String cpf) {
		if(StringUtils.isEmpty(cpf)) return null;

		ClienteDTO clienteDTO = clienteFacade.buscarClientePorCpf(cpf);

		if(Objects.isNull(clienteDTO)) return null;

		AnaliseCredito analiseCredito = analiseCreditoRepository.findByIdClienteMaiorData(clienteDTO.getId());

		if(Objects.isNull(analiseCredito)) return null;

		AnaliseCreditoDTO analiseCreditoDTO = analiseCreditoMapper.toDTO(analiseCredito);

		ResultadoAnaliseDTO resultado = new ResultadoAnaliseDTO();
		resultado.setClienteDTO(clienteDTO);
		resultado.setAnaliseCreditoDTO(analiseCreditoDTO);

		return resultado;
	}
}
