package com.ttknpdev.manytoone.springbootcrudmanytoone.dao;

import com.ttknpdev.manytoone.springbootcrudmanytoone.entity.Programmer;
import com.ttknpdev.manytoone.springbootcrudmanytoone.repository.ProgrammerRepositories;
import com.ttknpdev.manytoone.springbootcrudmanytoone.service.ProgrammerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DAOProgrammer implements ProgrammerService<Programmer>{

    private ProgrammerRepositories programmerRepositories;

    @Autowired
    public DAOProgrammer(ProgrammerRepositories programmerRepositories) {
        this.programmerRepositories = programmerRepositories;
    }

    @Override
    public Programmer createProgrammer(Programmer obj) {
        return programmerRepositories.save(obj);
    }

    @Override
    public List<Programmer> readsProgrammer() {
        List<Programmer> programmerList = new ArrayList<>();
        programmerRepositories
                .findAll()
                .forEach(programmerList::add);
        return programmerList;
    }

    @Override
    public Programmer readProgrammer(Long id) {
        return programmerRepositories
                .findById(id)
                .orElseThrow(()-> new RuntimeException("programmer id "+id+" : Not found!"));
    }

    @Override
    public Map<?,?> update(Long id, Programmer obj) {
        Map<String,Programmer> res = new HashMap<>();
        return  programmerRepositories
                .findById(id)
                .map(programmer -> {
                    programmer.setPgmFullname(obj.getPgmFullname());
                    programmer.setCurrentDatetime(obj.getCurrentDatetime());
                    programmer.setPgmExperience(obj.getPgmExperience());
                    programmer.setPgmSalary(obj.getPgmSalary());
                    programmer.setPgmLevel(obj.getPgmLevel());
                    programmerRepositories.save(programmer);
                    res.put("updated",programmer);
                    // remember : we return values
                    // for next order of lambda
                    // if we don't return
                    // orElseThrow will work
                    // because the programmer id not found
                    return res;
                })
                .orElseThrow(()-> new RuntimeException("programmer id "+id+" : Not found!"));
    }

    @Override
    public Map<?, ?> delete(Long id) {
        return programmerRepositories.findById(id)
                .map(programmer -> {
                    Map<String,Programmer> res = new HashMap<>();
                    programmerRepositories.delete(programmer);
                    res.put("deleted",programmer);
                    return res;
                })
                .orElseThrow(()-> new RuntimeException("programmer id "+id+" : Not found!"));
    }
}
