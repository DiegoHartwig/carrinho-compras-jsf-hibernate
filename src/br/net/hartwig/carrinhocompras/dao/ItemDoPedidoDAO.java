package br.net.hartwig.carrinhocompras.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.net.hartwig.carrinhocompras.model.ItemDoPedido;

/**
 *
 * @author Diego Michel Hartwig
 * @since 1.0.2018
 * @version 1.0.2018
 *
 */

public class ItemDoPedidoDAO extends DAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public void salvar(ItemDoPedido itemDoPedido) {

		EntityManager em = getEntityManager().createEntityManager();

		try {

			em.getTransaction().begin();

			em.persist(itemDoPedido);

			em.getTransaction().commit();

			em.close();

		} catch (Exception ex) {

			em.getTransaction().rollback();
		}

	}

	public void atualizar(ItemDoPedido itemDoPedido) {

		EntityManager em = getEntityManager().createEntityManager();

		try {

			em.getTransaction().begin();

			ItemDoPedido item = em.find(ItemDoPedido.class, itemDoPedido.getId());

			item.setProduto(itemDoPedido.getProduto());

			item.setPedido(itemDoPedido.getPedido());

			item.setQtdade(itemDoPedido.getQtdade());

			em.getTransaction().commit();

			em.close();

		} catch (Exception ex) {

			em.getTransaction().rollback();
		}

	}

	public void delete(ItemDoPedido itemDoPedido) {

		EntityManager em = getEntityManager().createEntityManager();

		try {

			em.getTransaction().begin();

			ItemDoPedido p = em.find(ItemDoPedido.class, itemDoPedido.getId());

			em.remove(p);

			em.getTransaction().commit();

			em.close();

		} catch (Exception ex) {

			em.getTransaction().rollback();
		}

	}

	public List<ItemDoPedido> getAll() {

		EntityManager em = getEntityManager().createEntityManager();

		List<ItemDoPedido> lista = null;

		try {
			Query q = em.createQuery("select object(c) from ItemDoPedido as c");
			lista = q.getResultList();

		} catch (Exception e) {
			em.close();
		}

		return lista;

	}

	@SuppressWarnings("unchecked")
	public List<ItemDoPedido> itemPedidoByPedido(Integer id_pedido) {

		EntityManager em = getEntityManager().createEntityManager();

		Query query = em.createQuery("SELECT i from ItemDoPedido i " + "join i.pedido p " + "WHERE p.id = :id")
				.setParameter("id", id_pedido);

		return query.getResultList();

	}

}