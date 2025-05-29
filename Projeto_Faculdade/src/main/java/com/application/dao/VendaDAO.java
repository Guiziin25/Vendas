package com.application.dao;

import com.application.model.Venda;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.EntityTransaction;
import java.util.Date;
import java.util.List;

public class VendaDAO implements IVendaDAO {

    private EntityManager entityManager;

    public VendaDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void cadastrar(Venda venda) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.persist(venda);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    @Override
    public Venda buscarPorId(Integer id) {
        return entityManager.find(Venda.class, id);
    }

    @Override
    public List<Venda> listar() {
        TypedQuery<Venda> query = entityManager.createQuery("SELECT v FROM Venda v", Venda.class);
        return query.getResultList();
    }

    @Override
    public List<Venda> listarPorCliente(Integer clienteId) {
        TypedQuery<Venda> query = entityManager.createQuery(
                "SELECT v FROM Venda v WHERE v.cliente.id = :clienteId",
                Venda.class
        );
        query.setParameter("clienteId", clienteId);
        return query.getResultList();
    }

    @Override
    public List<Venda> listarPorPeriodo(Date inicio, Date fim) {
        TypedQuery<Venda> query = entityManager.createQuery(
                "SELECT v FROM Venda v WHERE v.data BETWEEN :inicio AND :fim",
                Venda.class
        );
        query.setParameter("inicio", inicio);
        query.setParameter("fim", fim);
        return query.getResultList();
    }

    @Override
    public void editar(Venda venda) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.merge(venda);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
}