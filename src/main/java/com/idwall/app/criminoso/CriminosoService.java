package com.idwall.app.criminoso;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idwall.app.criminoso.dto.CriminosoInputDto;
import com.idwall.app.criminoso.dto.MandatoPrisaoDto;
import com.idwall.app.criminosocorcabelo.CriminosoCorCabelo;
import com.idwall.app.criminosocorcabelo.CriminosoCorCabeloRepository;
import com.idwall.app.criminosocorolho.CriminosoCorOlho;
import com.idwall.app.criminosocorolho.CriminosoCorOlhoRepository;
import com.idwall.app.criminosoidiomas.CriminosoIdiomas;
import com.idwall.app.criminosoidiomas.CriminosoIdiomasRepository;
import com.idwall.app.criminosonacionalidade.CriminosoNacionalidade;
import com.idwall.app.criminosonacionalidade.CriminosoNacionalidadeRepository;
import com.idwall.app.mandatoprisao.MandatoPrisao;
import com.idwall.app.mandatoprisao.MandatoPrisaoRepository;
import com.idwall.app.util.GenericService;
import com.idwall.app.util.enumeration.TipoInstituicao;
import com.idwall.app.util.enumeration.TipoSexo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CriminosoService {
    @Value("${quantidade.registros}")
    private String qtdRegistros;
    @Autowired
    private CriminosoRepository criminosoRepository;
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

    public List<Criminoso> listaCriminosos(CriminosoInputDto input) {
       var listaCriminosos = criminosoRepository.findByFilter(input);

        listaCriminosos
                .forEach(criminoso -> {
                    criminosoCorCabeloRepository.findByCriminosoId(criminoso.getId())
                            .ifPresent(criminoso::setCriminosoCorCabelos);

                    criminosoCorCabeloRepository.findByCriminosoId(criminoso.getId())
                            .ifPresent(criminoso::setCriminosoCorOlhos);

                    criminosoIdiomasRepository.findByCriminosoId(criminoso.getId())
                            .ifPresent(criminoso::setCriminosoIdiomas);

                    crimonosoNacionalidadeRepository.findByCriminosoId(criminoso.getId())
                            .ifPresent(criminoso::setCriminosoNacionalidades);

                    var mandatosArr = new ArrayList<MandatoPrisaoDto>();
                    mandatoPrisaoRepository.findByCriminosoId(criminoso.getId())
                            .ifPresent(item ->
                                    item.forEach(mandatoPrisao -> convertMandato(mandatoPrisao, mandatosArr))
                            );
                    criminoso.setMandatos(mandatosArr);
                });

        return listaCriminosos;
    }

    private void convertMandato(MandatoPrisao mandato, ArrayList<MandatoPrisaoDto> mandatos) {
        var entity = new MandatoPrisaoDto();
        entity.setAcusacao(mandato.getAcusacao());
        entity.setPaisEmissor(mandato.getPaisEmissor());

        mandatos.add(entity);
    }

    public void migrate() throws IOException, InterruptedException {

        JsonNode json = requestAPI("?page=1&resultPerPage="+qtdRegistros);

        var lista = json.get("_embedded").get("notices");

        lista.forEach(item -> {
            var id = item.get("entity_id").textValue();

            try {
                id = id.replace("/", "-");
                var json2 = requestAPI("/".concat(id));
                var criminoso = createCriminosoByMigrate(item, json2);
                createNacionalidadeByMigrate(json2, criminoso, crimonosoNacionalidadeRepository);
                createMandatosByMigrate(json2, criminoso, mandatoPrisaoRepository);
                createCriminosoCorOlhosByMigrate(json2, criminoso, criminosoCorOlhoRepository);
                createCriminosoCorCabeloByMigrate(json2, criminoso, criminosoCorCabeloRepository);
                createCriminosoIdiomasByMigrate(json2, criminoso, criminosoIdiomasRepository);

                var criminosoCorCabelo = new CriminosoCorCabelo();
                criminosoCorCabelo.setCriminoso(criminoso);

            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void createMandatosByMigrate(JsonNode json2, Criminoso criminoso, MandatoPrisaoRepository repository) {
        if (json2.get("arrest_warrants") != null || json2.get("arrest_warrants").get(0) != null) {
            json2.get("arrest_warrants").forEach(value -> {
                var mandato = new MandatoPrisao();
                mandato.setCriminoso(criminoso);
                if (value.get("charge") != null) {
                    mandato.setAcusacao(value.get("charge").textValue());
                }
                mandato.setPaisEmissor(value.get("issuing_country_id").textValue());

                repository.saveAndFlush(mandato);
            });
        }
    }

    private static void createCriminosoCorOlhosByMigrate(JsonNode json2, Criminoso criminoso, CriminosoCorOlhoRepository repository) {
        if (json2.get("eyes_colors_id") != null || json2.get("eyes_colors_id").get(0) != null) {
            json2.get("eyes_colors_id").forEach(value -> {
                var entity = new CriminosoCorOlho();
                entity.setCriminoso(criminoso);
                entity.setSigla(value.textValue());

                repository.saveAndFlush(entity);
            });
        }
    }

    private static void createCriminosoCorCabeloByMigrate(JsonNode json2, Criminoso criminoso, CriminosoCorCabeloRepository repository) {
        if (json2.get("hairs_id") != null || json2.get("hairs_id").get(0) != null) {
            json2.get("hairs_id").forEach(value -> {
                var entity = new CriminosoCorCabelo();
                entity.setCriminoso(criminoso);
                entity.setSigla(value.textValue());

                repository.saveAndFlush(entity);
            });
        }
    }

    private static void createCriminosoIdiomasByMigrate(JsonNode json2, Criminoso criminoso, CriminosoIdiomasRepository repository) {
        if (json2.get("languages_spoken_ids") != null || json2.get("languages_spoken_ids").get(0) != null) {
            json2.get("languages_spoken_ids").forEach(value -> {
                var entity = new CriminosoIdiomas();
                entity.setCriminoso(criminoso);
                entity.setSigla(value.textValue());

                repository.saveAndFlush(entity);
            });
        }
    }

    private static void createNacionalidadeByMigrate(JsonNode json2, Criminoso criminoso, CriminosoNacionalidadeRepository repository) {
        if (json2.get("nationalities") != null || json2.get("nationalities").get(0) != null) {
            json2.get("nationalities").forEach(value -> {
                var nacionalidade = new CriminosoNacionalidade();
                nacionalidade.setCriminoso(criminoso);
                nacionalidade.setSigla(value.textValue());
                repository.saveAndFlush(nacionalidade);
            });
        }
    }

    private Criminoso createCriminosoByMigrate(JsonNode item, JsonNode json2) {
        var dataNascimento = item.get("date_of_birth").textValue();
        var random = new Random();
        var crimonoso = new Criminoso();

        if (item.get("_links") != null && item.get("_links").get("thumbnail") != null && item.get("_links").get("thumbnail").get("href") != null) {
            var foto = item.get("_links").get("thumbnail").get("href").textValue();
            crimonoso.setFoto(foto);
        }

        crimonoso.setNome(item.get("name").textValue());
        crimonoso.setApelido(item.get("forename").textValue());
        crimonoso.setData_nascimento(LocalDate.parse(dataNascimento.replace("/", "-")));
        crimonoso.setTipoInstituicao(TipoInstituicao.values()[random.nextInt(2)]);
        crimonoso.setAltura(json2.get("height").doubleValue());
        crimonoso.setPeso(json2.get("weight").doubleValue());
        crimonoso.setLocal_nascimento(json2.get("place_of_birth").textValue());
        crimonoso.setMarcas_distintivas(json2.get("distinguishing_marks").textValue());
        crimonoso.setSexo(TipoSexo.valueOf(json2.get("sex_id").textValue()));

        return criminosoRepository.saveAndFlush(crimonoso);
    }

    private static JsonNode requestAPI(String url) throws IOException, InterruptedException {
        var urlBase = "https://ws-public.interpol.int/notices/v1/red";
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(
                        URI.create(urlBase + url))
                .header("accept", "application/json")
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(response.body());
    }
}
