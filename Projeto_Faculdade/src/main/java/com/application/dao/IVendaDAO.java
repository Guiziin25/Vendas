package com.application.dao;

import com.application.model.Venda;
import java.util.Date;
import java.util.List;

public interface IVendaDAO {
    void cadastrar(Venda venda);
    Venda buscarPorId(Integer id);
    List<Venda> listar();
    List<Venda> listarPorCliente(Integer clienteId);
    List<Venda> listarPorPeriodo(Date inicio, Date fim);
    void editar(Venda venda);
}