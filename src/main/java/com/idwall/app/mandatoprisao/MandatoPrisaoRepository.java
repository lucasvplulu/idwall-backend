package com.idwall.app.mandatoprisao;

import com.idwall.app.criminoso.dto.MandatoPrisaoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MandatoPrisaoRepository extends JpaRepository<MandatoPrisao, UUID> {

    @Query("Select m from MandatoPrisao m where criminoso.id = :id")
    Optional<List<MandatoPrisao>> findByCriminosoId(@Param("id") UUID Id);


}
