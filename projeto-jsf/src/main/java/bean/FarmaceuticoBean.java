package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dao.DaoGeneric;
import dao.FarmaceuticoDAO;
import dao.LaboratorioDAO;
import model.Farmaceutico;
import model.Laboratorio;

@ManagedBean(name="farmBean")
@ViewScoped
public class FarmaceuticoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Farmaceutico farmaceutico = new Farmaceutico();
	private Laboratorio laboratorio;
	
	private DaoGeneric<Farmaceutico> daoGenericF = new DaoGeneric<Farmaceutico>();
	private FarmaceuticoDAO<Farmaceutico> daoFarmaceutico = new FarmaceuticoDAO<Farmaceutico>();
	private LaboratorioDAO<Laboratorio> daoLaboratorio = new LaboratorioDAO<Laboratorio>();
	private List<Farmaceutico> listaFarmaceuticos = new ArrayList<Farmaceutico>();
	
	private boolean existe = false;
	private boolean mostrarLabsDisponiveisBoolean = false;
	
	public void salvar() {
		farmaceutico = daoGenericF.salvarMerge(farmaceutico);
		atualizar();
		novoFarmaceutico();
	}
	
	public void novoFarmaceutico() {
		farmaceutico = new Farmaceutico();
		setExiste(false);
	}
	
	public void voltar() {
		novoFarmaceutico();
	}
	
	public void excluir() {
		if(listaFarmaceuticos.contains(farmaceutico)) {
			daoGenericF.excluirPorId(farmaceutico);
			getListDeFarmaceuticos();
		}
		novoFarmaceutico();
	}
	
	public void atualizar() {
		listaFarmaceuticos = daoFarmaceutico.getListaFarmaceuticos();
	}
	
	public void getListDeFarmaceuticos() {
		listaFarmaceuticos = daoGenericF.getListaEntidade(Farmaceutico.class);
	}
	
	public void addFarmaceuticoALaboratorio() {
		daoFarmaceutico.addFarmaceuticoALaboratorio(farmaceutico, laboratorio);
	}
	
	public List<Laboratorio> labsDoFarmaceutico(){
		return (List<Laboratorio>) farmaceutico.getContratosComFarmaceuticos();
	}
	
	public void removerFarmaceuticoDoLab() {
		daoFarmaceutico.removerFarmaceuticoDoLab(farmaceutico, laboratorio);
	}
	
	public List<Laboratorio> labsDisponiveis(){
		return daoLaboratorio.labsSemFarmaceutico();
	}
	
	
	public Farmaceutico getFarmaceutico() {
		return farmaceutico;
	}
	public void setFarmaceutico(Farmaceutico farmaceutico) {
		this.farmaceutico = farmaceutico;
	}
	public Laboratorio getLaboratorio() {
		return laboratorio;
	}
	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}

	public List<Farmaceutico> getListaFarmaceuticos() {
		return listaFarmaceuticos;
	}
	public void setListaFarmaceuticos(List<Farmaceutico> listaFarmaceuticos) {
		this.listaFarmaceuticos = listaFarmaceuticos;
	}

	public boolean isExiste() {
		return existe;
	}

	public void setExiste(boolean existe) {
		this.existe = existe;
	}

	public boolean isMostrarLabsDisponiveisBoolean() {
		return mostrarLabsDisponiveisBoolean;
	}

	public void setMostrarLabsDisponiveisBoolean(boolean mostrarLabsDisponiveisBoolean) {
		this.mostrarLabsDisponiveisBoolean = mostrarLabsDisponiveisBoolean;
	}

}
