package com.application.service;

import com.application.dao.ICupomDAO;
import com.application.model.CupomDesconto;

import java.util.List;

public class CupomService implements ICupomService {

    private ICupomDAO cupomDAO;

    public CupomService(ICupomDAO cupomDAO) {
        this.cupomDAO = cupomDAO;
    }

    @Override
    public void criarCupom(CupomDesconto cupom) {
        cupomDAO.cadastrar(cupom);
    }

    @Override
    public CupomDesconto validarCupom(String codigo) {
        CupomDesconto cupom = cupomDAO.buscarPorCodigo(codigo);
        if (cupom != null && cupom.isAtivo()) {
            return cupom;
        }
        return null;
    }

    @Override
    public void desativarCupom(String codigo) {
        CupomDesconto cupom = cupomDAO.buscarPorCodigo(codigo);
        if (cupom != null) {
            cupom.setAtivo(false);
            cupomDAO.editar(cupom);
        }
    }

    @Override
    public List<CupomDesconto> listarCuponsAtivos() {
        return cupomDAO.listarAtivos();
    }
}