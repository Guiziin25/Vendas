package com.application.service;

import com.application.model.CupomDesconto;

import java.util.List;

public interface ICupomService {
    void criarCupom(CupomDesconto cupom);

    CupomDesconto validarCupom(String codigo);

    void desativarCupom(String codigo);

    List<CupomDesconto> listarCuponsAtivos();
}