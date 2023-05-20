package com.idwall.app.criminosonacionalidade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CriminosoNacionalidadeRepository extends JpaRepository<CriminosoNacionalidade, String> {

    @Query("SELECT sigla from CriminosoNacionalidade where criminoso.id = :id")
    Optional<List<String>> findByCriminosoId(@Param("id") UUID Id);

}
