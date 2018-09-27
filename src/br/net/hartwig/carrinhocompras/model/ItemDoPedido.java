package br.net.hartwig.carrinhocompras.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Diego Michel Hartwig
 * @since 1.0.2018
 * @version 1.0.2018
 *
 */

@Entity
@Table(name = "item_do_pedido")
public class ItemDoPedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, length = 11)
	private int id;

	@Column(name = "qtdade", nullable = false, length = 11)
	private int qtdade;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_pedido", referencedColumnName = "id", nullable = false)
	private Pedido pedido;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_produto", referencedColumnName = "id", nullable = false)
	private Produto produto;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQtdade() {
		return qtdade;
	}

	public void setQtdade(int qtdade) {
		this.qtdade = qtdade;
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

	@Override
	public String toString() {
		return "ItemDoPedido [id=" + id + ", qtdade=" + qtdade + ", pedido=" + pedido + ", produto=" + produto + "]";
	}

}