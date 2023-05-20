package com.idwall.app.criminoso;

import com.idwall.app.criminoso.dto.CriminosoInputDto;
import com.idwall.app.util.GenericController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/criminoso")
public class CriminosoController {

    @Autowired
    CriminosoService criminosoService;

    @GetMapping()
    public ResponseEntity<List<Criminoso>> listaCriminosos( CriminosoInputDto input) {
        return ResponseEntity.ok(this.criminosoService.listaCriminosos(input));
    }

    @GetMapping(path = "/migrate")
    public void migrateBase() throws IOException, InterruptedException {
        criminosoService.migrate();
    }

}
