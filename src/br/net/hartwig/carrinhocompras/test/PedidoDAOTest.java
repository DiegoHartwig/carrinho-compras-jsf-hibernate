package br.net.hartwig.carrinhocompras.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.net.hartwig.carrinhocompras.dao.ItemDoPedidoDAO;
import br.net.hartwig.carrinhocompras.dao.PedidoDAO;
import br.net.hartwig.carrinhocompras.model.Cliente;
import br.net.hartwig.carrinhocompras.model.ItemDoPedido;
import br.net.hartwig.carrinhocompras.model.Pedido;

public class PedidoDAOTest {

	/**
	 *
	 * @author Diego Michel Hartwig
	 * @since 1.0.2018
	 * @version 1.0.2018
	 *
	 */

	@Test
	@Ignore
	public void salvar() {

		Cliente cliente1 = new Cliente();
		cliente1.setId(1);

		Pedido pedido1 = new Pedido();

		pedido1.setCliente(cliente1);

		Date dataAtual = new Date();
		SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
		dataFormatada.format(dataAtual);
		pedido1.setData(dataAtual);

		PedidoDAO dao = new PedidoDAO();

		dao.salvar(pedido1);

	}

	@Test
	public void listar() {

		PedidoDAO dao = new PedidoDAO();

		List<Pedido> pedidos = dao.getAll();

		for (Pedido pedido : pedidos) {

			System.out.println(pedido);

		}

	}

}
