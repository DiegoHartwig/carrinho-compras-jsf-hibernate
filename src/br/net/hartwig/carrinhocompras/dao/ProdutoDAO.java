package br.net.hartwig.carrinhocompras.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.net.hartwig.carrinhocompras.model.Produto;

/**
 *
 * @author Diego Michel Hartwig
 * @since 1.0.2018
 * @version 1.0.2018
 *
 */

public class ProdutoDAO extends DAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public void salvar(Produto produto) {

		EntityManager em = getEntityManager().createEntityManager();

		try {
			em.getTransaction().begin();
			em.persist(produto);
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}

	}

	public void atualizar(Produto produto) {

		EntityManager em = getEntityManager().createEntityManager();

		try {
			em.getTransaction().begin();

			Produto p = em.find(Produto.class, produto.getId());

			p.setDescricao(produto.getDescricao());

			em.getTransaction().commit();
			em.close();

		} catch (Exception e) {
			em.getTransaction().rollback();
		}

	}

	public void delete(Produto produto) {

		EntityManager em = getEntityManager().createEntityManager();

		try {
			em.getTransaction().begin();
			Produto p = em.find(Produto.class, produto.getId());
			em.remove(p);
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}

	}

	public List<Produto> getAll() {

		EntityManager em = getEntityManager().createEntityManager();

		List<Produto> lista = null;

		try {
			Query q = em.createQuery("select object(p) from Produto as p order by p.id DESC");
			lista = q.getResultList();

		} catch (Exception e) {
			em.close();
		}

		return lista;

	}

}