package bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dao.DaoGeneric;
import dao.LaboratorioDAO;
import dao.MedicamentoDAO;
import model.Laboratorio;
import model.Medicamento;


@ManagedBean(name = "medBean")
@ViewScoped
public class MedicamentoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private MedicamentoDAO<Medicamento> daoMedicamento = new MedicamentoDAO<Medicamento>();
	private DaoGeneric<Medicamento> daoGenericM = new DaoGeneric<Medicamento>();
	private LaboratorioDAO<Laboratorio> daoLaboratorio = new LaboratorioDAO<Laboratorio>();
	
	private List<Medicamento> listaMedicamentos;
	private Laboratorio laboratorio = new Laboratorio();
	private Medicamento medicamento = new Medicamento();
	
	private boolean existe = false;
	private boolean labsBoolean = false;
	
	public void salvar() {
		daoGenericM.salvarMerge(medicamento);
		listaMedicamentos.add(medicamento);
		listarMedicamentos();
	}
	
	public void novoMedicamento() {
		medicamento = new Medicamento();
		existe = false;
		labsBoolean = false;
	}
	
	public void excluir() {
		if(listaMedicamentos.contains(medicamento)) {
			daoGenericM.excluirPorId(medicamento);
			listaMedicamentos.remove(medicamento);
		}
		novoMedicamento();
	}
	
	public void voltar() {
		novoMedicamento();
	}
	
	@PostConstruct
	public void listarMedicamentos() {
		listaMedicamentos = daoGenericM.getListaEntidade(Medicamento.class);
	}
	
	public void cadastrarMedicamentoNoLaboratorio() {
		daoMedicamento.cadastrarMedicamentoEmLaboratorio(medicamento, laboratorio);
	}
	
	public void removerLaboratorioDoMedicamento() {
		daoMedicamento.removerLabMedicamento(laboratorio, medicamento);
	}
	
	public List<Laboratorio> labsDisponiveis(){
		List<Laboratorio> laboratoriosDisponiveis = daoLaboratorio.getLaboratorios();
		laboratoriosDisponiveis.removeAll(medicamento.getLabResponsavel());
		return laboratoriosDisponiveis;
	}
	
	public List<Laboratorio> labReponsavelId(){
		return medicamento.getLabResponsavel();
	}

	public Medicamento getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
		existe = true;
		labsBoolean = false;
	}

	public List<Medicamento> getListaMedicamentos() {
		return listaMedicamentos;
	}


	public void setListaMedicamentos(List<Medicamento> listaMedicamentos) {
		this.listaMedicamentos = listaMedicamentos;
	}


	public boolean isExiste() {
		return existe;
	}


	public boolean isLabsBoolean() {
		return labsBoolean;
	}
	
	public void mudarLabsBoolean() {
		labsBoolean = !labsBoolean;
	}

	public MedicamentoDAO<Medicamento> getDaoMedicamento() {
		return daoMedicamento;
	}

	public DaoGeneric<Medicamento> getDaoGenericM() {
		return daoGenericM;
	}


	public Laboratorio getLaboratorio() {
		return laboratorio;
	}


	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}
	
	
}
