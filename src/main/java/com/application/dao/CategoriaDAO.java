package com.application.dao;

import com.application.dto.CategoriaDto;
import com.application.model.Categoria;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.EntityTransaction;
import java.util.List;

public class CategoriaDAO implements ICategoriaDAO {
    private EntityManager entityManager;

    public CategoriaDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void cadastrar(CategoriaDto categoriaDto) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            Categoria categoria = toEntity(categoriaDto); // Converte CategoriaDto para Categoria
            entityManager.persist(categoria); // Persiste a nova categoria
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback(); // Rollback em caso de erro
            throw e; // Lança a exceção para tratamento externo
        }
    }

    @Override
    public void editar(CategoriaDto categoriaDto) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            Categoria categoria = toEntity(categoriaDto); // Converte CategoriaDto para Categoria
            entityManager.merge(categoria); // Atualiza a categoria existente
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback(); // Rollback em caso de erro
            throw e; // Lança a exceção para tratamento externo
        }
    }

    @Override
    public void excluir(Integer id) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            Categoria categoria = entityManager.find(Categoria.class, id); // Busca a categoria pelo ID
            if (categoria != null) {
                entityManager.remove(categoria); // Remove a categoria se encontrada
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback(); // Rollback em caso de erro
            throw e; // Lança a exceção para tratamento externo
        }
    }

    @Override
    public CategoriaDto buscarPorId(Integer id) {
        Categoria categoria = entityManager.find(Categoria.class, id); // Busca a categoria pelo ID
        return toDto(categoria); // Converte Categoria para CategoriaDto
    }

    @Override
    public List<CategoriaDto> listar() {
        TypedQuery<Categoria> query = entityManager.createQuery("SELECT c FROM Categoria c", Categoria.class);
        List<Categoria> categorias = query.getResultList(); // Retorna a lista de todas as categorias
        return categorias.stream().map(this::toDto).toList(); // Converte para List<CategoriaDto>
    }

    // Método auxiliar para converter CategoriaDto em Categoria
    private Categoria toEntity(CategoriaDto dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());
        return categoria;
    }


    // Metodo auxiliar para converter Categoria em CategoriaDto
    private CategoriaDto toDto(Categoria categoria) {
        if (categoria == null) return null; // Verifica se a categoria é nula
        CategoriaDto dto = new CategoriaDto();
        dto.setId(categoria.getId());
        dto.setNome(categoria.getNome());
        dto.setDescricao(categoria.getDescricao());
        return dto;
    }
}