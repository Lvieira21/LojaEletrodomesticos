package br.com.fsma.projeto_web.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
		if (produtoDao.existe(produto) == false) {
			produtoDao.adiciona(produto);
			addMessageSuccess("Mensagem do sistema", "Cadastrando produto: " + this.produto.getNome());
		} else {
			addMessageError("Erro!", "Produto " + this.produto.getNome() + " já existe.");
		}
		this.produto = new Produto();

	}

	public List<Produto> getProdutos() {
		return produtoDao.listaTodos();
	}

	@Transacional
	public void aumenta(Produto produto) {
		Integer quantidade = produto.getQtd();
		quantidade += 1;
		produto.setQtd(quantidade);
		produtoDao.atualiza(produto);
		addMessageSuccess("Mensagem do sistema", "Adição feita com sucesso.");
	}

	@Transacional
	public void diminui(Produto produto) {
		Integer quantidade = produto.getQtd();
		if (quantidade >= 1) {
			quantidade -= 1;
			produto.setQtd(quantidade);
			produtoDao.atualiza(produto);
			addMessageSuccess("Mensagem do sistema", "Subtração feita com sucesso.");
		} else {
			addMessageError("Erro!", "Quantidade mínima.");
		}
	}

	@Transacional
	public void remove(Produto produto) {
		addMessageSuccess("Mensagem do sistema", "Produto - " + produto.getNome() + " removido(a).");
		System.out.println("Produto " + produto.getNome() + " " + produto.getQtd() + " removido!");
		produtoDao.remove(produto);
	}

	public void addMessageSuccess(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void addMessageError(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
