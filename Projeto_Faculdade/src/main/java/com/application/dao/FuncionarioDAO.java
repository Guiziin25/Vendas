package com.application.dao;


import com.application.model.Funcionario;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.EntityTransaction;
import java.util.List;

public class FuncionarioDAO implements IFuncionarioDAO {

    private EntityManager entityManager;

    public FuncionarioDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void cadastrar(Funcionario funcionario) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.persist(funcionario);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    @Override
    public void editar(Funcionario funcionario) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.merge(funcionario);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    @Override
    public Funcionario buscarPorId(Integer id) {
        return entityManager.find(Funcionario.class, id);
    }

    @Override
    public Funcionario buscarPorEmail(String email) {
        TypedQuery<Funcionario> query = entityManager.createQuery(
                "SELECT f FROM Funcionario f WHERE f.email = :email", Funcionario.class
        );
        query.setParameter("email", email);
        List<Funcionario> result = query.getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public List<Funcionario> listar() {
        TypedQuery<Funcionario> query = entityManager.createQuery("SELECT f FROM Funcionario f", Funcionario.class);
        return query.getResultList();
    }
}