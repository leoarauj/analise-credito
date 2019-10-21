package br.com.analise.credito.api.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.persistence.NonUniqueResultException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.analise.credito.api.dto.AnaliseCreditoDTO;
import br.com.analise.credito.api.dto.ClienteDTO;
import br.com.analise.credito.api.dto.ResultadoAnaliseDTO;
import br.com.analise.credito.api.enuns.EstadoCivil;
import br.com.analise.credito.api.enuns.Risco;
import br.com.analise.credito.api.enuns.StatusAprovadoNegado;
import br.com.analise.credito.api.exception.RestMessageCode;
import br.com.analise.credito.api.exception.RestResponseMessageException;
import br.com.analise.credito.api.facade.ResultadoAnaliseFacade;
import br.com.analise.credito.api.model.AnaliseCredito;
import br.com.analise.credito.api.model.AnaliseRisco;
import br.com.analise.credito.api.model.PesoDependentes;
import br.com.analise.credito.api.model.PesoEstadoCivil;
import br.com.analise.credito.api.model.PesoIdade;
import br.com.analise.credito.api.repository.AnaliseCreditoRepository;
import br.com.analise.credito.api.repository.AnaliseRiscoRepository;
import br.com.analise.credito.api.repository.PesoDependentesRepository;
import br.com.analise.credito.api.repository.PesoEstadoCivilRepository;
import br.com.analise.credito.api.repository.PesoIdadeRepository;

/**
 * Classe de implementação das regras de negócio referente a {@link AnaliseCredito}
 * 
 * @author Leonardo Araújo
 */
@Service
@Transactional(rollbackOn = RestResponseMessageException.class)
public class AnaliseCreditoService {
	private final Double limiteMin = 0.2;
	private final Double limiteMax = 0.4;

	@Autowired
	private AnaliseCreditoRepository analiseCreditoRepository;

	@Autowired
	private AnaliseRiscoRepository analiseRiscoRepository;

	@Autowired
	private PesoIdadeRepository pesoIdadeRepository;

	@Autowired
	private PesoEstadoCivilRepository pesoEstadoCivilRepository;

	@Autowired
	private PesoDependentesRepository pesoDependentesRepository;

	@Autowired
	private ResultadoAnaliseFacade resultadoAnaliseFacade;


	/**
	 * Função responsável por encapsular o processo de tomada de decisão da análise de crédito
	 * 
	 * @param clienteDTO
	 * @return
	 */
	public AnaliseCredito avaliarProposta(final ClienteDTO clienteDTO) {
		validarCamposObrigatorios(clienteDTO);

		if(avaliarRendaMinimaCliente(clienteDTO.getRenda())) {
			return negarPorRendaBaixa(clienteDTO);
		}

		if(avaliarPorIdadeMinima(clienteDTO) || avaliarPorIdadeMaxima(clienteDTO)) {
			return negarPorAltoRisco(clienteDTO, 0.0);
		}

		Double totalScore = calcularScoreTotal(clienteDTO)	;

		if(avaliarRiscoPorScore(totalScore)) {
			return negarPorAltoRisco(clienteDTO, totalScore);
		}

		return aprovarProposta(clienteDTO, totalScore);
	}

	/**
	 * Serviço que retorna uma lista com os dados de {@link ClienteDTO} e
	 * {@link AnaliseCreditoDTO} que já foram avaliados
	 * 
	 * @return
	 */
	public List<ResultadoAnaliseDTO> buscarTodosResultadosAnalise() {
		return resultadoAnaliseFacade.buscarTodasAnalisesCreditoVigentes();
	}

	/**
	 * Retorna o resultado da análise de crédito segundo Cpf do Cliente informado
	 * 
	 * @param cpf
	 * @return
	 */
	public ResultadoAnaliseDTO buscarResultadoAnalisePorCpfCliente(final String cpf) {
		if(StringUtils.isEmpty(cpf)) {
			throw new RestResponseMessageException(RestMessageCode.CAMPOS_OBRIGATORIOS_NAO_INFORMADOS);
		}

		return resultadoAnaliseFacade.buscarResultadoAnalisePorCpfCliente(cpf);
	}

	/**
	 * Retorna uma lista de possíveis valores para {@link StatusAprovadoNegado}
	 * @return
	 */
	public List<StatusAprovadoNegado> getStatusAprovadoNegado() {
		return Arrays.asList(StatusAprovadoNegado.values());
	}

	/**
	 * Realiza a aprovação da proposta de Crédito
	 * 
	 * @param clienteDTO
	 * @param totalScore
	 * @return
	 */
	private AnaliseCredito aprovarProposta(final ClienteDTO clienteDTO, final double totalScore) {
		AnaliseCredito analiseCredito = calcularLimite(clienteDTO);

		analiseCredito.setIdCliente(clienteDTO.getId());
		analiseCredito.setScore(totalScore);
		analiseCredito.setStatus(StatusAprovadoNegado.APROVADO);
		analiseCredito.setDataAnalise(LocalDateTime.now());

		return analiseCreditoRepository.save(analiseCredito);
	}

	/**
	 * Calcula o limite <b>mínimo e máximo</b> liberado na análise de crédito
	 * 
	 * <dd> Formula:
	 * <br>
	 * LIMITE MÁXIMO = (RENDA - (RENDA * (DEPENDENTES * 5%)) - 40%
	 * <br>
	 * LIMITE MÍNIMO = (RENDA - (RENDA * (DEPENDENTES * 5%)) - 20%
	 * 
	 * <br>
	 * <br>
	 * Dedução de 5% para cada dependente
	 * </dd>
	 * 
	 * 
	 * @param clienteDTO
	 * @return
	 */
	private AnaliseCredito calcularLimite(final ClienteDTO clienteDTO) {
		Double renda = clienteDTO.getRenda();

		double porcentagemDeducao = (clienteDTO.getDependentes() * 5) / 100.0;

		double rendaDedusida = renda - (renda * porcentagemDeducao);

		double limiteMin = calcularLimiteMin(rendaDedusida);
		double limiteMax = calcularLimiteMax(rendaDedusida);

		AnaliseCredito analiseCredito = new AnaliseCredito();

		analiseCredito.setDataAnalise(LocalDateTime.now());
		analiseCredito.setLimiteMin(limiteMin);
		analiseCredito.setLimiteMax(limiteMax);

		return analiseCredito;
	}

	/**
	 * Valida se o Cliente possuí idade mínima para enviar proposta de crédito
	 * 
	 * @param clienteDTO
	 * @return
	 */
	private boolean avaliarPorIdadeMinima(final ClienteDTO clienteDTO) {
		return clienteDTO.getIdade() < pesoIdadeRepository.findIdadeMinima();
	}

	/**
	 * Valida se o Cliente não ultrapassa a idade máxima para enviar proposta de crédito
	 * 
	 * @param clienteDTO
	 * @return
	 */
	private boolean avaliarPorIdadeMaxima(final ClienteDTO clienteDTO) {
		return clienteDTO.getIdade() > pesoIdadeRepository.findIdadeMaxima();
	}

	/**
	 * Calcula o limite <b>mínimo</b> liberado na análise de crédito
	 * 
	 * @param renda
	 * @return
	 */
	private double calcularLimiteMin(final double renda) {
		return renda * this.limiteMin;
	}

	/**
	 * Calcula o limite <b>máximo</b> liberado na análise de crédito
	 * @param renda
	 * @return
	 */
	private double calcularLimiteMax(final double renda) {
		return renda * this.limiteMax;
	}

	/**
	 * Função responsável por avaliar o {@link Risco} por Score 
	 * 
	 * @param score
	 * @return
	 */
	private boolean avaliarRiscoPorScore(final Double score) {
		try {
			AnaliseRisco analiseRisco = analiseRiscoRepository.findByAnaliseRiscoByScore(score);

			return analiseRisco.getRisco().equals(Risco.ALTO);
		} catch (NonUniqueResultException e) {
			throw new RestResponseMessageException(RestMessageCode.ERRO_RESULTADO_NAO_UNICO);
		}
	}

	/**
	 * Função responsável por verificar o <b>score</b> do {@link PesoIdade} 
	 * 
	 * @param clienteDTO
	 * @return Score
	 */
	private Double avaliarScoreIdade(final int idade) {
		try {
			return pesoIdadeRepository.findScoreByClassificacaoInicialAndFinalOfIdadeCliente(idade);
		} catch (NonUniqueResultException e) {
			throw new RestResponseMessageException(RestMessageCode.ERRO_RESULTADO_NAO_UNICO);
		}
	}

	/**
	 * Função responsável por verificar o <b>score</b> do {@link PesoEstadoCivil} 
	 * 
	 * @param clienteDTO
	 * @return Score
	 */
	private Double avaliarScoreEstadoCivil(final Long idEstadoCivil) {
		EstadoCivil estadoCivil = EstadoCivil.getEstadoCivilPorId(idEstadoCivil);

		try {
			return pesoEstadoCivilRepository.findScoreByEstadoCivil(estadoCivil);
		} catch (NonUniqueResultException e) {
			throw new RestResponseMessageException(RestMessageCode.ERRO_RESULTADO_NAO_UNICO);
		}
	}

	/**
	 * Função responsável por verificar o <b>score</b> do {@link PesoDependentes} 
	 * 
	 * @param depententes
	 * @return
	 */
	private Double avaliarScoreDependentes(final int depententes) {
		try {
			return pesoDependentesRepository.findScoreByClassificacaoInicialAndFinalOfDependentes(depententes);
		} catch (NonUniqueResultException e) {
			throw new RestResponseMessageException(RestMessageCode.ERRO_RESULTADO_NAO_UNICO);
		}
	}

	/**
	 * Realiza o cálculo da contagem de pontos seguindo a formula:
	 * 
	 * <dd><i>totalScore = (scoreIdade + scoreEstadoCivil + scoreDependentes) / 3</i></dd>
	 * 
	 * @param clienteDTO
	 * @return
	 */
	private Double calcularScoreTotal(final ClienteDTO clienteDTO) {
		Double scoreIdade = avaliarScoreIdade(clienteDTO.getIdade());
		Double scoreEstadoCivil = avaliarScoreEstadoCivil(clienteDTO.getIdEstadoCivil());
		Double scoreDependentes = avaliarScoreDependentes(clienteDTO.getDependentes());
		Double total = (scoreIdade + scoreEstadoCivil + scoreDependentes) / 3;

		return Math.ceil(total);
	}

	/**
	 * Realiza a avaliação da proposta de crédito com base na renda, 
	 * caso estejá dentro valor mínimo da classificação de {@link Risco} <b>MEDIO</b>
	 * 
	 * @param renda
	 * @return
	 */
	private boolean avaliarRendaMinimaCliente(final Double renda) {
		try {
			AnaliseRisco analiseRisco = analiseRiscoRepository.findByRisco(Risco.MEDIO);
			return renda < analiseRisco.getRendaMinima();
		} catch (NonUniqueResultException e) {
			throw new RestResponseMessageException(RestMessageCode.ERRO_RESULTADO_NAO_UNICO);
		}
	}

	/**
	 * Em caso de baixa pontuação da análise de crédito a proposta de crédito é negada
	 * 
	 * @param clienteDTO
	 * @param score
	 * @return
	 */
	private AnaliseCredito negarPorAltoRisco(final ClienteDTO clienteDTO, final Double score) {
		AnaliseCredito analiseCreditoNegada = new AnaliseCredito();

		analiseCreditoNegada.setIdCliente(clienteDTO.getId());
		analiseCreditoNegada.setMotivo("Reprovado pela política de crédito");
		analiseCreditoNegada.setLimiteMax(0.0);
		analiseCreditoNegada.setLimiteMin(0.0);
		analiseCreditoNegada.setStatus(StatusAprovadoNegado.NEGADO);
		analiseCreditoNegada.setDataAnalise(LocalDateTime.now());
		analiseCreditoNegada.setScore(score);

		return analiseCreditoRepository.save(analiseCreditoNegada);
	}

	/**
	 * Em caso de renda abaixo do valor mínimo a proposta de crédito é negada
	 * 
	 * @param clienteDTO
	 * @return
	 */
	private AnaliseCredito negarPorRendaBaixa(final ClienteDTO clienteDTO) {
		AnaliseCredito analiseCreditoNegada = new AnaliseCredito();

		analiseCreditoNegada.setIdCliente(clienteDTO.getId());
		analiseCreditoNegada.setMotivo("Renda baixa");
		analiseCreditoNegada.setLimiteMax(0.0);
		analiseCreditoNegada.setLimiteMin(0.0);
		analiseCreditoNegada.setStatus(StatusAprovadoNegado.NEGADO);
		analiseCreditoNegada.setDataAnalise(LocalDateTime.now());
		analiseCreditoNegada.setScore(0.0);

		return analiseCreditoRepository.save(analiseCreditoNegada);
	}

	/**
	 * Realiza a validação de todos os campos obrigatórios
	 * para a tomada de decisão
	 * 
	 * @param clienteDTO
	 */
	private void validarCamposObrigatorios(final ClienteDTO clienteDTO) {
		if(Objects.isNull(clienteDTO)
				|| Objects.isNull(clienteDTO.getId())
				|| Objects.isNull(clienteDTO.getIdEstadoCivil())
				|| Objects.isNull(clienteDTO.getDependentes())
				|| Objects.isNull(clienteDTO.getRenda())
				|| Objects.isNull(clienteDTO.getIdade())) {

			throw new RestResponseMessageException(RestMessageCode.CAMPOS_OBRIGATORIOS_NAO_INFORMADOS);
		}
	}
}
