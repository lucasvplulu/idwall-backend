package com.idwall.app.criminoso;

import com.idwall.app.criminoso.dto.CriminosoInputDto;
import com.idwall.app.criminosocorcabelo.CriminosoCorCabeloRepository;
import com.idwall.app.criminosocorolho.CriminosoCorOlhoRepository;
import com.idwall.app.criminosoidiomas.CriminosoIdiomasRepository;
import com.idwall.app.criminosonacionalidade.CriminosoNacionalidadeRepository;
import com.idwall.app.mandatoprisao.MandatoPrisaoRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class CriminosoRepositoryCustomImpl implements CriminosoRepositoryCustom {
    @Autowired
    EntityManager entityManager;

    @Autowired
    private CriminosoCorOlhoRepository criminosoCorOlhoRepository;
    @Autowired
    private CriminosoNacionalidadeRepository crimonosoNacionalidadeRepository;
    @Autowired
    private CriminosoCorCabeloRepository criminosoCorCabeloRepository;
    @Autowired
    private CriminosoIdiomasRepository criminosoIdiomasRepository;
    @Autowired
    private MandatoPrisaoRepository mandatoPrisaoRepository;

    public List<Criminoso> findByFilter(CriminosoInputDto inputDto) {
        var query = "SELECT c FROM Criminoso c ";
        var queryParams = new ArrayList<String>();

        if (inputDto.getNome() != null) {
            queryParams.add("c.nome LIKE '%" + inputDto.getNome() + "%'");
        }

        if (inputDto.getApelido() != null) {
            queryParams.add("c.apelido LIKE '%" + inputDto.getApelido() + "%'");
        }

        if (inputDto.getSexo() != null) {
            queryParams.add("c.sexo = " + inputDto.getSexo().ordinal());
        }

        if (inputDto.getInstituicao() != null) {
            queryParams.add("c.tipoInstituicao = " + inputDto.getInstituicao().ordinal());
        }

        if (inputDto.getIdadeInicial() != null && inputDto.getIdadeFinal() != null) {
            var inicio = LocalDate.now().minusYears((long) inputDto.getIdadeInicial());
            var fim = LocalDate.now().minusYears((long) inputDto.getIdadeFinal());
            queryParams.add("c.dataNascimento BETWEEN '" + fim + "' and '" + inicio + "'");
        }

        if (inputDto.getLocalNascimento() != null) {
            queryParams.add("c.localNascimento = ".concat(inputDto.getLocalNascimento()));
        }

        if (queryParams.size() > 0) {
            query = query.concat("where " + String.join(" and ", queryParams));
        }

        return entityManager
                .createQuery(query)
                .getResultList();

    }


}
