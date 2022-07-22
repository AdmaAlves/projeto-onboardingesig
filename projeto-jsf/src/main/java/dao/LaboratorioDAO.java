package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import JPAUtil.JPAUtil;
import model.Farmaceutico;
import model.Laboratorio;
import model.Medicamento;

public class LaboratorioDAO<E> extends DaoGeneric<Laboratorio> {

	private List<Laboratorio> laboratorios;
	
	// ADICIONAR FARMACEUTICO AO LABORATORIO
	public void addFarmaceuticoNoLaboratorio(Farmaceutico farmaceutico, Laboratorio laboratorio) {
		laboratorio.setFarmaceutico(farmaceutico);
		farmaceutico.getContratosComFarmaceuticos().add(laboratorio);
		atualizar(laboratorio);
	}
	
	// ENCONTRAR POR ID
	public Laboratorio encontrarPorId(Long id) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		Laboratorio laboratorioEncontrado = null;
		if(entityManager.isOpen()) {
			laboratorioEncontrado = entityManager.find(Laboratorio.class, id);
		}

		return laboratorioEncontrado;	
	}
	
	// REMOVER FARMACEUTICO DO LABORTORIO
	public void removerFarmaceuticoDoLab(Laboratorio laboratorio) {
		if(laboratorio.getFarmaceutico() != null) {
			FarmaceuticoDAO<Farmaceutico> daoFarmaceutico = new FarmaceuticoDAO<Farmaceutico>();
			Farmaceutico farmaceuticoSelecionado = daoFarmaceutico.encontrarPorId(laboratorio.getFarmaceutico().getId());
			farmaceuticoSelecionado.getContratosComFarmaceuticos().remove(laboratorio);
			
			Laboratorio laboratorioSelecionado = encontrarPorId(laboratorio.getId());
			laboratorioSelecionado.setFarmaceutico(null);
			
			daoFarmaceutico.atualizar(farmaceuticoSelecionado);
			daoFarmaceutico.retornaFarmaceutico();
			atualizar(laboratorioSelecionado);
		}
	}
	
	// ATUALIZAR
	public Laboratorio atualizar(Laboratorio laboratorio) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try{
			entityTransaction.begin();
			laboratorio = entityManager.merge(laboratorio);
			entityManager.flush();
			
			entityTransaction.commit();
			entityManager.close();
		} catch(Exception ex) {
			entityManager.getTransaction().rollback();
		}
		return laboratorio;
	}
	
	// ORDENAR
	private void ordenarLaboratorioPorNome() {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Laboratorio> criteria = cb.createQuery(Laboratorio.class);
		Root<Laboratorio> laboratorio = criteria.from(Laboratorio.class);
		criteria.select(laboratorio).orderBy(cb.asc(laboratorio.get("nome")));
		laboratorios = entityManager.createQuery(criteria).getResultList();
		
		entityTransaction.commit();
		entityManager.close();
	}
	
	// GET
	public List<Laboratorio> getLaboratorios() {
		ordenarLaboratorioPorNome();
		return laboratorios;
	}
	
	// LISTA DE MEDICAMENTOS CADASTRADOS NO LABORATÓRIO
	public List<Medicamento> MedicamentosCadastradosNoLab(Laboratorio laboratorio){
		return (List<Medicamento>) laboratorio.getMedicamentos();
	}
	
	// LABORATÓRIOS SEM FARMACÊUTICO
	public List<Laboratorio> labsSemFarmaceutico() {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		List<Laboratorio> listaLabsSemFarmaceutico = new ArrayList<Laboratorio>();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Laboratorio> criteria = cb.createQuery(Laboratorio.class);
		Root<Laboratorio> laboratorio = criteria.from(Laboratorio.class);
		
		criteria.select(laboratorio).where(cb.isNull(laboratorio.get("farmaceutico")))
								.orderBy(cb.asc(laboratorio.get("nome")));
		listaLabsSemFarmaceutico.addAll(entityManager.createQuery(criteria).getResultList());
		
		entityTransaction.commit();
		entityManager.close();
		
		return listaLabsSemFarmaceutico;
	}
}
