package com.idwall.app.mandatoprisao;

import com.idwall.app.criminoso.Criminoso;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.w3c.dom.Text;

import javax.persistence.*;
import java.awt.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "mandato_prisao")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MandatoPrisao implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "CHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;
    private String paisEmissor;

    @Column(name = "acusacao", columnDefinition = "TEXT")
    private String acusacao;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "criminoso")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Criminoso criminoso;

}
