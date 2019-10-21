package br.com.cadastro.api.repository;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cadastro.api.enuns.EstadoCivil;
import br.com.cadastro.api.enuns.Sexo;
import br.com.cadastro.api.model.Cliente;

/**
 * Classe de <b>Test</b> para a camada <b>Repository</b> de {@link Cliente}
 * @author Leonardo Araújo
 */
@DataJpaTest
@RunWith(SpringRunner.class)
public class ClienteRepositoryTest {

	@Autowired
	private ClienteRepository clienteRepository;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	/**
	 * Realiza o teste de persistência de {@link Cliente}
	 */
	@Test
	public void salvaCliente() {
		Cliente cliente = initCliente();

		this.clienteRepository.save(cliente);

		Assertions.assertThat(cliente.getId()).isNotNull();
	}

	/**
	 * Realiza o teste da busca de {@link Cliente} pelo atributo CPF
	 */
	@Test
	public void buscaClientePorCpf() {
		Cliente cliente = initCliente();
		clienteRepository.save(cliente);

		Cliente result = clienteRepository.findByCpf(cliente.getCpf());

		Assertions.assertThat(result).isEqualTo(cliente);
	}

	/**
	 * Cria um modelo de {@link Cliente} para testes
	 * @return Cliente
	 */
	private Cliente initCliente() {
		Cliente cliente = new Cliente();

		cliente.setCpf("446.712.560-45");
		cliente.setDependentes(3);
		cliente.setEstadoCivil(EstadoCivil.SOLTEIRO);
		cliente.setIdade(25);
		cliente.setNome("Teste");
		cliente.setRenda(2000.0);
		cliente.setSexo(Sexo.MASCULINO);
		cliente.setUf("GO");

		return cliente;
	}
}
