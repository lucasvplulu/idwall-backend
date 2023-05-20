package com.idwall.app.util.enumeration;

public enum TipoSexo {
    M ("MASCULINO"),
    F ("FEMININO");

    private String descricao;

    private TipoSexo(String name) {
        this.descricao = name;
    }

    public String getDescricao() {
        return descricao;
    }
}
