package br.net.hartwig.carrinhocompras.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.net.hartwig.carrinhocompras.model.Pedido;

/**
 *
 * @author Diego Michel Hartwig
 * @since 1.0.2018
 * @version 1.0.2018
 *
 */

public class PedidoDAO extends DAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public void salvar(Pedido pedido) {

		EntityManager em = getEntityManager().createEntityManager();

		try {

			em.getTransaction().begin();

			em.persist(pedido);

			em.getTransaction().commit();

		} catch (RuntimeException ex) {

			em.getTransaction().rollback();
			throw ex;
		} finally {
			em.close();
		}

	}

	public Pedido pedidoById(Integer id_pedido) {

		EntityManager em = getEntityManager().createEntityManager();

		return em.find(Pedido.class, id_pedido);

	}

	@SuppressWarnings("unchecked")
	public List<Pedido> pedidoByCliente(Integer id_cliente) {

		EntityManager em = getEntityManager().createEntityManager();

		Query query = em.createQuery("SELECT p from Pedido p " + "join p.cliente c " + "WHERE c.id = :id")
				.setParameter("id", id_cliente);

		return query.getResultList();

	}

	public void atualizar(Pedido pedido) {

		EntityManager em = getEntityManager().createEntityManager();

		try {

			em.getTransaction().begin();

			Pedido p = em.find(Pedido.class, pedido.getId());

			p.setData(pedido.getData());

			p.setCliente(pedido.getCliente());

			em.getTransaction().commit();

			em.close();

		} catch (Exception ex) {

			em.getTransaction().rollback();
		}

	}

	public void delete(Pedido pedido) {

		EntityManager em = getEntityManager().createEntityManager();

		try {

			em.getTransaction().begin();

			Pedido p = em.find(Pedido.class, pedido.getId());

			em.remove(p);

			em.getTransaction().commit();

			em.close();

		} catch (Exception ex) {

			em.getTransaction().rollback();
		}

	}

	public List<Pedido> getAll() {

		EntityManager em = getEntityManager().createEntityManager();

		List<Pedido> lista = null;

		try {
			Query q = em.createQuery("select object(p) from Pedido as p");
			lista = q.getResultList();

		} catch (Exception e) {
			em.close();
		}

		return lista;

	}

}