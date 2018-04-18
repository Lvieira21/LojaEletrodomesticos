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

@Named
@ViewScoped
public class ClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Cliente cliente;
	private Mensagem mensagem = new Mensagem();

	@Inject
	private ClienteDAO clienteDao;

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
		if (clienteDao.existe(cliente) == false) {
			if (clienteDao.numeroValido(cliente)) {
				cliente.setTelefone(clienteDao.celularOuFixo(cliente));
				if (clienteDao.verificaCpf(cliente)) {
					clienteDao.celularOuFixo(cliente);
					clienteDao.adiciona(cliente);
					mensagem.addMessageSuccess("Mensagem do Sistema",
							"Cliente " + cliente.getNome() + " cadastrado com sucesso!");
					this.cliente = new Cliente();
				} else {
					mensagem.addMessageError("Erro!", "CPF inválido");
				}
			} else {
				mensagem.addMessageError("Erro!", "Telefone inválido");
			}

		} else {
			mensagem.addMessageError("Erro!", "CPF já cadastrado");
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
