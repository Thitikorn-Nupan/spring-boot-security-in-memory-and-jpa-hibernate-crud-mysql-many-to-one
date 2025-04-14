package com.ttknpdev.manytoone.springbootcrudmanytoone.testbusiness;

import com.ttknpdev.manytoone.springbootcrudmanytoone.dao.DAOPerformance;
import com.ttknpdev.manytoone.springbootcrudmanytoone.dao.DAOProgrammer;
import com.ttknpdev.manytoone.springbootcrudmanytoone.entity.Performance;
import com.ttknpdev.manytoone.springbootcrudmanytoone.entity.Programmer;
import com.ttknpdev.manytoone.springbootcrudmanytoone.repository.PerformancesRepositories;
import com.ttknpdev.manytoone.springbootcrudmanytoone.repository.ProgrammerRepositories;
import com.ttknpdev.manytoone.springbootcrudmanytoone.service.PerformanceService;
import com.ttknpdev.manytoone.springbootcrudmanytoone.service.ProgrammerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

// concept is mocking
// ** JUnit 5 + Mockito ** For testing Service Logic Not API
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TestServiceByJUnit {

    // ***** Second way **** Not using Correct Method for Creating Mock Objects **
    private PerformancesRepositories performancesRepositories;
    private ProgrammerRepositories programmerRepositories;
    private PerformanceService<Performance> performanceService;
    private ProgrammerService<Programmer> programmerService;

    @BeforeEach
    public void setUp() {
        performancesRepositories = Mockito.mock(PerformancesRepositories.class);
        programmerRepositories = Mockito.mock(ProgrammerRepositories.class);
        performanceService = new DAOPerformance(performancesRepositories, programmerRepositories);
        programmerService = new DAOProgrammer(programmerRepositories);
    }

    @Nested
    public class TestServicePerformance {

        @Test
        public void testReadPrimaryKeyPerformance() {
            // keep concept mocking
            String projectName = "TT Shop";
            /*
                So, we have to tell Mockito to return something
                when userRepository.findAll() is called.
                We do this with the static when method.
            */
            when( performancesRepositories.findById(projectName) ).thenReturn(Optional.of(getPerformance()));
            /*
                Now users object called userRepository.findAll() passed userService.reads()
                So it should store list of getUsers() method
            */
            Performance performance =  performanceService.readPrimaryKeyPerformance(projectName);

            assertThat(performance.getProjectName() ).isEqualTo(projectName);
            verify(performancesRepositories,times(1)).findById(projectName); // verify (v. ตรวจสอบ) , invocations (n. การร้องขอ)
        }

        @Test
        public void testReadsPerformanceByIdProgrammer () {
            Long programmerId = 1L;
            // Mock my readsPerformanceByIdProgrammer(...) method
            when( programmerRepositories.findById(programmerId) ).thenReturn(Optional.of(getProgrammer()));

            Optional<Programmer> programmer = programmerRepositories.findById(programmerId);

            assertThat(programmer.isPresent()).isTrue();
            verify(programmerRepositories,times(1)).findById(programmerId);


            when( performancesRepositories.readsPerformanceByIdProgrammer(programmerId) ).thenReturn(getPerformances());

            List<Performance> performances = performanceService.readsPerformanceByIdProgrammer(programmerId);

            assertThat( performances ).hasSize( 3 );
            verify(performancesRepositories,times(1)).readsPerformanceByIdProgrammer(programmerId);
        }

        @Test
        public void testCreatePerformanceByPrimaryKeyProgrammer () {
            // Mock my createPerformanceByPrimaryKeyProgrammer(...) method
            when( programmerRepositories.findById(1L) ).thenReturn(Optional.of(getProgrammer()));

            // call service
            Programmer programmer = programmerRepositories.findById(1L).get();
            Performance newPerformance  = getPerformance() , newPerformanceHasProgrammer = getPerformance();


            // test response
            assertThat( programmer.getPgmFullname() ).isEqualTo( "Alex Sandler" );
            verify(programmerRepositories,times(1)).findById(1L);

            newPerformanceHasProgrammer.setProgrammer(programmer);

            // Mock
            when( performancesRepositories.save(newPerformance) ).thenReturn(newPerformanceHasProgrammer);

            // call
            Performance currentPerformance = performanceService.createPerformanceByPrimaryKeyProgrammer(1L,newPerformance);

            // test response
            assertThat(currentPerformance.getProgrammer() ).isEqualTo( newPerformanceHasProgrammer.getProgrammer() );
            verify(performancesRepositories,times(1)).save(newPerformance);
        }

        @Test
        public void testDeletePerformanceAllByIdProgrammer() {
            Long programmerId = 1L;
            // Mock my deletePerformanceAllByIdProgrammer(...) method
            when( programmerRepositories.findById(programmerId) ).thenReturn(Optional.of(getProgrammer()));

            Optional<Programmer> programmer = programmerRepositories.findById(1L);

            assertThat(programmer.isPresent()).isTrue();
            verify(programmerRepositories,times(1)).findById(programmerId);


            when( performancesRepositories.deleteByProgrammer_PgmId(programmerId) ).thenReturn(3);
            /*
                *** behind the sense
                Map<String,Integer> resultMap = new HashMap<>();
                resultMap.put("deleted",performancesRepositories.deleteByProgrammer_PgmId(1L));
            */

            Map resultMap = performanceService.deletePerformanceAllByIdProgrammer(programmerId);

            assertThat( resultMap.get("deleted") ).isEqualTo(3);
            verify(performancesRepositories,times(1)).deleteByProgrammer_PgmId(programmerId);
        }

        @Test
        public void testUpdatePerformanceByPrimaryKey() {
            String projectName = "TT Shop";
            // keep concept mocking

            when( performancesRepositories.findById(projectName) ).thenReturn(Optional.of(getPerformance()));

            Optional<Performance> performance = performancesRepositories.findById(projectName);

            assertThat(performance.isPresent() ).isTrue();
            verify(performancesRepositories,times(1)).findById(projectName);



            Performance newPerformance = getPerformance() ;
            newPerformance.setProjectName("New TT Shop");
            newPerformance.setProjectCharge(30000D);
            newPerformance.setMemberAmount((short)10);

            when( performancesRepositories.updatePerformanceByPrimaryKey(newPerformance.getProjectName(),newPerformance.getProjectCharge(),newPerformance.getMemberAmount(), projectName) ).thenReturn(1);

            // call
            Map resultMap = performanceService.updatePerformanceByPrimaryKey(projectName,newPerformance);

            assertThat( resultMap.get("updated") ).isEqualTo(newPerformance);
            verify(performancesRepositories,times(1)).updatePerformanceByPrimaryKey(newPerformance.getProjectName(),newPerformance.getProjectCharge(),newPerformance.getMemberAmount(), projectName);
        }

    }

    // after when() have to call verify()
    @Nested
    public class TestServiceProgrammer {
        @Test
        public void testCreateProgrammer() {
            // Mock my createPerformanceByPrimaryKeyProgrammer(...) method
            Programmer programmer = getProgrammer() ;
            when( programmerRepositories.save(programmer) ).thenReturn( programmer );
            programmerService.createProgrammer(programmer);
            assertThat( programmer ).isNotNull();
            verify(programmerRepositories,times(1)).save(programmer);
        }

        @Test
        public void testReadsProgrammer() {
            when( programmerRepositories.findAll() ).thenReturn( getProgrammers() );
            List<Programmer> programmers = programmerService.readsProgrammer();
            assertThat( programmers ).isNotNull();
            verify(programmerRepositories,times(1)).findAll();
        }

        @Test
        public void testUpdateProgrammer() {
            when( programmerRepositories.findById(1L) ).thenReturn(Optional.of(getProgrammer()));
            Optional<Programmer> programmer = programmerRepositories.findById(1L);
            assertThat(programmer.isPresent()).isTrue();
            verify(programmerRepositories,times(1)).findById(1L);
            Programmer newProgrammer = getProgrammer() ;
            newProgrammer.setPgmFullname("John Smith");
            when( programmerRepositories.save(programmer.get()) ).thenReturn(newProgrammer);
            Map resultMap = programmerService.update(1L,newProgrammer);
            assertThat( resultMap.get("updated") ).isNotNull();
            verify(programmerRepositories,times(1)).save(programmer.get());
        }
        @Test
        public void testDeleteProgrammer() {
            when( programmerRepositories.findById(1L) ).thenReturn(Optional.of(getProgrammer()));
            Optional<Programmer> programmer = programmerRepositories.findById(1L);
            assertThat(programmer.isPresent()).isTrue();
            verify(programmerRepositories,times(1)).findById(1L);
            // use when() and pass void method
            doNothing().when(programmerRepositories).delete(programmer.get());
            Map resultMap = programmerService.delete(1L);
            assertThat( resultMap.get("deleted") ).isNotNull();
            verify(programmerRepositories,times(1)).delete(programmer.get());
        }
    }



    private Performance getPerformance() {
        return new Performance("TT Shop",25000000d,(short)30);
    }

    private List<Performance> getPerformances() {
        return List.of(
                new Performance("TT1 Shop",25000000d,(short)30),
                new Performance("TT2 Shop",25000000d,(short)30),
                new Performance("TT3 Shop",25000000d,(short)30)
        );
    }

    private Programmer getProgrammer() {
        return  new Programmer("Alex Sandler",35000d,'A',(short)3);
    }

    private List<Programmer> getProgrammers() {
        return List.of(
                new Programmer("Alex1 Sandler",35000d,'A',(short)3) ,
                new Programmer("Alex2 Sandler",35000d,'A',(short)3) ,
                new Programmer("Alex3 Sandler",35000d,'A',(short)3)
                );
    }
}
