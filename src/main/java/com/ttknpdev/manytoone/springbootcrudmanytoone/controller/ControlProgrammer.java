package com.ttknpdev.manytoone.springbootcrudmanytoone.controller;

import com.ttknpdev.manytoone.springbootcrudmanytoone.entity.Programmer;
import com.ttknpdev.manytoone.springbootcrudmanytoone.service.ProgrammerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/programmer")
public class ControlProgrammer {

    private final ProgrammerService<Programmer> programmerService;

    @Autowired
    public ControlProgrammer(ProgrammerService programmerService) {
        this.programmerService = programmerService;
    }

    @PostMapping(value = "/create")
    private ResponseEntity create(@RequestBody Programmer p) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(programmerService.createProgrammer(p));
    }

    @GetMapping(value = "/reads")
    private ResponseEntity reads() {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(programmerService.readsProgrammer());
    }
    @GetMapping(value = "/read/{id}")
    private ResponseEntity read(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(programmerService.readProgrammer(id));
    }

    @PutMapping(value = "/update/{id}")
    private ResponseEntity update(@PathVariable Long id , @RequestBody Programmer p) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(programmerService.update(id,p));
    }

    @DeleteMapping(value = "/delete/{id}") // delete performance before programmer
    private ResponseEntity delete(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(programmerService.delete(id));
    }


}
