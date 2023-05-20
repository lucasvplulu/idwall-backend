package com.idwall.app.criminoso;

import com.idwall.app.criminoso.dto.MandatoPrisaoDto;
import com.idwall.app.mandatoprisao.MandatoPrisao;
import com.idwall.app.util.enumeration.TipoInstituicao;
import com.idwall.app.util.enumeration.TipoSexo;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "criminoso")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Criminoso implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "CHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;
    private String nome;
    private String apelido;
    private LocalDate data_nascimento;
    private String local_nascimento;
    private TipoSexo sexo;
    private Double peso;
    private Double altura;
    private String marcas_distintivas;
    private String foto;
    private TipoInstituicao tipoInstituicao;
    @Transient
    private List<String> criminosoCorCabelos;
    @Transient
    private List<String> criminosoCorOlhos;
    @Transient
    private List<String> criminosoNacionalidades;
    @Transient
    private List<String> criminosoIdiomas;
    @Transient
    private List<MandatoPrisaoDto> mandatos;

}
