package br.com.fsma.projeto_web.validador;

import java.io.Serializable;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.fsma.projeto_web.modelo.dao.ClienteDAO;
import br.com.fsma.projeto_web.modelo.negocio.Cliente;
import br.com.fsma.projeto_web.msg.Mensagem;
import br.com.vieira.verificador.VerificadorCPF;

@Named
@RequestScoped
public class ValidadorCliente implements Serializable {

	private static final long serialVersionUID = 1L;

	private Mensagem mensagem = new Mensagem();

	@Inject
	private ClienteDAO clienteDao;

	public boolean validaCliente(Cliente cliente) {
		if (clienteDao.existe(cliente)) {	
			if (numeroValido(cliente)) {
				if (VerificadorCPF.verificaCpf(cliente.getCpf())) {
					mensagem.addMessageSuccess("Mensagem do Sistema",
							"Cliente " + cliente.getNome() + " cadastrado com sucesso!");
					return true;
				} else {
					mensagem.addMessageError("Erro!", "CPF inválido");
				} 
			} else {
				mensagem.addMessageError("Erro!", "Número de telefone inválido (Se não tiver certeza do número, deixe em branco)");
			}
		} else {
			mensagem.addMessageError("Erro!", "CPF já cadastrado");
		}
		return false;

	}

	public boolean numeroValido(Cliente cliente) {
		String telefoneNumero = cliente.getTelefone().replaceAll("[^0-9]", "");
		if ((telefoneNumero.length() >= 10) || (telefoneNumero.length() == 0)) {
			return true;
		}
		return false;
	}
	
	public boolean semNumero (String numero) {
		if (numero.replaceAll("[^0-9]", "").isEmpty()) {
			return true;
		}
		return false;
	}

	public String celularOuFixo(Cliente cliente) {
		String telefone = cliente.getTelefone().replace("_", "");
		if (semNumero(cliente.getTelefone())) {
			return ("N/A");
		}
		return telefone;
	}
}
