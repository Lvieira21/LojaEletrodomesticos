package br.com.fsma.projeto_web.modelo.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import br.com.fsma.projeto_web.modelo.negocio.Venda;

@Named
@RequestScoped
public class VendaDao implements Serializable {

	private static final long serialVersionUID = 1L;

	private DAO<Venda> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<Venda>(this.em, Venda.class);
	}

	@Inject
	private EntityManager em;

	public void adiciona(Venda venda) {
		dao.adiciona(venda);
	}

	public void atualiza(Venda venda) {
		em.merge(venda);
	}

	public void remove(Venda venda) {
		dao.remove(venda);
	}

	public Venda buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	
	public List<Venda> listaTodos() {
		return dao.listaTodos();
	}
}
