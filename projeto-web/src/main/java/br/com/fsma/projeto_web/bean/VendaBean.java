package br.com.fsma.projeto_web.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.fsma.projeto_web.modelo.dao.ProdutoDAO;
import br.com.fsma.projeto_web.modelo.dao.ProdutoVendaDAO;
import br.com.fsma.projeto_web.modelo.dao.VendaDao;
import br.com.fsma.projeto_web.modelo.negocio.Cliente;
import br.com.fsma.projeto_web.modelo.negocio.Produto;
import br.com.fsma.projeto_web.modelo.negocio.ProdutoVenda;
import br.com.fsma.projeto_web.modelo.negocio.Venda;
import br.com.fsma.projeto_web.msg.Mensagem;
import br.com.fsma.projeto_web.tx.Transacional;

@Named
@ViewScoped
public class VendaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Venda venda;
	private Mensagem mensagem = new Mensagem();
	private ProdutoVenda produtoVenda;

	@Inject
	private VendaDao vendaDao;

	@Inject
	private ProdutoVendaDAO produtoVendaDao;

	@Inject
	private ProdutoDAO produtoDao;

	public Venda getVenda() {
		return venda;
	}

	@PostConstruct
	public void init() {
		System.out.println("VendaBean.init();");
		venda = new Venda();
		venda.setProdutos(new ArrayList<>());
	}

	public void selecionaCliente(Cliente cliente) {
		if (cliente != null) {
			this.venda.setDataVenda(LocalDate.now());
			this.venda.setCliente(cliente);
			mensagem.addMessageSuccess("Mensagem do sistema", "Cliente " + cliente.getNome() + " selecionado(a)!");
		}
	}

	public void removeCliente() {
		venda = new Venda();
		venda.setProdutos(new ArrayList<>());
	}

	public void selecionaProduto(Produto produto) {
		this.produtoVenda = new ProdutoVenda();
		this.produtoVenda.setProduto(produto);
		this.produtoVenda.setQtdVendida(1);
		this.produtoVenda.setValorUnitario(produto.getValorUnitario());
		this.produtoVenda.setVenda(venda);
		if (!(this.venda.getProdutos().contains(produtoVenda))) {
			this.venda.getProdutos().add(produtoVenda);
			valorTotal();
			mensagem.addMessageSuccess("Mensagem do sistema",
					"Produto " + produto.getNome() + " adicionado(a) ao carrinho!");
		} else {
			mensagem.addMessageError("Erro!", "Produto já está no carrinho");
		}
	}

	public void retiraProduto(Produto produto) {
		this.produtoVenda = new ProdutoVenda();
		String nome = produto.getNome();
		this.produtoVenda.setProduto(produto);
		this.venda.getProdutos().remove(produtoVenda);
		valorTotal();
		mensagem.addMessageSuccess("Mensagem do sistema", nome + " removido(a) do carrinho!");
	}

	public void aumenta(ProdutoVenda produtoVenda) {
		Integer quantidade = produtoVenda.getQtdVendida();
		if (quantidade < produtoVenda.getProduto().getQtd()) {
			quantidade += 1;
			produtoVenda.setQtdVendida(quantidade);
			valorTotal();
			mensagem.addMessageSuccess("Mensagem do sistema", "Adição feita com sucesso.");
		} else {
			mensagem.addMessageError("Erro!", "Quantidade máxima atingida.");
		}
	}

	public void diminui(ProdutoVenda produtoVenda) {
		Integer quantidade = produtoVenda.getQtdVendida();
		if (quantidade > 1) {
			quantidade -= 1;
			produtoVenda.setQtdVendida(quantidade);
			valorTotal();
			mensagem.addMessageSuccess("Mensagem do sistema", "Subtração feita com sucesso.");
		} else {
			mensagem.addMessageError("Erro!", "Quantidade mínima.");
		}
	}

	public BigDecimal valorTotal() {
		BigDecimal valor = new BigDecimal(0);
		for (ProdutoVenda prodvend : venda.getProdutos()) {
			valor = valor.add(prodvend.getValorUnitario().multiply(new BigDecimal(prodvend.getQtdVendida())));
		}
		venda.setValorTotal(valor);
		return valor;
	}

	@Transacional
	public void retiraDoEstoque() {
		for (ProdutoVenda prodVend : venda.getProdutos()) {
			for (Produto produto : produtoDao.listaTodos()) {
				if (prodVend.getProduto().equals(produto)) {
					produto.setQtd(produto.getQtd() - prodVend.getQtdVendida());
					produtoDao.atualiza(produto);
				}
			}
		}
	}

	@Transacional
	public void vender() {
		if (!(venda.getProdutos().isEmpty())) {
			vendaDao.adiciona(venda);
			for (ProdutoVenda prodVend : venda.getProdutos()) {
				produtoVendaDao.adiciona(prodVend);
			}
			retiraDoEstoque();
			mensagem.addMessageSuccess("Mensagem do sistema", "Venda concluída com sucesso.");
			removeCliente();
		} else {
			mensagem.addMessageError("Erro!", "Carrinho vazio");
		}
	}

	public List<Venda> getVendas() {
		return vendaDao.listaTodos();
	}

	public boolean vendaCriada() {
		if (this.venda.getCliente() == null) {
			return false;
		}
		return true;
	}
}
