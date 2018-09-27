package br.net.hartwig.carrinhocompras.test;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.net.hartwig.carrinhocompras.dao.ClienteDAO;
import br.net.hartwig.carrinhocompras.model.Cliente;

/**
 *
 * @author Diego Michel Hartwig
 * @since 1.0.2018
 * @version 1.0.2018
 *
 */

public class ClienteDAOTest {

	@Test
	@Ignore
	public void salvar() {

		Cliente c1 = new Cliente();
		c1.setCpf("199.999.999-99");
		c1.setNome("AAAAAAAA");
		c1.setSobrenome("BBBBBBBB");

		Cliente c2 = new Cliente();
		c2.setCpf("100.000.000-00");
		c2.setNome("CCCCCCCC");
		c2.setSobrenome("DDDDDDDDDD");

		// Cliente c3 = new Cliente();

		ClienteDAO dao = new ClienteDAO();

		try {
			dao.salvar(c1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			dao.salvar(c2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// dao.salvar(c3);

	}

	@Test
	@Ignore
	public void listar() {

		ClienteDAO dao = new ClienteDAO();

		List<Cliente> clientes = dao.getAll();

		for (Cliente cliente : clientes) {

			System.out.println(cliente);

		}

	}

	@Test
	@Ignore
	public void deletar() {

		ClienteDAO dao = new ClienteDAO();

		Cliente cliente = new Cliente();
		cliente.setId(10);

		dao.delete(cliente);

	}

	@Test
	@Ignore
	public void update() {

		Cliente cliente = new Cliente();

		cliente.setCpf("654.654.654-11");
		cliente.setNome("Fulano7777");
		cliente.setSobrenome("da Silva");
		cliente.setId(9);

		ClienteDAO dao = new ClienteDAO();
		dao.update(cliente);

	}

}
