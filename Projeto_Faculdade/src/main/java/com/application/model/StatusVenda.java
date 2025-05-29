package com.application.model;

public enum StatusVenda {
    PENDENTE,
    PAGO,
    ENVIADO,
    ENTREGUE,
    CANCELADO;

    public static boolean isValid(String status) {
        for (StatusVenda s : StatusVenda.values()) {
            if (s.name().equals(status)) {
                return true;
            }
        }
        return false;
    }
    }