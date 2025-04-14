package com.ttknpdev.manytoone.springbootcrudmanytoone.dao;

import com.ttknpdev.manytoone.springbootcrudmanytoone.entity.Performance;
import com.ttknpdev.manytoone.springbootcrudmanytoone.repository.PerformancesRepositories;
import com.ttknpdev.manytoone.springbootcrudmanytoone.repository.ProgrammerRepositories;
import com.ttknpdev.manytoone.springbootcrudmanytoone.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Done unit testing
@Service
public class DAOPerformance implements PerformanceService<Performance> {

    private PerformancesRepositories performancesRepositories;
    private ProgrammerRepositories programmerRepositories;

    @Autowired
    public DAOPerformance(PerformancesRepositories performancesRepositories, ProgrammerRepositories programmerRepositories) {
        this.performancesRepositories = performancesRepositories;
        this.programmerRepositories = programmerRepositories;
    }

    @Override
    public Performance createPerformanceByPrimaryKeyProgrammer(Long id, Performance obj) {
        // if we need to create , first we have to search Programmer by id
        // because performances table has foreign key
        return programmerRepositories
                .findById(id)
                .map(programmer -> {
                    obj.setProgrammer(programmer); // set id of programmer
                    return performancesRepositories.save(obj);
                })
                .orElseThrow( () ->
                    new RuntimeException("programmer id "+id+" : Not found!")
                );
    }

    @Override
    public List<Performance> readsPerformanceByIdProgrammer(Long id) {
        List<Performance> performanceList = new ArrayList<>();
        return programmerRepositories.findById(id)
                .map(programmer -> {
                     performanceList.addAll(performancesRepositories.readsPerformanceByIdProgrammer(id));
                     return performanceList;
                })
                .orElseThrow(()-> new RuntimeException("programmer id "+id+" : Not found!"));
    }

    @Override
    public Performance readPrimaryKeyPerformance(String projectName) {
        return performancesRepositories
                .findById(projectName)
                .orElseThrow(()->new RuntimeException("performance filed project name "+projectName+" : Not found"));
    }

    @Override
    public Map<?,?> deletePerformanceAllByIdProgrammer(Long id) {
        return programmerRepositories
                .findById(id)
                .map(programmer -> {
                    Map<String,Integer> res = new HashMap<>();
                    res.put("deleted",performancesRepositories.deleteByProgrammer_PgmId(id));
                    return res;
                })
                .orElseThrow(()->new RuntimeException("programmer id "+id+" : Not found!"));
    }

    @Override
    public Map<?, ?> updatePerformanceByPrimaryKey(String projectName, Performance obj) {
        return performancesRepositories.findById(projectName)
                .map(performance -> {
                    Map<String , Performance> res = new HashMap<>();
                    performancesRepositories.updatePerformanceByPrimaryKey(
                             obj.getProjectName()
                            ,obj.getProjectCharge()
                            ,obj.getMemberAmount()
                            ,projectName);
                    res.put("updated",obj);
                    return res;
                }).orElseThrow(()-> new RuntimeException("performance filed project name "+projectName+" : Not found"));
    }
}
