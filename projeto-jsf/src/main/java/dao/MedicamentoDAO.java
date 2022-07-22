package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import JPAUtil.JPAUtil;
import model.Laboratorio;
import model.Medicamento;

public class MedicamentoDAO<E> extends DaoGeneric<Medicamento> {
	
	private List<Medicamento> medicamentos = new ArrayList<Medicamento>();
	
	public Medicamento encontrarPorId(Long id) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		Medicamento medicamentoEncontrado = entityManager.find(Medicamento.class, id);

		entityTransaction.commit();
		entityManager.close();

		return medicamentoEncontrado;
	}
	
	public Medicamento removerLabMedicamento(Laboratorio laboratorio, Medicamento medicamento) {
		try {
			EntityManager entityManager = JPAUtil.getEntityManager();
			EntityTransaction entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			
			medicamento = entityManager.merge(medicamento);
			laboratorio = entityManager.merge(laboratorio);
			
			medicamento.getLabResponsavel().remove(laboratorio);
			laboratorio.getMedicamentos().remove(medicamento);
			
			entityTransaction.commit();
			entityManager.close();
			
		}catch (Exception ex){
			ex.printStackTrace();
		}
		retornaMedicamentos();
		return medicamento;
	}
	
	public List<Medicamento> retornaMedicamentos(){
		pegarMedicamentosPorNome();
		return medicamentos;
	}
	
	private void pegarMedicamentosPorNome() {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Medicamento> criteria = cb.createQuery(Medicamento.class);
		Root<Medicamento> medicamento = criteria.from(Medicamento.class);
		criteria.select(medicamento).orderBy(cb.asc(medicamento.get("nome")));
		medicamentos = entityManager.createQuery(criteria).getResultList();
		
		entityTransaction.commit();
		entityManager.close();
	}
	
	public Medicamento cadastrarMedicamentoEmLaboratorio(Medicamento medicamento, Laboratorio laboratorio) {
		Medicamento medicamentoSelecionado = encontrarPorId(medicamento.getId());
		if(medicamentoSelecionado != null) {
			LaboratorioDAO<Laboratorio> daoLaboratorio = new LaboratorioDAO<Laboratorio>();
			Laboratorio laboratorioSelecionado = daoLaboratorio.encontrarPorId(laboratorio.getId());
			if(laboratorioSelecionado != null) {
				boolean medicamentoNaoEstaNoLaboratorio = !laboratorioSelecionado.getMedicamentos().contains(medicamentoSelecionado);
				boolean laboratorioNaoTemMedicamentoSelecionado = !medicamentoSelecionado.getLabResponsavel().contains(laboratorioSelecionado);
				
				if(medicamentoNaoEstaNoLaboratorio) {
					laboratorioSelecionado.getMedicamentos().add(medicamentoSelecionado);
				}
				
				if(laboratorioNaoTemMedicamentoSelecionado) {
					medicamentoSelecionado.getLabResponsavel().add(laboratorioSelecionado);
				}
				daoLaboratorio.salvarMerge(laboratorioSelecionado);
				medicamentoSelecionado = salvarMerge(medicamentoSelecionado);
				daoLaboratorio.getLaboratorios();
				retornaMedicamentos();
			}
		}
		return medicamentoSelecionado;
	}


}
