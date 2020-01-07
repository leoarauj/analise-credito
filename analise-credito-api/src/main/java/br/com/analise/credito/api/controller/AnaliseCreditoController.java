package br.com.analise.credito.api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.analise.credito.api.dto.AnaliseCreditoDTO;
import br.com.analise.credito.api.dto.ClienteDTO;
import br.com.analise.credito.api.dto.ResultadoAnaliseDTO;
import br.com.analise.credito.api.enuns.StatusAprovadoNegado;
import br.com.analise.credito.api.model.AnaliseCredito;
import br.com.analise.credito.api.response.RestResponse;
import br.com.analise.credito.api.service.AnaliseCreditoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "${api.version}/analises")
@Api(value = "Endpoints de Análise de Crédito")
public class AnaliseCreditoController {

	@Autowired
	private AnaliseCreditoService analiseCreditoService;

	/**
	 * Recebe um {@link ClienteDTO} para analiarProposta
	 * 
	 * @param clienteDTO
	 * @return
	 */
	@ApiOperation(value = "Avalia a proposta para concessão de crédito")
	@ApiResponses({ 
		@ApiResponse(code = 201, message = "Success", response = URI.class),
		@ApiResponse(code = 400, message = "Bad Request", response = RestResponse.class),
		@ApiResponse(code = 404, message = "Nof Found", response = RestResponse.class)
	})
	@RequestMapping(produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> avaliarProposta(@RequestBody final ClienteDTO clienteDTO) {
		AnaliseCredito analiseCredito = analiseCreditoService.avaliarProposta(clienteDTO);

		URI location = getURI(analiseCredito);

		return ResponseEntity.created(location).build();
	}

	/**
	 * Retorna uma lista com os dados de {@link ClienteDTO} e {@link AnaliseCreditoDTO}
	 * que já foram avaliados
	 * 
	 * @return
	 */
	@ApiOperation(value = "Busca todos os resultados vigentes de cada Análise de Crédito")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Success", response = ResultadoAnaliseDTO.class),
		@ApiResponse(code = 400, message = "Bad Request", response = RestResponse.class),
		@ApiResponse(code = 404, message = "Nof Found", response = RestResponse.class)
	})
	@RequestMapping(value = "/resultados", method = RequestMethod.GET)
	public ResponseEntity<?> buscarTodosResultadosAnaliseCompleta() {
		List<ResultadoAnaliseDTO> resultadosAnalise = analiseCreditoService.buscarTodosResultadosAnalise();

		return ResponseEntity.ok(resultadosAnalise);
	}

	/**
	 * Endpoint para a consulta de resultados de análise por cpf de cliente
	 * @param cpf
	 * @return
	 */
	@ApiOperation(value = "Consulta resultado da Análise de Crédito por cpf de cliente")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Success", response = ResultadoAnaliseDTO.class),
		@ApiResponse(code = 400, message = "Bad Request", response = RestResponse.class),
		@ApiResponse(code = 404, message = "Nof Found", response = RestResponse.class)
	})
	@RequestMapping(value = "/resultado/cliente/{cpf}", method = RequestMethod.GET)
	public ResponseEntity<?> getAnaliseCreditoPorCpfCliente(@PathVariable("cpf") final String cpf) {
		ResultadoAnaliseDTO resultado = analiseCreditoService.buscarResultadoAnalisePorCpfCliente(cpf);

		return ResponseEntity.ok(resultado);
	}

	/**
	 * Endpoint que retorna uma lista dos valores de {@link StatusAprovadoNegado}
	 * @return
	 */
	@ApiOperation(value = "Busca todos os valores disponivéis no enum StatusAprovadoNegado")
	@ApiResponse(code = 200, message = "Success", response = StatusAprovadoNegado.class)
	@RequestMapping(value = "/status", method = RequestMethod.GET)
	public ResponseEntity<?> retornarValoresStatusAprovadoNegado() {
		List<StatusAprovadoNegado> collect = analiseCreditoService.getStatusAprovadoNegado();

		return ResponseEntity.ok(collect);
	}

	/**
	 * Gera a URI de retorno
	 * 
	 * @param analiseCredito
	 * @return
	 */
	private URI getURI(final AnaliseCredito analiseCredito) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(analiseCredito.getId()).toUri();
	}
}
