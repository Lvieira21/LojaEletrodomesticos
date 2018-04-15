package br.com.fsma.projeto_web.msg;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Mensagem {

	public void addMessageSuccess(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void addMessageError(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
