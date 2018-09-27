package br.net.hartwig.carrinhocompras.test;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.net.hartwig.carrinhocompras.dao.ItemDoPedidoDAO;
import br.net.hartwig.carrinhocompras.model.ItemDoPedido;
import br.net.hartwig.carrinhocompras.model.Pedido;
import br.net.hartwig.carrinhocompras.model.Produto;

public class ItemDoPedidoDAOTest {

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

		Pedido pedido = new Pedido();
		pedido.setId(1);

		Produto produto = new Produto();
		produto.setId(7);

		ItemDoPedido itemDoPedido = new ItemDoPedido();
		itemDoPedido.setPedido(pedido);
		itemDoPedido.setProduto(produto);
		itemDoPedido.setQtdade(5);

		ItemDoPedidoDAO itemDoPedidoDao = new ItemDoPedidoDAO();

		itemDoPedidoDao.salvar(itemDoPedido);

	}

	@Test
	@Ignore
	public void listar() {

		ItemDoPedidoDAO dao = new ItemDoPedidoDAO();

		List<ItemDoPedido> itens = dao.getAll();

		for (ItemDoPedido item : itens) {

			System.out.println(item);

		}

	}

}
