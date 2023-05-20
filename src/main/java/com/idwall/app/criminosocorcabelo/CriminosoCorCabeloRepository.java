package com.idwall.app.criminosocorcabelo;

import com.idwall.app.criminoso.Criminoso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CriminosoCorCabeloRepository extends JpaRepository<CriminosoCorCabelo, String> {

    @Query("SELECT sigla from CriminosoCorCabelo where criminoso.id = :id")
    Optional<List<String>> findByCriminosoId(@Param("id") UUID Id);

}
