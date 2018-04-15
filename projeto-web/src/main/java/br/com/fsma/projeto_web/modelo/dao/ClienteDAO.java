package br.com.fsma.projeto_web.modelo.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.fsma.projeto_web.modelo.negocio.Cliente;

@Named
@RequestScoped
public class ClienteDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	private DAO<Cliente> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<Cliente>(this.em, Cliente.class);
	}

	@Inject
	private EntityManager em;

	public void adiciona(Cliente cliente) {
		dao.adiciona(cliente);
	}

	public void atualiza(Cliente cliente) {
		em.merge(cliente);
	}

	public void remove(Cliente cliente) {
		dao.remove(cliente);
	}

	public Cliente buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public boolean existe(Cliente cliente) {
		TypedQuery<Cliente> query = em.createQuery(" select c from Cliente c " + " where c.cpf = :pCpf", Cliente.class);

		query.setParameter("pCpf", cliente.getCpf());
		try {
			@SuppressWarnings("unused")
			Cliente resultado = query.getSingleResult();
			return true;
		} catch (NoResultException ex) {
			return false;
		}

	}

	public boolean verificaCpf(Cliente cliente) {
		String cpf = cliente.getCpf().replaceAll("[^0-9]", "");
		char[] cpfArray = cpf.toCharArray();
		int[] cpfNumber = new int[cpfArray.length];
		for (int i = 0; i < cpfArray.length; i++) {
			cpfNumber[i] = Character.getNumericValue(cpfArray[i]);
		}
		if (testaDigitosIguais(cpfNumber)) {
			if (testaDigitoUm(cpfNumber)) {
				if (testaDigitoDois(cpfNumber)) {
					System.out.println("CPF válido");
					return true;
				}
			}
		}
		System.out.println("CPF inválido");
		return false;
	}

	public boolean testaDigitosIguais(int[] cpfNumber) {
		for (int i = 0; i < cpfNumber.length; i++) {
			if (cpfNumber[i] == cpfNumber[i + 1]) {
				if (i == cpfNumber.length-2)
					return false;
			}
			if (cpfNumber[i] != cpfNumber[i+1])
				return true;
		}
		return false;
	}

	public boolean testaDigitoUm(int[] cpfNumber) {
		Integer verificador = new Integer(0);
		for (int i = 0, j = 10; i < cpfNumber.length - 2; i++, j--) {
			verificador += (cpfNumber[i] * j);
		}
		verificador = (verificador * 10) % 11;
		if (verificador == 10)
			verificador = 0;
		if (verificador == cpfNumber[cpfNumber.length - 2]) {
			return true;
		}
		return false;
	}

	public boolean testaDigitoDois(int[] cpfNumber) {
		Integer verificador = new Integer(0);
		for (int i = 0, j = 11; i < cpfNumber.length - 1; i++, j--) {
			verificador += (cpfNumber[i] * j);
		}
		verificador = (verificador * 10) % 11;
		if (verificador == 10)
			verificador = 0;
		if (verificador == cpfNumber[cpfNumber.length - 1]) {
			return true;
		}
		return false;
	}

	public List<Cliente> listaTodos() {
		return dao.listaTodos();
	}
}
