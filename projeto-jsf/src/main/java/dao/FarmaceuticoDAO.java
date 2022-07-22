package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import JPAUtil.JPAUtil;
import model.Farmaceutico;
import model.Laboratorio;

public class FarmaceuticoDAO<E> extends DaoGeneric<Farmaceutico> {
	
	private List<Farmaceutico> listaFarmaceuticos;
	private LaboratorioDAO<Laboratorio> daoLaboratorio = new LaboratorioDAO<Laboratorio>();
	
	
	public FarmaceuticoDAO<E> removerFarmaceuticoDoLab(Farmaceutico farmaceutico, Laboratorio laboratorio){
		if(laboratorio.getFarmaceutico().equals(farmaceutico)) {
			daoLaboratorio = new LaboratorioDAO<Laboratorio>();
			daoLaboratorio.removerFarmaceuticoDoLab(laboratorio);
		}
		getListaFarmaceuticos();
		return this;
	}
	
	public List<Farmaceutico> getListaFarmaceuticos() {
		return listaFarmaceuticos;
	}
	
	public Farmaceutico atualizar(Farmaceutico farmaceutico) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try{
			entityTransaction.begin();
			farmaceutico = entityManager.merge(farmaceutico);
			entityManager.flush();
			
			entityTransaction.commit();
			entityManager.close();
		} catch(Exception ex) {
			entityManager.getTransaction().rollback();
		}
		return farmaceutico;
	}
	
	public Farmaceutico cadastrarFarmaceuticoNoLab(Farmaceutico farmaceutico, Laboratorio laboratorio) {
		Farmaceutico farmaceuticoSelecionado = encontrarPorId(farmaceutico.getId());
		if(farmaceuticoSelecionado != null) {
			daoLaboratorio = new LaboratorioDAO<Laboratorio>();
			Laboratorio laboratorioTeste = daoLaboratorio.encontrarPorId(laboratorio.getId());
			if(laboratorioTeste != null) {
				boolean labNaoTemFarmaceutico = !farmaceuticoSelecionado.getContratosComFarmaceuticos().contains(laboratorioTeste);
				
				if(labNaoTemFarmaceutico) {
					farmaceuticoSelecionado.getContratosComFarmaceuticos().add(laboratorioTeste);
				}
				daoLaboratorio.salvarMerge(laboratorioTeste);
				farmaceuticoSelecionado = salvarMerge(farmaceuticoSelecionado);
				daoLaboratorio.getLaboratorios();
				retornaFarmaceutico();
			}
		}
		return farmaceuticoSelecionado;
	}
	
	public Farmaceutico encontrarPorId(Long id) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		Farmaceutico farmaceuticoEncontrado = null;
		if(entityManager.isOpen()) {
			farmaceuticoEncontrado = entityManager.find(Farmaceutico.class, id);
		}
		entityTransaction.commit();
		entityManager.close();
		return farmaceuticoEncontrado;
	}
	
	public List<Farmaceutico> retornaFarmaceutico(){
		pegarFarmaceuticoPorCrf();
		return listaFarmaceuticos;
	}
	
	private void pegarFarmaceuticoPorCrf() {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Farmaceutico> criteria = cb.createQuery(Farmaceutico.class);
		Root<Farmaceutico> farmaceutico = criteria.from(Farmaceutico.class);
		criteria.select(farmaceutico).orderBy(cb.asc(farmaceutico.get("crf")));
		listaFarmaceuticos = entityManager.createQuery(criteria).getResultList();
		
		entityTransaction.commit();
		entityManager.close();
		
	}
	
	public void addFarmaceuticoALaboratorio(Farmaceutico farmaceutico, Laboratorio laboratorio) {
		farmaceutico.getContratosComFarmaceuticos().add(laboratorio);
		laboratorio.setFarmaceutico(farmaceutico);
		atualizar(farmaceutico);
		daoLaboratorio.atualizar(laboratorio);
	}
	
	public List<Laboratorio> labsMinistradosID(Farmaceutico farmaceutico){
		return (List<Laboratorio>) encontrarPorId(farmaceutico.getId()).getContratosComFarmaceuticos();
	}

}
