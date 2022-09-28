package gradeProject;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import gradeProject.logic.Computations;
import gradeProject.logic.Diploma;
import gradeProject.logic.Subject;

public class ComputationsTest {
    private Computations computationsOddSubjects; 
    private Computations computationsEmptyDiploma;
    private Computations computationsEvenSubjects;
    private Diploma diploma; 

    private Subject subject1; 
    private Subject subject2;
    private Subject subject3;
    private Subject subject4;

    @BeforeEach
    public void setup(){
        diploma = new Diploma("Alice"); 
        computationsEmptyDiploma = new Computations(diploma);   
        subject1 = new Subject("TMA4115", "C");
        subject2 = new Subject("TDT4100", "A");
        subject3 = new Subject("EXP0300", "F");
        diploma.addSubject(subject1);
        diploma.addSubject(subject2);
        diploma.addSubject(subject3);
        computationsOddSubjects = new Computations(diploma);
        subject4 = new Subject("TDT4104", "C");
        diploma.addSubject(subject4);
        computationsEvenSubjects = new Computations(diploma);
    }

    @Test
    @DisplayName("Tester konstruktøren")
    public void testConstructor(){
        Assertions.assertEquals(Arrays.asList(subject1, subject2, subject3, subject4), diploma.getSubjects());
    }

    @Test
    @DisplayName("Tester computeAverage")
    public void testComputeAverage(){
        Assertions.assertThrows(IllegalStateException.class, () -> {
            computationsEmptyDiploma.computeAverage();
        });
        computationsOddSubjects.computeAverage();
        Assertions.assertEquals("2.67", computationsOddSubjects.getAverage());
    }

    @Test
    @DisplayName("Tester computeMedian")
    public void testComputeMedian(){
        Assertions.assertThrows(IllegalStateException.class, () -> {
            computationsEmptyDiploma.computeMedian();
        });
        computationsOddSubjects.computeMedian();
        Assertions.assertEquals("3", computationsOddSubjects.getMedian());
        computationsEvenSubjects.computeMedian();
        Assertions.assertEquals("3", computationsEvenSubjects.getMedian());
    }



}
