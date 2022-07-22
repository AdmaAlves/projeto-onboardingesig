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
import javax.persistence.OneToMany;


@Entity
public class Farmaceutico implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	private String crf;
	private String email;
	
	@OneToMany(mappedBy="farmaceutico",cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private List<Laboratorio> contratosComFarmaceutico;
	
	
	// --------------------- CONSTRUCTORS ----------------------------
	public Farmaceutico() {
		this.contratosComFarmaceutico = new ArrayList<Laboratorio>();
	}
	
	public Farmaceutico (String nome, String crf, String email) {
		super();
		this.nome = nome;
		this.crf = crf;
		this.email = email;
	}
	// ----------------------------------------------------------------
	// GETTERS AND SETTERS
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

	public String getCrf() {
		return crf;
	}

	public void setCrf(String crf) {
		this.crf = crf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Laboratorio> getContratosComFarmaceuticos() {
		return contratosComFarmaceutico;
	}

	public void setContratosComFarmaceuticos(List<Laboratorio> contratosComFarmaceutico) {
		this.contratosComFarmaceutico = contratosComFarmaceutico;
	}
	// ------------------------------------------------------------------
	// ------------------------ TO STRING -------------------------------
	@Override
	public String toString() {
		return "Farmaceutico [id=" + id + ", nome=" + nome + ", crf=" + crf + ", email=" + email + "]";
	}
	// -----------------------------------------------------------------
	//----------------------- EQUALS and HASH --------------------------
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
		Farmaceutico other = (Farmaceutico) obj;
		return id == other.id;
	}
}