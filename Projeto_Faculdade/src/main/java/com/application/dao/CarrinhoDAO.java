package com.application.dao;

import com.application.model.Carrinho;
import com.application.model.ItemCarrinho;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.EntityTransaction;
import java.util.List;

public class CarrinhoDAO implements ICarrinhoDAO {


    private EntityManager entityManager;

    public CarrinhoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public void salvar(Carrinho carrinho) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            if (carrinho.getId() == null) {
                entityManager.persist(carrinho);
            } else {
                entityManager.merge(carrinho);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    @Override
    public Carrinho buscarPorCliente(Integer clienteId) {
        TypedQuery<Carrinho> query = entityManager.createQuery(
                "SELECT c FROM Carrinho c WHERE c.cliente.id = :clienteId",
                Carrinho.class
        );
        query.setParameter("clienteId", clienteId);
        List<Carrinho> result = query.getResultList();
        if (result.isEmpty()) return null;
        return result.get(0);
    }

    @Override
    public void adicionarItem(ItemCarrinho item) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.persist(item);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    @Override
    public void removerItem(Integer itemId) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            ItemCarrinho item = entityManager.find(ItemCarrinho.class, itemId);
            if (item != null) {
                entityManager.remove(item);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
}