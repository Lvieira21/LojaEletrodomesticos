package br.com.fsma.projeto_web.modelo.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.fsma.projeto_web.modelo.negocio.Cliente;
import br.com.fsma.projeto_web.modelo.negocio.Troca;
import br.com.fsma.projeto_web.modelo.negocio.Venda;

@Named
@RequestScoped
public class TrocaDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	private DAO<Troca> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<Troca>(this.em, Troca.class);
	}

	@Inject
	private EntityManager em;

	public void adiciona(Troca troca) {
		dao.adiciona(troca);
	}

	public Troca buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public List<Venda> listaVendaProdutos(Cliente cliente) {
		String jpql = "select distinct v from Venda v join fetch v.produtos where v.cliente = :pCliente";

		Query query = em.createQuery(jpql);
		query.setParameter("pCliente", cliente);

		List<Venda> lista = query.getResultList();

		return lista;
	}
	
	public List<Troca> listaTodos() {
		return dao.listaTodos();
	}
}
