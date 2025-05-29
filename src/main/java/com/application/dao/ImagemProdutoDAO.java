package com.application.dao;

import com.application.model.ImagemProduto;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.EntityTransaction;
import java.util.List;

public class ImagemProdutoDAO implements IImagemProdutoDAO {

    private EntityManager entityManager;

    public ImagemProdutoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void cadastrar(ImagemProduto imagemProduto) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.persist(imagemProduto);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    @Override
    public void editar(ImagemProduto imagemProduto) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.merge(imagemProduto);
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
            ImagemProduto imagemProduto = entityManager.find(ImagemProduto.class, id);
            if (imagemProduto != null) {
                entityManager.remove(imagemProduto);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    @Override
    public ImagemProduto buscarPorId(Integer id) {
        return entityManager.find(ImagemProduto.class, id);
    }

    @Override
    public List<ImagemProduto> listarPorProduto(Integer produtoId) {
        TypedQuery<ImagemProduto> query = entityManager.createQuery(
                "SELECT i FROM ImagemProduto i WHERE i.produto.id = :produtoId",
                ImagemProduto.class);
        query.setParameter("produtoId", produtoId);
        return query.getResultList();
    }
}