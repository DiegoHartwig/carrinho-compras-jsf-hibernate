package br.net.hartwig.carrinhocompras.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import br.net.hartwig.carrinhocompras.dao.ClienteDAO;
import br.net.hartwig.carrinhocompras.dao.PedidoDAO;
import br.net.hartwig.carrinhocompras.model.Cliente;
import br.net.hartwig.carrinhocompras.model.Pedido;

/**
 *
 * @author Diego Michel Hartwig
 * @since 1.0.2018
 * @version 1.0.2018
 *
 */

@ManagedBean(name = "clienteBean")
@RequestScoped
public class ClienteBean implements Serializable {

	private static final long serialVersionUID = 4246475923528526942L;

	private Cliente cliente = new Cliente();

	private ClienteDAO clienteDao = new ClienteDAO();

	private transient DataModel<Cliente> clientes;

	private Cliente clienteSelecionado;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public ClienteDAO getClienteDao() {
		return clienteDao;
	}

	public void setClienteDao(ClienteDAO clienteDao) {
		this.clienteDao = clienteDao;
	}

	public void setClientes(DataModel<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

	public void selecionarCliente() {
		this.cliente = clientes.getRowData();
	}

	@PostConstruct
	public void inicializar() {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(false);
	}

	public DataModel<Cliente> getClientes() {

		ClienteDAO dao = new ClienteDAO();

		try {
			List<Cliente> lista = dao.getAll();

			clientes = new ListDataModel<Cliente>(lista);

		} catch (Exception e) {

		}
		return clientes;
	}

	public void addCliente() {

		try {
			ClienteDAO dao = new ClienteDAO();

			dao.salvar(cliente);

			String nome = cliente.getNome();

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Cliente " + nome + ", cadastrado com sucesso"));

		} catch (Exception e) {

			if (e.getMessage().trim().startsWith("Duplicate entry")) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção! Este CPF já está cadastrado", ""));

			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Erro ao salvar"));
				e.printStackTrace();
			}

		}

	}

	public void novoCliente() {
		cliente = new Cliente();
	}

	public void deleteCliente() {
		this.cliente = clientes.getRowData();
		try {
			ClienteDAO dao = new ClienteDAO();
			PedidoDAO pedidoDAO = new PedidoDAO();

			List<Pedido> listaPedidoCliente = pedidoDAO.pedidoByCliente(cliente.getId());

			if (listaPedidoCliente.size() > 0) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Não foi possível excluir o cliente, pois já existem pedidos relacionados.", ""));
			} else {

				dao.delete(cliente);

				String nome = cliente.getNome();

				List<Cliente> lista = dao.getAll();

				clientes = new ListDataModel<Cliente>(lista);

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Cliente: " + nome + ", deletado com sucesso!"));
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível excluir o cliente.", ""));
		}
	}

	public String updateCliente() {

		String retorno = "erro";

		try {
			ClienteDAO dao = new ClienteDAO();

			dao.update(cliente);

			String nome = cliente.getNome();

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Cliente: " + nome + ", atualizado com sucesso!"));

			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

			retorno = "clientes";

		} catch (Exception e) {
			// TODO: handle exception
		}
		return retorno;
	}

}
