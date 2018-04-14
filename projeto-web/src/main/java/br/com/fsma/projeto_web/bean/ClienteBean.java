package br.com.fsma.projeto_web.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.fsma.projeto_web.modelo.dao.ClienteDAO;
import br.com.fsma.projeto_web.modelo.negocio.Cliente;
import br.com.fsma.projeto_web.tx.Transacional;

@Named
@ViewScoped
public class ClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Cliente cliente;

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
		System.out.println("Gravando cliente: " + this.cliente.getNome());

		if (clienteDao.existe(cliente) == false) {
			clienteDao.adiciona(cliente);
			String mensagemSucesso = "Cliente " + cliente.getNome() + " cadastrado com sucesso!";
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "", mensagemSucesso));
		} else {
			String mensagem = "CPF já cadastrado!";
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", mensagem));
			this.cliente = new Cliente();
		}
	}
	
	public List<Cliente> getClientes () {
		return clienteDao.listaTodos();
	}
}
