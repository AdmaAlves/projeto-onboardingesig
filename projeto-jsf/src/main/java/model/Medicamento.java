package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Medicamento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String gtin;
	private String descricao;
	
	@ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private List<Laboratorio> labResponsavel;
	
	// ------------------ CONSTRUCTORS --------------------------------
	public Medicamento() {
		this.labResponsavel = new ArrayList<Laboratorio>();
	}
	
	public Medicamento(String nome, String gtin, String descricao, Double preco) {
		super();
		this.nome = nome;
		this.gtin = gtin;
		this.descricao = descricao;
	}
	// -----------------------------------------------------------------------
	// ----------------- GETTERS AND SETTERS ---------------------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getGtin() {
		return gtin;
	}

	public void setGtin(String gtin) {
		this.gtin = gtin;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Laboratorio> getLabResponsavel() {
		return labResponsavel;
	}

	public void setLabResponsavel(List<Laboratorio> labResponsavel) {
		this.labResponsavel = labResponsavel;
	}
	// ---------------------------------------------------------------------
	// ------------------------ TO STRING ----------------------------------
	@Override
	public String toString() {
		return "Medicamento [id=" + id + ", nome=" + nome + ", gtin="
	+ gtin + ", descricao=" + descricao + ", preco=" + "]";
	}
	//------------------------------------------------------------------------
	// ------------------ EQUALS and HASH ------------------------------------
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Medicamento other = (Medicamento) obj;
		return id == other.id;
	}
	
}
