package com.application.dao;

import com.application.model.Estoque;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.EntityTransaction;
import java.util.List;

public class EstoqueDAO implements IEstoqueDAO {

    private EntityManager entityManager;

    public EstoqueDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void atualizar(Estoque estoque) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.merge(estoque);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    @Override
    public Estoque buscarPorProduto(Integer produtoId) {
        TypedQuery<Estoque> query = entityManager.createQuery(
                "SELECT e FROM Estoque e WHERE e.produto.id = :produtoId",
                Estoque.class
        );
        query.setParameter("produtoId", produtoId);
        List<Estoque> result = query.getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public List<Estoque> listarComBaixoEstoque() {
        TypedQuery<Estoque> query = entityManager.createQuery(
                "SELECT e FROM Estoque e WHERE e.quantidade <= e.limiteMinimo",
                Estoque.class
        );
        return query.getResultList();
    }
}