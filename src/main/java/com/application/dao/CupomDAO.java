package com.application.dao;

import com.application.model.CupomDesconto;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.EntityTransaction;
import java.util.Date;
import java.util.List;

public class CupomDAO implements ICupomDAO {

    private EntityManager entityManager;

    public CupomDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void cadastrar(CupomDesconto cupom) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.persist(cupom);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    @Override
    public void editar(CupomDesconto cupom) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.merge(cupom);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    @Override
    public CupomDesconto buscarPorCodigo(String codigo) {
        TypedQuery<CupomDesconto> query = entityManager.createQuery(
                "SELECT c FROM CupomDesconto c WHERE c.codigo = :codigo",
                CupomDesconto.class
        );
        query.setParameter("codigo", codigo);
        List<CupomDesconto> result = query.getResultList();
        if (result.isEmpty()) return null;
        return result.get(0);
    }

    @Override
    public List<CupomDesconto> listarAtivos() {
        TypedQuery<CupomDesconto> query = entityManager.createQuery(
                "SELECT c FROM CupomDesconto c WHERE c.ativo = true",
                CupomDesconto.class
        );
        return query.getResultList();
    }
}