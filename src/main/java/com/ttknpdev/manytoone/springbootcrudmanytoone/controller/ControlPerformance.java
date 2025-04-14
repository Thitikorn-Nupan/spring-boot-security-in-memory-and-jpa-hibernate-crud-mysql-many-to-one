package com.ttknpdev.manytoone.springbootcrudmanytoone.controller;

import com.ttknpdev.manytoone.springbootcrudmanytoone.entity.Performance;
import com.ttknpdev.manytoone.springbootcrudmanytoone.service.PerformanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/performance")
public class ControlPerformance {

    private final PerformanceService<Performance> performanceService;
    @Autowired
    public ControlPerformance(PerformanceService performanceService) {
        this.performanceService = performanceService;
    }

    @PostMapping(value = "/create/programmer/{idProgrammer}")
    private ResponseEntity create(@RequestBody Performance performance ,@PathVariable Long idProgrammer) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(performanceService.createPerformanceByPrimaryKeyProgrammer(idProgrammer,performance));
    }

    @GetMapping(value = "/reads/programmer/{idProgrammer}")
    private ResponseEntity reads(@PathVariable Long idProgrammer) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(performanceService.readsPerformanceByIdProgrammer(idProgrammer));
    }
    @GetMapping(value = "/read/programmer/{projectName}")
    private ResponseEntity read(@PathVariable String projectName) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(performanceService.readPrimaryKeyPerformance(projectName));
    }

    @DeleteMapping(value = "/delete/programmer/{idProgrammer}")
    private ResponseEntity delete(@PathVariable Long idProgrammer) {
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(performanceService.deletePerformanceAllByIdProgrammer(idProgrammer));
    }

    @PutMapping(value = "/update/programmer/{projectName}")
    private ResponseEntity update(@PathVariable String projectName , @RequestBody Performance performance) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(performanceService.updatePerformanceByPrimaryKey(projectName,performance));
    }
}
