package br.net.hartwig.carrinhocompras.controller;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import br.net.hartwig.carrinhocompras.dao.ClienteDAO;
import br.net.hartwig.carrinhocompras.dao.ItemDoPedidoDAO;
import br.net.hartwig.carrinhocompras.dao.PedidoDAO;
import br.net.hartwig.carrinhocompras.dao.ProdutoDAO;
import br.net.hartwig.carrinhocompras.model.Cliente;
import br.net.hartwig.carrinhocompras.model.ItemDoPedido;
import br.net.hartwig.carrinhocompras.model.Pedido;
import br.net.hartwig.carrinhocompras.model.Produto;

/**
 *
 * @author Diego Michel Hartwig
 * @since 1.0.2018
 * @version 1.0.2018
 *
 */

@ManagedBean(name = "pedidoBean")
@ViewScoped
public class PedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Pedido pedido = new Pedido();

	private Produto produto = new Produto();

	private transient DataModel<Pedido> pedidos;

	private List<ItemDoPedido> listaItens;

	private List<ItemDoPedido> listaItensFiltrados;

	private int cliente_id;

	private int id_produto;

	private Pedido pedidoSelecionado;

	private Produto produtoSelecionado;

	private Cliente clienteSelecionado;

	private List<Produto> listaProdutos;

	private List<Produto> listaProdutosFiltrados;

	private HashMap<Integer, Integer> hmItensListaPosicao;

	public List<Produto> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public List<Produto> getListaProdutosFiltrados() {
		return listaProdutosFiltrados;
	}

	public void setListaProdutosFiltrados(List<Produto> listaProdutosFiltrados) {
		this.listaProdutosFiltrados = listaProdutosFiltrados;
	}

	public int getCliente_id() {
		return cliente_id;
	}

	public void setCliente_id(int cliente_id) {
		this.cliente_id = cliente_id;
	}

	public int getId_produto() {
		return id_produto;
	}

	public void setId_produto(int id_produto) {
		this.id_produto = id_produto;
	}

	public Pedido getPedidoSelecionado() {
		return pedidoSelecionado;
	}

	public void selecionarPedido() {
		this.pedido = pedidos.getRowData();
	}

	public void setPedidoSelecionado(Pedido pedidoSelecionado) {
		this.pedidoSelecionado = pedidoSelecionado;
	}

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}

	public void setPedidos(DataModel<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

	public HashMap<Integer, Integer> getHmItensListaPosicao() {
		return hmItensListaPosicao;
	}

	public void setHmItensListaPosicao(HashMap<Integer, Integer> hmItensListaPosicao) {
		this.hmItensListaPosicao = hmItensListaPosicao;
	}

	@PostConstruct
	public void inicializar() {
		try {

			ProdutoDAO produtoDao = new ProdutoDAO();

			listaProdutos = produtoDao.getAll();
			listaItens = new ArrayList<>();

			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(false);

		} catch (Exception e) {

		}
	}

	public void novoPedido() {

		PedidoDAO pedidoDAO = new PedidoDAO();
		ItemDoPedidoDAO itemDoPedidoDAO = new ItemDoPedidoDAO();

		pedido = new Pedido();

		if (listaItens.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Por favor, inclua itens no pedido", ""));
		} else if (cliente_id <= 0) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Por favor, selecione um cliente", ""));
		} else {

			pedido.setCliente(clienteSelecionado);
			pedido.setData(new Date());

			pedidoDAO.salvar(pedido);

			for (ItemDoPedido itemDoPedido : listaItens) {
				itemDoPedido.setPedido(pedido);
				itemDoPedidoDAO.salvar(itemDoPedido);
			}

			cliente_id = -1;
			listaItens = new ArrayList<>();
			hmItensListaPosicao.clear();

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Pedido nº " + pedido.getId() + " foi criado com sucesso"));
		}

	}

	public void selecionarCliente(ValueChangeEvent event) {
		ClienteDAO clienteDAO = new ClienteDAO();

		clienteSelecionado = clienteDAO.get(Integer.parseInt(event.getNewValue().toString()));
	}

	public void selecionarPedidosPorCliente(ValueChangeEvent event) {
		ClienteDAO clienteDAO = new ClienteDAO();
		PedidoDAO pedidoDAO = new PedidoDAO();

		clienteSelecionado = clienteDAO.get(Integer.parseInt(event.getNewValue().toString()));

		List<Pedido> lista = pedidoDAO.pedidoByCliente(clienteSelecionado.getId());
		pedidos = new ListDataModel<Pedido>(lista);

		listaItens = new ArrayList<>();
	}

	public Collection<SelectItem> getCarregarClientes() {

		ClienteDAO dao = new ClienteDAO();

		Collection<SelectItem> lista = new ArrayList<SelectItem>();

		lista.add(new SelectItem(-1, "-- Nome do Cliente --"));

		List<Cliente> listaCliente = dao.getAll();

		for (int i = 0; i < listaCliente.size(); i++) {
			lista.add(new SelectItem(listaCliente.get(i).getId(), listaCliente.get(i).getNome()));
		}

		return lista;
	}

	public Collection<SelectItem> getCarregarProdutos() {

		ProdutoDAO produtoDao = new ProdutoDAO();

		Collection<SelectItem> lista = new ArrayList<SelectItem>();

		lista.add(new SelectItem("-- Selecione o produto desejado --"));

		List<Produto> listaProduto = produtoDao.getAll();

		for (int i = 0; i < listaProduto.size(); i++) {
			lista.add(new SelectItem(listaProduto.get(i).getId(), listaProduto.get(i).getDescricao()));
		}

		return lista;
	}

	public Collection<SelectItem> getCarregarClientesByCpf() {

		ClienteDAO dao = new ClienteDAO();

		Collection<SelectItem> lista = new ArrayList<SelectItem>();

		lista.add(new SelectItem(-1, "-- CPF do Cliente --"));

		List<Cliente> listaCliente = dao.getAll();

		for (int i = 0; i < listaCliente.size(); i++) {
			lista.add(new SelectItem(listaCliente.get(i).getId(), listaCliente.get(i).getCpf()));
		}

		return lista;
	}

	public DataModel<Pedido> getPedidos() {

		PedidoDAO pedidoDao = new PedidoDAO();
		List<Pedido> lista = new ArrayList<>();

		try {
			if (clienteSelecionado != null && clienteSelecionado.getId() != 0) {
				lista = pedidoDao.pedidoByCliente(clienteSelecionado.getId());
			} else {
				lista = pedidoDao.getAll();
			}
			pedidos = new ListDataModel<Pedido>(lista);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return pedidos;

	}

	public String addPedido() {

		String retorno = "erro";

		try {
			PedidoDAO dao = new PedidoDAO();
			dao.salvar(pedido);

			retorno = "listar";
		} catch (Exception e) {
			// TODO: handle exception
		}

		return retorno;

	}

	public String deletePedido() {
		this.pedido = pedidos.getRowData();

		String retorno = "erro";

		try {
			PedidoDAO dao = new PedidoDAO();

			dao.delete(pedido);

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Produto: " + pedido.getId() + ", deletado com sucesso!"));
			retorno = "listar";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return retorno;
	}

	public String updatePedido() {

		String retorno = "erro";

		try {
			PedidoDAO dao = new PedidoDAO();

			dao.atualizar(pedido);

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Produto: " + pedido.getId() + ", atualizado com sucesso!"));

			retorno = "listar";

		} catch (Exception e) {
			// TODO: handle exception
		}
		return retorno;
	}

	public void ListarProdutosTelaPedido() {

	}

	public List<ItemDoPedido> getListaItens() {

		if (listaItens == null) {
			listaItens = new ArrayList<ItemDoPedido>();
		}
		return listaItens;
	}

	public void setListaItens(List<ItemDoPedido> listaItens) {
		this.listaItens = listaItens;
	}

	public List<ItemDoPedido> getListaItensFiltrados() {
		return listaItensFiltrados;
	}

	public void setListaItensFiltrados(List<ItemDoPedido> listaItensFiltrados) {
		this.listaItensFiltrados = listaItensFiltrados;
	}

	public void adicionarItem(Produto produto) {

		if (hmItensListaPosicao == null) {
			hmItensListaPosicao = new HashMap<>();
		}

		ItemDoPedido item = new ItemDoPedido();
		item.setProduto(produto);

		if (produto.getQuantidade() <= 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Por favor, defina uma quantidade superior a zero para o produto " + produto.getDescricao(), ""));
		} else {

			item.setQtdade(produto.getQuantidade());

			if (hmItensListaPosicao.containsKey(item.getProduto().getId())) {
				listaItens.get(hmItensListaPosicao.get(item.getProduto().getId()))
						.setQtdade(listaItens.get(hmItensListaPosicao.get(item.getProduto().getId())).getQtdade()
								+ item.getQtdade());
			} else {
				listaItens.add(item);
				hmItensListaPosicao.put(item.getProduto().getId(), listaItens.size() - 1);
			}
		}

	}

	public void excluirItem(ItemDoPedido item) {

		if (hmItensListaPosicao.containsKey(item.getProduto().getId())) {

			listaItens.remove(item);

			hmItensListaPosicao = new HashMap<>();

			for (int i = 0; i < listaItens.size(); i++) {
				hmItensListaPosicao.put(listaItens.get(i).getProduto().getId(), i);
			}
		}

	}

	public void visualizarItensPedido(Pedido pedido) {

		ItemDoPedidoDAO itemDoPedidoDAO = new ItemDoPedidoDAO();

		listaItens = itemDoPedidoDAO.itemPedidoByPedido(pedido.getId());
	}

}
