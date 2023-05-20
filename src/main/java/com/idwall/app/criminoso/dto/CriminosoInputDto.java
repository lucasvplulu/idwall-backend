package com.idwall.app.criminoso.dto;

import com.idwall.app.util.enumeration.TipoInstituicao;
import com.idwall.app.util.enumeration.TipoSexo;
import lombok.*;

@Data
public class CriminosoInputDto {

    private String nome;
    private String apelido;
    private TipoSexo sexo;
    private TipoInstituicao instituicao;
    private Long idadeInicial;
    private Long idadeFinal;
    private String localNascimento;
    private int page = 1;
    private int size = 10;

}
