package br.com.fsma.projeto_web.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.fsma.projeto_web.modelo.dao.ProdutoDAO;
import br.com.fsma.projeto_web.modelo.negocio.Produto;
import br.com.fsma.projeto_web.tx.Transacional;

@Named
@ViewScoped
public class ProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Produto produto;
	
	@Inject
	private ProdutoDAO produtoDao;
	
	public Produto getProduto() {
		return produto;
	}
	
	@PostConstruct
	public void init() {
		System.out.println("ProdutoBean.init();");
		produto = new Produto();
	}
	
	@Transacional
	public void cadastra() {
		System.out.println("Cadastrando produto: " + this.produto.getNome());
		produtoDao.adiciona(produto);
		this.produto = new Produto();
	}
	
	public List<Produto> getProdutos () {
		return produtoDao.listaTodos();
	}
}
