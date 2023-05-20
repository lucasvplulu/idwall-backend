package com.idwall.app.criminosocorolho;

import com.idwall.app.criminoso.Criminoso;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "criminoso_cor_olho")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CriminosoCorOlho implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "CHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;
    private String sigla;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "criminoso")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Criminoso criminoso;

}
