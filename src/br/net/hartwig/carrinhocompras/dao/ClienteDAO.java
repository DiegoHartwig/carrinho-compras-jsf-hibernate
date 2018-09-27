package br.net.hartwig.carrinhocompras.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.net.hartwig.carrinhocompras.model.Cliente;

/**
 *
 * @author Diego Michel Hartwig
 * @since 1.0.2018
 * @version 1.0.2018
 *
 */

public class ClienteDAO extends DAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public void salvar(Cliente cliente) throws Exception {

		EntityManager em = getEntityManager().createEntityManager();

		try {
			em.getTransaction().begin();
			em.persist(cliente);
			em.getTransaction().commit();
			em.close();
		} catch (PersistenceException e) {

			Throwable t = e;
			boolean cont = true;
			while (t != null) {
				if (t.getMessage().startsWith("Duplicate entry")) {
					cont = false;
					throw new Exception("Duplicate entry");
				}
				t = t.getCause();
			}
			if (cont) {
				throw new Exception(e.getMessage());
			}

		}

	}

	public Cliente get(int cliente_id) {

		EntityManager em = getEntityManager().createEntityManager();

		return em.find(Cliente.class, cliente_id);
	}

	public void update(Cliente cliente) {

		EntityManager em = getEntityManager().createEntityManager();

		try {
			em.getTransaction().begin();
			Cliente c = em.find(Cliente.class, cliente.getId());

			c.setNome(cliente.getNome());
			c.setSobrenome(cliente.getSobrenome());
			c.setCpf(cliente.getCpf());

			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}

	}

	public void delete(Cliente cliente) {

		EntityManager em = getEntityManager().createEntityManager();

		try {
			em.getTransaction().begin();
			Cliente c = em.find(Cliente.class, cliente.getId());
			em.remove(c);
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}

	}

	public List<Cliente> getAll() {

		EntityManager em = getEntityManager().createEntityManager();

		List<Cliente> lista = null;

		try {
			Query q = em.createQuery("select object(c) from Cliente as c order by c.id DESC");
			lista = q.getResultList();

		} catch (Exception e) {
			em.close();
		}

		return lista;

	}

}
