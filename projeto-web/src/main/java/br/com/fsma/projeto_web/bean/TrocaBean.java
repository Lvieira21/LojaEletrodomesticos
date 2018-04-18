package br.com.fsma.projeto_web.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.fsma.projeto_web.modelo.dao.TrocaDAO;
import br.com.fsma.projeto_web.modelo.negocio.Troca;
import br.com.fsma.projeto_web.modelo.negocio.Venda;
import br.com.fsma.projeto_web.msg.Mensagem;
import br.com.fsma.projeto_web.tx.Transacional;

@Named
@ViewScoped
public class TrocaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Troca troca;

	private Mensagem mensagem = new Mensagem();

	@Inject
	private TrocaDAO trocaDao;

	public Troca getTroca() {
		return troca;
	}

	@PostConstruct
	public void init() {
		System.out.println("TrocaBean.init();");
		troca = new Troca();
	}

	public List<Troca> getTrocas() {
		return trocaDao.listaTodos();
	}

	public List<Venda> getListaVendasProdutos() {
		return trocaDao.listaVendaProdutos(this.troca.getCliente());
	}

	@Transacional
	public void trocar() {
		this.troca.setDataTroca(LocalDate.now());
		trocaDao.adiciona(troca);
		mensagem.addMessageSuccess("Mensagem do sistema", "Troca registrada com sucesso!");
		troca = new Troca();
	}

	public boolean clienteSelecionado() {
		if (this.troca.getCliente() != null) {
			return true;
		}
		return false;
	}

	public boolean vendaSelecionada() {
		if (this.troca.getVenda() != null) {
			return true;
		}
		return false;
	}

	public boolean produtoSelecionado() {
		if (this.troca.getProdutoVenda() != null) {
			return true;
		}
		return false;
	}

}
