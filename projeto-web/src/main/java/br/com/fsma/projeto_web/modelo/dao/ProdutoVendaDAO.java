package br.com.fsma.projeto_web.modelo.dao;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import br.com.fsma.projeto_web.modelo.negocio.ProdutoVenda;


@Named
@RequestScoped
public class ProdutoVendaDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	private DAO<ProdutoVenda> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<ProdutoVenda>(this.em, ProdutoVenda.class);
	}

	@Inject
	private EntityManager em;

	public void adiciona(ProdutoVenda produtoVenda) {
		dao.adiciona(produtoVenda);
	}
	
	public void atualiza(ProdutoVenda produtoVenda) {
		em.merge(produtoVenda);
	}

	public void remove(ProdutoVenda produtoVenda) {
		dao.remove(produtoVenda);
	}

	public ProdutoVenda buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
}
