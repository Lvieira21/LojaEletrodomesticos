package br.com.fsma.projeto_web.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.fsma.projeto_web.modelo.dao.ProdutoDAO;
import br.com.fsma.projeto_web.modelo.negocio.Produto;
import br.com.fsma.projeto_web.msg.Mensagem;
import br.com.fsma.projeto_web.tx.Transacional;

@Named
@ViewScoped
public class ProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Produto produto;
	private Mensagem mensagem = new Mensagem();

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
			mensagem.addMessageSuccess("Mensagem do sistema", "Cadastrando produto: " + this.produto.getNome());
		} else {
			mensagem.addMessageError("Erro!", "Produto " + this.produto.getNome() + " já existe.");
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
		mensagem.addMessageSuccess("Mensagem do sistema", "Adição feita com sucesso.");
	}

	@Transacional
	public void diminui(Produto produto) {
		Integer quantidade = produto.getQtd();
		if (quantidade >= 1) {
			quantidade -= 1;
			produto.setQtd(quantidade);
			produtoDao.atualiza(produto);
			mensagem.addMessageSuccess("Mensagem do sistema", "Subtração feita com sucesso.");
		} else {
			mensagem.addMessageError("Erro!", "Quantidade mínima.");
		}
	}

	@Transacional
	public void remove(Produto produto) {
		if (produto.getQtd() == 0) {
			produtoDao.remove(produto);
			mensagem.addMessageSuccess("Mensagem do sistema", "Produto - " + produto.getNome() + " removido(a).");
		} else {
			mensagem.addMessageError("Erro!", "É necessário que não haja nenhuma quantidade do produto restante");
		}
	}

}
