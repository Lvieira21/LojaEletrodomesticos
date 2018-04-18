package br.com.fsma.projeto_web.teste;

import br.com.fsma.projeto_web.modelo.dao.ClienteDAO;
import br.com.fsma.projeto_web.modelo.negocio.Cliente;

public class TestaTelefone {
 
	public static void main(String[] args) {
		
		Cliente cliente = new Cliente();
		Cliente cliente2 = new Cliente();
		Cliente cliente3 = new Cliente();
		ClienteDAO clienteDAO = new ClienteDAO();
		
		cliente.setTelefone("(22) 9983-98683");
		cliente2.setTelefone("(22) 2772-1012_");
		cliente3.setTelefone("(22) 274_-____");
	
		clienteDAO.celularOuFixo(cliente);
		clienteDAO.celularOuFixo(cliente2);
		clienteDAO.celularOuFixo(cliente3);
	}
}
