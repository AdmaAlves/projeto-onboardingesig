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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.UniqueConstraint;


@Entity
public class Laboratorio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	private String codigo;
	
	@ManyToOne(targetEntity = Farmaceutico.class, cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_farmaceutico")
	private Farmaceutico farmaceutico;
	
	@ManyToMany(targetEntity = Medicamento.class, cascade = {CascadeType.MERGE, CascadeType.REMOVE},fetch = FetchType.EAGER)
	@JoinTable(name = "medicamentosCadastrados", uniqueConstraints = {@UniqueConstraint(columnNames = {"id_laboratorio", "id_medicamento"})},
			joinColumns = {@JoinColumn(name="id_laboratorio")}, inverseJoinColumns = {@JoinColumn(name="id_medicamento")} )
	private List<Medicamento> medicamentos;
	
	
	// ----------------- CONSTRUCTORS ---------------------------
	public Laboratorio() {
		medicamentos = new ArrayList<Medicamento>();
		farmaceutico = new Farmaceutico();
	}
	
	public Laboratorio(String nome, String codigo) {
		super();
		this.nome = nome;
		this.codigo = codigo;
	}
	// ----------------------------------------------------------
	// -------------- GETTERS AND SETTERS ------------------------
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Farmaceutico getFarmaceutico() {
		return farmaceutico;
	}

	public void setFarmaceutico(Farmaceutico farmaceutico) {
		this.farmaceutico = farmaceutico;
	}

	public List<Medicamento> getMedicamentos() {
		return medicamentos;
	}

	public void setMedicamentos(List<Medicamento> medicamentos) {
		this.medicamentos = medicamentos;
	}
	// ----------------------------------------------------------
	// ------------------ TO STRING -----------------------------
	@Override
	public String toString() {
		return "Laboratorio [id=" + id + ", nome=" + nome + ", codigo=" + codigo + "]";
	}
	// ---------------------------------------------------------
	// ------------------- EQUALS and HASH ---------------------
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
		Laboratorio other = (Laboratorio) obj;
		return id == other.id;
	}
	
}