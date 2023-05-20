package com.idwall.app.util.enumeration;

public enum TipoInstituicao {
    FBI ("FBI"),
    INTERPOL ("Interpol");

    private String descricao;

    private TipoInstituicao(String name) {
        this.descricao = name;
    }

    public String getDescricao() {
        return descricao;
    }
}
