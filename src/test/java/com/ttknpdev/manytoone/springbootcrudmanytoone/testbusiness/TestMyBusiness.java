package com.ttknpdev.manytoone.springbootcrudmanytoone.testbusiness;

import com.ttknpdev.manytoone.springbootcrudmanytoone.dao.DAOPerformance;
import com.ttknpdev.manytoone.springbootcrudmanytoone.entity.Performance;
import com.ttknpdev.manytoone.springbootcrudmanytoone.entity.Programmer;
import com.ttknpdev.manytoone.springbootcrudmanytoone.repository.PerformancesRepositories;
import com.ttknpdev.manytoone.springbootcrudmanytoone.repository.ProgrammerRepositories;
import com.ttknpdev.manytoone.springbootcrudmanytoone.service.PerformanceService;
import com.ttknpdev.manytoone.springbootcrudmanytoone.service.ProgrammerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class TestMyBusiness {

    @Autowired
    private ProgrammerRepositories programmerRepositories;
    @Autowired
    private PerformancesRepositories performancesRepositories;
    private ProgrammerService programmerService;
    private PerformanceService performanceService;

    /*
     @Autowired
    public void setProgrammerService() {
        this.programmerService = new DAOProgrammer(programmerRepositories);
    }*/
    @Autowired
    public void setPerformanceService() {
        this.performanceService = new DAOPerformance(performancesRepositories , programmerRepositories);
    }


    /*    @Autowired
        private PerformancesRepositories performancesRepositories;
        private ProgrammerRepositories programmerRepositories;
        @Autowired
        public void setProgrammerService() {
            this.programmerService = new DAOProgrammer();
        }*/
    @Test
    public void createProgrammer() {
        Programmer p1 = new Programmer("Peter Parkers" , 35000D,'B',(short)12);
        assertNotNull(programmerService.createProgrammer(p1));
    }

    @Test
    public void createPerformance() {
        Performance performance1 = new Performance("Mobile Banking Service",50000.5,(short)30);
        assertNotNull(performanceService.createPerformanceByPrimaryKeyProgrammer(1L,performance1));
    }

    @Test
    public void readsPerformance(){
        assertNotNull(performanceService.readsPerformanceByIdProgrammer(1L));
    }

    @Test
    public void updateProgrammer(){
        Programmer p1 = new Programmer("Peter Parkers" , 75000.5D,'A',(short)20);
        assertNotNull(programmerService.update(1L,p1).get("updated"));
    }

    @Test
    public void deletePerformance() {
        assertNotNull(performanceService.deletePerformanceAllByIdProgrammer(2L).get("deleted"));
    }


}
