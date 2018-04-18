package br.com.fsma.projeto_web.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.fsma.projeto_web.modelo.dao.DAO;
import br.com.fsma.projeto_web.modelo.negocio.Cliente;

@FacesConverter(forClass = Cliente.class, value = "clienteConverter")
public class ClienteConverter implements Converter {

	@Inject
	EntityManager em;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if (arg2 == null || arg2.isEmpty()) {
			return null;
		} else {
			Long id = Long.parseLong(arg2);
			Cliente cliente = new DAO<Cliente>(em, Cliente.class).buscaPorId(id);
			return cliente;
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		Cliente cliente = (Cliente) arg2;
		if (cliente != null) {
			return String.valueOf(cliente.getId());
		}
		return null;
	}

}
