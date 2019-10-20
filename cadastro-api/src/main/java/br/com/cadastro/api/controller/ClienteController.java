package br.com.cadastro.api.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.cadastro.api.dto.ClienteDTO;
import br.com.cadastro.api.mapper.ClienteMapper;
import br.com.cadastro.api.model.Cliente;
import br.com.cadastro.api.response.RestResponse;
import br.com.cadastro.api.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "${api.version}/clientes")
@Api(value = "Endpoints para gerenciar o Cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ClienteMapper clienteMapper;

	/**
	 * Realiza a conversão de DTO para Entity e executa o serviço para persistir um
	 * novo {@link Cliente}
	 * 
	 * @param clienteDTO
	 * @return
	 */
	@ApiOperation(value = "Salva Cliente")
	@ApiResponses({ 
		@ApiResponse(code = 201, message = "Success", response = ClienteDTO.class),
		@ApiResponse(code = 400, message = "Bad Request", response = RestResponse.class),
		@ApiResponse(code = 404, message = "Nof Found", response = RestResponse.class)
	})
	@RequestMapping(produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> salvar(@RequestBody final ClienteDTO clienteDTO) {
		clienteDTO.setId(null);

		Cliente cliente = clienteMapper.toEntity(clienteDTO);
		clienteService.salvar(cliente);

		URI location = getURI(cliente);

		return ResponseEntity.created(location).build();
	}

	/**
	 * Retorna a {@link ClienteDTO} segundo o <b>CPF</b> informado
	 * 
	 * @param cpf
	 * @return {@link ClienteDTO}
	 */
	@ApiOperation(value = "Consulta Cliente por CPF")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Success", response = ClienteDTO.class),
		@ApiResponse(code = 400, message = "Bad Request", response = RestResponse.class),
		@ApiResponse(code = 404, message = "Nof Found", response = RestResponse.class)
	})
	@RequestMapping(value = "/cpf/{cpf}", method = RequestMethod.GET)
	public ResponseEntity<?> buscarPorCpf(@PathVariable("cpf") final String cpf) {
		Cliente cliente = clienteService.buscarPorCpf(cpf);
		ClienteDTO clienteDTO = clienteMapper.toDTO(cliente);

		return ResponseEntity.ok(clienteDTO);
	}

	/**
	 * Gera a URI de retorno
	 * 
	 * @param clienteDTO
	 * @return
	 */
	private URI getURI(final Cliente cliente) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId()).toUri();
	}
}