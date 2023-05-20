package com.idwall.app.criminosoidiomas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CriminosoIdiomasRepository extends JpaRepository<CriminosoIdiomas, String> {

    @Query("SELECT sigla from CriminosoIdiomas where criminoso.id = :id")
    Optional<List<String>> findByCriminosoId(@Param("id") UUID Id);

}
