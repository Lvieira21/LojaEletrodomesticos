package br.com.fsma.projeto_web.modelo.negocio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
		
	@Column(nullable=false)
	private String nome;
	@Column(nullable=false)
	private String modelo;
	@Column(nullable=false)
	private String marca;
	@Column(nullable=false)
	private Integer qtd;
	@Column(nullable=false)
	private BigDecimal valorUnitario;
	
	@OneToMany(mappedBy="produto")
	private List<ProdutoVenda> produtoVenda;
	

	public List<ProdutoVenda> getProdutoVenda() {
		return produtoVenda;
	}

	public void setProdutoVenda(List<ProdutoVenda> produtoVenda) {
		this.produtoVenda = produtoVenda;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getModelo() {
		return modelo;
	}

	public String getMarca() {
		return marca;
	}

	public Integer getQtd() {
		return qtd;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setQtd(Integer qtd) {
		this.qtd = qtd;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((marca == null) ? 0 : marca.hashCode());
		result = prime * result + ((modelo == null) ? 0 : modelo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (marca == null) {
			if (other.marca != null)
				return false;
		} else if (!marca.equals(other.marca))
			return false;
		if (modelo == null) {
			if (other.modelo != null)
				return false;
		} else if (!modelo.equals(other.modelo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Produto [id=" + id + ", nome=" + nome + ", modelo=" + modelo + ", marca=" + marca + ", qtd=" + qtd
				+ ", valorUnitario=" + valorUnitario + ", produtoVenda=" + produtoVenda + "]";
	}
	
}