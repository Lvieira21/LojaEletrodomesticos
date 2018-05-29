package br.com.fsma.projeto_web.teste;

import br.com.fsma.projeto_web.modelo.negocio.Cliente;
import br.com.fsma.projeto_web.validador.ValidadorCliente;

public class TestaTelefone {
 
	public static void main(String[] args) {
		
		Cliente cliente = new Cliente();
		Cliente cliente2 = new Cliente();
		Cliente cliente3 = new Cliente();
		Cliente cliente4 = new Cliente();
		ValidadorCliente validador = new ValidadorCliente();
		
		
		cliente.setTelefone("(22) 9983-98683");
		cliente2.setTelefone("(22) 2772-1012_");
		cliente3.setTelefone("(22) 274_-____");
		cliente4.setTelefone("(__) ____-____");
	
		
		System.out.println(validador.numeroValido(cliente3));
		System.out.println(validador.numeroValido(cliente4));
		System.out.println(validador.numeroValido(cliente2));
		System.out.println(validador.numeroValido(cliente));
		
		System.out.println(validador.celularOuFixo(cliente));
		System.out.println(validador.celularOuFixo(cliente2));
		System.out.println(validador.celularOuFixo(cliente3));
		System.out.println(validador.celularOuFixo(cliente4));
	}
}
