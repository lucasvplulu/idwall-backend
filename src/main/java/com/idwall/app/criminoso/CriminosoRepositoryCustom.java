package com.idwall.app.criminoso;

import com.idwall.app.criminoso.dto.CriminosoInputDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CriminosoRepositoryCustom {
    List<Criminoso> findByFilter(CriminosoInputDto inputDto);
}
