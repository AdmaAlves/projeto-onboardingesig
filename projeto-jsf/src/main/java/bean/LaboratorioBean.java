package bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dao.DaoGeneric;
import dao.FarmaceuticoDAO;
import dao.LaboratorioDAO;
import dao.MedicamentoDAO;
import model.Farmaceutico;
import model.Laboratorio;
import model.Medicamento;

@ManagedBean(name="labBean")
@ViewScoped
public class LaboratorioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Laboratorio laboratorio = new Laboratorio();
	private Medicamento medicamento;
	private Farmaceutico farmaceutico;
	
	private LaboratorioDAO<Laboratorio> daoLaboratorio = new LaboratorioDAO<Laboratorio>();
	private MedicamentoDAO<Medicamento> daoMedicamento;
	private FarmaceuticoDAO<Farmaceutico> daoFarmaceutico = new FarmaceuticoDAO<Farmaceutico>();
	private DaoGeneric<Laboratorio> daoGenericL = new DaoGeneric<Laboratorio>();

	private boolean existe = false;
	private boolean mostrarFarmaceuticoBoolean = false;
	private boolean mostrarMedicamentoBoolean = false;

	private List<Laboratorio> listaLaboratorios;

	public void salvar() {
		laboratorio = daoGenericL.salvarMerge(laboratorio);
		listaLaboratorios.add(laboratorio);
		listarLaboratorios();
	}

	public void novoLaboratorio() {
		laboratorio = new Laboratorio();
		existe = false;
		mostrarMedicamentoBoolean = false;
		mostrarFarmaceuticoBoolean = false;
	}

	public void excluir() {
		if (listaLaboratorios.contains(laboratorio)) {
			daoGenericL.excluirPorId(laboratorio);
			listaLaboratorios.remove(laboratorio);

		}
		novoLaboratorio();

	}

	public void voltar() {
		novoLaboratorio();
	}

	@PostConstruct
	public void listarLaboratorios() {
		listaLaboratorios = daoGenericL.getListaEntidade(Laboratorio.class);
	}

	public void removerMedicamentoDoLaboratorio() {
		daoMedicamento.removerLabMedicamento(laboratorio, medicamento);
	}

	public void podeMostrarMedicamentoBoolean() {
		mostrarMedicamentoBoolean = !mostrarMedicamentoBoolean;
	}

	public void podeMostrarFarmaceuticoBoolean() {
		mostrarFarmaceuticoBoolean = !mostrarFarmaceuticoBoolean;
	}

	public void addFarmaceutico() {
		daoLaboratorio.addFarmaceuticoNoLaboratorio(farmaceutico, laboratorio);
		podeMostrarFarmaceuticoBoolean();
	}

	public void removerFarmaceuticoDoLaboratorio() {
		daoLaboratorio.removerFarmaceuticoDoLab(laboratorio);
	}

	public List<Farmaceutico> farmaceuticosDisponiveis() {
		return daoFarmaceutico.retornaFarmaceutico();
	}

	public Laboratorio getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}

	public Medicamento getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}

	public Farmaceutico getFarmaceutico() {
		return farmaceutico;
	}

	public void setFarmaceutico(Farmaceutico farmaceutico) {
		this.farmaceutico = farmaceutico;
	}

	public boolean isExiste() {
		return existe;
	}

	public void setExiste(boolean existe) {
		this.existe = existe;
	}

	public boolean isMostrarFarmaceuticoBoolean() {
		return mostrarFarmaceuticoBoolean;
	}

	public void setMostrarFarmaceuticoBoolean(boolean mostrarFarmaceuticoBoolean) {
		this.mostrarFarmaceuticoBoolean = mostrarFarmaceuticoBoolean;
	}

	public boolean isMostrarMedicamentoBoolean() {
		return mostrarMedicamentoBoolean;
	}

	public void setMostrarMedicamentoBoolean(boolean mostrarMedicamentoBoolean) {
		this.mostrarMedicamentoBoolean = mostrarMedicamentoBoolean;
	}

	public List<Laboratorio> getListaLaboratorios() {
		return listaLaboratorios;
	}

	public void setListaLaboratorios(List<Laboratorio> listaLaboratorios) {
		this.listaLaboratorios = listaLaboratorios;
	}

}
