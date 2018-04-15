package br.com.fsma.projeto_web.teste;

import br.com.fsma.projeto_web.modelo.dao.ClienteDAO;
import br.com.fsma.projeto_web.modelo.negocio.Cliente;

public class TestaValidCPF {

	public static void main(String[] args) {

		Cliente cliente = new Cliente();
		Cliente cliente2 = new Cliente();
		Cliente cliente3 = new Cliente();
		ClienteDAO clienteDAO = new ClienteDAO();

		cliente.setCpf("057.954.837-11"); //Digito inválido
		cliente.setEndereco("Rua José");
		cliente.setNome("Lucas Vieira");
		
		cliente2.setCpf("111.111.111-11"); //Numeros Iguais
		cliente2.setEndereco("Rua José");
		cliente2.setNome("Lucas Vieira");
		

		cliente3.setCpf("057.954.837-61"); //Digito válido
		cliente3.setEndereco("Rua José");
		cliente3.setNome("Lucas Vieira");
		
		clienteDAO.verificaCpf(cliente);
		clienteDAO.verificaCpf(cliente2);
		clienteDAO.verificaCpf(cliente3);
	}
}
