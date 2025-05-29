package com.application.dao;

import com.application.model.CupomDesconto;
import java.util.List;

public interface ICupomDAO {
    void cadastrar(CupomDesconto cupom);
    void editar(CupomDesconto cupom);
    CupomDesconto buscarPorCodigo(String codigo);
    List<CupomDesconto> listarAtivos();
}