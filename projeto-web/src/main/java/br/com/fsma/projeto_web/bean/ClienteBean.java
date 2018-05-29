package br.com.fsma.projeto_web.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.fsma.projeto_web.modelo.dao.ClienteDAO;
import br.com.fsma.projeto_web.modelo.negocio.Cliente;
import br.com.fsma.projeto_web.msg.Mensagem;
import br.com.fsma.projeto_web.tx.Transacional;
import br.com.fsma.projeto_web.validador.ValidadorCliente;

@Named
@ViewScoped
public class ClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Cliente cliente;
	private Mensagem mensagem = new Mensagem();

	@Inject
	private ClienteDAO clienteDao;

	@Inject
	private ValidadorCliente validador;

	public Cliente getCliente() {
		return cliente;
	}

	@PostConstruct
	public void init() {
		System.out.println("ClienteBean.init();");
		cliente = new Cliente();
	}

	@Transacional
	public void cadastra() {
		if (validador.validaCliente(cliente)) {
			cliente.setTelefone(validador.celularOuFixo(cliente));
			cliente.setCompras(new ArrayList<>());
			cliente.setTrocas(new ArrayList<>());
			clienteDao.adiciona(cliente);
			this.cliente = new Cliente();
		} else {
			this.cliente = new Cliente();
		}
	}

	public List<Cliente> getClientes() {
		return clienteDao.listaTodos();
	}

	public List<Cliente> getClientesSemTroca() {
		List<Cliente> clientesSemTroca = new ArrayList<Cliente>();
		for (Cliente cliente : clienteDao.listaTodos()) {
			if (cliente.getTrocas().isEmpty()) {
				clientesSemTroca.add(cliente);
			}
		}
		return clientesSemTroca;
	}

	@Transacional
	public void remove(Cliente cliente) {
		if (cliente.getCompras().isEmpty()) {
			mensagem.addMessageSuccess("Mensagem do Sistema",
					"Cliente " + cliente.getNome() + " apagado do sistema com sucesso!");
			clienteDao.remove(cliente);
			return;
		} else {
			mensagem.addMessageError("Erro!", "Cliente tem compras realizadas no sistema");
			return;
		}

	}
}
