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

import br.net.hartwig.carrinhocompras.dao.ProdutoDAO;
import br.net.hartwig.carrinhocompras.model.Produto;

/**
 *
 * @author Diego Michel Hartwig
 * @since 1.0.2018
 * @version 1.0.2018
 *
 */

@ManagedBean(name = "produtoBean")
@RequestScoped
public class ProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Produto produto = new Produto();

	private transient DataModel<Produto> produtos;

	private Produto produtoSelecionado;

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void selecionarProduto() {
		this.produto = produtos.getRowData();
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}

	public void setProdutos(DataModel<Produto> produtos) {
		this.produtos = produtos;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public void novoProduto() {

		produto = new Produto();

	}

	@PostConstruct
	public void inicializar() {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(false);
	}

	public DataModel<Produto> getProdutos() {

		ProdutoDAO produtoDao = new ProdutoDAO();

		try {
			List<Produto> lista = produtoDao.getAll();
			produtos = new ListDataModel<Produto>(lista);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return produtos;

	}

	public String addProduto() {

		String retorno = "erro";

		try {
			ProdutoDAO dao = new ProdutoDAO();
			dao.salvar(produto);

			retorno = "listar";
		} catch (Exception e) {
			// TODO: handle exception
		}

		return retorno;

	}

	public String deleteProduto() {
		this.produto = produtos.getRowData();

		String retorno = "erro";

		try {
			ProdutoDAO dao = new ProdutoDAO();

			dao.delete(produto);

			String nome = produto.getDescricao();

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Produto: " + nome + ", deletado com sucesso!"));
			retorno = "listar";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return retorno;
	}

	public String updateProduto() {

		String retorno = "erro";

		try {
			ProdutoDAO dao = new ProdutoDAO();

			dao.atualizar(produto);

			String nome = produto.getDescricao();

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Produto: " + nome + ", atualizado com sucesso!"));

			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

			retorno = "listar";

		} catch (Exception e) {
			// TODO: handle exception
		}
		return retorno;
	}

}
