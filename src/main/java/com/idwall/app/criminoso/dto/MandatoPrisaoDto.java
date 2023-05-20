package com.idwall.app.criminoso.dto;

import com.idwall.app.util.enumeration.TipoSexo;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MandatoPrisaoDto {

    private String acusacao;
    private String paisEmissor;

}
