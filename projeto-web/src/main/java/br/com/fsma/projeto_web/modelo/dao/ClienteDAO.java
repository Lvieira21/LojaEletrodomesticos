package br.com.fsma.projeto_web.modelo.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.fsma.projeto_web.modelo.negocio.Cliente;

@Named
@RequestScoped
public class ClienteDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	private DAO<Cliente> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<Cliente>(this.em, Cliente.class);
	}

	@Inject
	private EntityManager em;

	public void adiciona(Cliente cliente) {
		dao.adiciona(cliente);
	}

	public void atualiza(Cliente cliente) {
		em.merge(cliente);
	}

	public void remove(Cliente cliente) {
		dao.remove(cliente);
	}

	public Cliente buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public boolean existe(Cliente cliente) {
		TypedQuery<Cliente> query = em.createQuery(" select c from Cliente c " + " where c.cpf = :pCpf", Cliente.class);

		query.setParameter("pCpf", cliente.getCpf());
		try {
			@SuppressWarnings("unused")
			Cliente resultado = query.getSingleResult();
			return true;
		} catch (NoResultException ex) {
			return false;
		}

	}

	public List<Cliente> listaTodosPaginada(int firstResult, int maxResults) {
		return dao.listaTodosPaginada(firstResult, maxResults);
	}
}
