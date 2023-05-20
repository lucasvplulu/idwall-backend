package com.idwall.app.util;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @param <T> Type of desired value object
 * @author lkogici
 */
public abstract class GenericController<T> {

    GenericService service;

    public GenericController(GenericService service) {
        this.service = service;
    }


    @GetMapping
    @ApiOperation(value = "Lista todos os registros")
    public ResponseEntity<List<T>> findAll() {
        return ResponseEntity.ok(this.service.findAll());
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Lista somente o registro com o id específicado")
    public ResponseEntity<T> findById(
            @ApiParam(value = "Id do registro que deseja buscar", required = true)
            @PathVariable(value = "id") String id) {

        T response = (T) this.service.findById(id);

        if (response != null) {
            return ResponseEntity.ok(response);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping
    @ApiOperation(value = "Criação de um novo registro")
    public ResponseEntity<T> create(@RequestBody @Valid T entity) {
        return ResponseEntity.ok((T) this.service.create(entity));
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Atualização de um determinado registo")
    public ResponseEntity<T> update(
            @ApiParam(value = "Id do registro a ser atualizado", required = true)
            @PathVariable(value = "id") String id,
            @RequestBody @Valid T newEntity) {

        T response = (T) this.service.update(id, newEntity);

        if (response != null) {
            return ResponseEntity.ok(response);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Exclusão de um determinado registro")
    public ResponseEntity<StringMessage> delete(
            @ApiParam(value = "Id do registro a ser excluído", required = true)
            @PathVariable(value = "id") String id) {

        Boolean response = this.service.delete(id);

        StringMessage responseMessage = new StringMessage();

        if (response) {
            responseMessage.setMessage("Excluído com sucesso");

            return ResponseEntity.ok(responseMessage);
        }

        responseMessage.setMessage("Registro Não encontrado");

        return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);

    }

}