package com.application.dao;

import com.application.model.Produto;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.EntityTransaction;
import java.util.List;

public class ProdutoDAO implements IProdutoDAO {

    private EntityManager entityManager;

    public ProdutoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void cadastrar(Produto produto) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.persist(produto);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    @Override
    public void editar(Produto produto) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.merge(produto);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    @Override
    public void excluir(Integer id) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            Produto produto = entityManager.find(Produto.class, id);
            if (produto != null) {
                entityManager.remove(produto);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    @Override
    public Produto buscarPorId(Integer id) {
        return entityManager.find(Produto.class, id);
    }

    @Override
    public List<Produto> listar() {
        TypedQuery<Produto> query = entityManager.createQuery("SELECT p FROM Produto p", Produto.class);
        return query.getResultList();
    }

    @Override
    public List<Produto> buscarPorCategoria(Integer categoriaId) {
        TypedQuery<Produto> query = entityManager.createQuery(
                "SELECT p FROM Produto p WHERE p.categoria.id = :categoriaId",
                Produto.class
        );
        query.setParameter("categoriaId", categoriaId);
        return query.getResultList();
    }

    @Override
    public List<Produto> buscarPorNome(String nome) {
        TypedQuery<Produto> query = entityManager.createQuery(
                "SELECT p FROM Produto p WHERE LOWER(p.nome) LIKE :nome",
                Produto.class
        );
        query.setParameter("nome", "%" + nome.toLowerCase() + "%");
        return query.getResultList();
    }
}