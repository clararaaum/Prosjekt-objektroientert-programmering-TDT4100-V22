package gradeProject;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import gradeProject.logic.Diploma;
import gradeProject.logic.Subject;

public class DiplomaTest {
    private Subject subject1; 
    private Subject subject2;
    private Subject subject3;
    private Diploma diploma; 
    
    @BeforeEach
    public void setup(){
        subject1 = new Subject("TMA4115", "C");
        subject2 = new Subject("TDT4100", "A");
        subject3 = new Subject("EXP0300", "F");
        diploma = new Diploma("Alice");
    }

    @Test
    @DisplayName("Test konstruktøren")
    public void testConstructor1(){ 
        Assertions.assertEquals("Alice", diploma.getUsername(), "Sjekker at brukernavnet settes i konstruktrøen");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Diploma("a");
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Diploma("RolfRolfesenRolfensenRolfern");
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Diploma("Mari Elise");
        });
    }

    @Test
    @DisplayName("Tester addSubject")
    public void testAddSubject(){
        Assertions.assertEquals(true, diploma.getSubjects().isEmpty(), "subjects-listen skal være tom før emner blir lagt til");
        diploma.addSubject(subject1);
        Assertions.assertEquals(Arrays.asList(subject1), diploma.getSubjects());
        diploma.addSubject(subject2);
        diploma.addSubject(subject3);
        Assertions.assertEquals(3, diploma.getSubjects().size());

        Assertions.assertThrows(IllegalStateException.class, () -> {
            diploma.addSubject(subject1);
        });
    }

    @Test
    @DisplayName("Tester removeSubject")
    public void testRemoveSubject(){
        diploma.addSubject(subject1);
        diploma.addSubject(subject2);
        diploma.removeSubject(0);
        Assertions.assertEquals(Arrays.asList(subject2), diploma.getSubjects());
        diploma.removeSubject(0);
        Assertions.assertEquals(true, diploma.getSubjects().isEmpty(), "alle emnene skal ha blitt fjernet");
        // Trenger ikke sjekke om brukeren prøver å fjerne et emne på en ugyldig indeks fordi brukeren ikke klarer å velge den, den får bare opp emene den har lagt inn
    }

    @Test
    @DisplayName("Tester getInstanceOfGrade")
    public void testGetInstanceOfGrade(){
        diploma.addSubject(subject1);
        diploma.addSubject(subject2);
        diploma.addSubject(subject3);
        diploma.addSubject(new Subject("MAO1101", "C"));
        Assertions.assertEquals(1, diploma.getInstanceOfGrade("A"));
        Assertions.assertEquals(2, diploma.getInstanceOfGrade("C"));     
        Assertions.assertEquals(0, diploma.getInstanceOfGrade("B"));     
        Assertions.assertEquals(1, diploma.getInstanceOfGrade("F"));     
    }

    
}
