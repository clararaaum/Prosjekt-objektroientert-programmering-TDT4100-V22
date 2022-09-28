package gradeProject;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import gradeProject.logic.Diploma;
import gradeProject.logic.Study;
import gradeProject.logic.Subject;

public class TestFilterStudies {

    private Subject subject1; 
    private Subject subject2;
    private Subject subject3;
    private Diploma diploma; 
    
    @BeforeEach
    public void setUp(){

        // 5 + 3 + 0 / 3 = 2.67

        diploma = new Diploma("Alice");
        diploma.addSubject(new Subject("TMA4115", "C"));
        diploma.addSubject(new Subject("TDT4100", "A"));
        diploma.addSubject(new Subject("EXP0300", "F"));
        
    }

    @Test
    @DisplayName("Tester getFilteredStudies")
    public void testGetFilteredStudies(){
        List<Study> aliceFilteredStudies = new ArrayList<>();
        aliceFilteredStudies.add(new Study("BAKKF", 2.42));   
        aliceFilteredStudies.add(new Study("BDRAA", 1.20));  
        
    }
}

