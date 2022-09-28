package gradeProject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import gradeProject.logic.Subject;

public class SubjectTest {

    private Subject subject1;
    private Subject subject2;
    private Subject subject3;

    private void checkSubject(Subject subject, String subjectName, String grade){
        Assertions.assertEquals(subject.getSubjectName(), subjectName);
        Assertions.assertEquals(subject.getGrade(), grade);
    }


    @BeforeEach
    public void setUp(){
        subject1 = new Subject("ABC1234", "C");
        subject2 = new Subject("ABC1234", "F");
        subject3 = new Subject("ABC1234", "A");
    }

    @Test
    @DisplayName("Sjekk konstruktør")
    public void testConstructor(){
        checkSubject(new Subject("TMA4115", "B"), "TMA4115", "B");
        checkSubject(new Subject("TMA4110", "F"), "TMA4110", "F");
        checkSubject(new Subject("TIO4110", "A"), "TIO4110", "A");


        Assertions.assertThrows(IllegalArgumentException.class, () ->{
            new Subject("TMA4114", "G");
        }, "Skal ikke kunne legge til en annen karakter enn A-F skalaen");

        Assertions.assertThrows(IllegalArgumentException.class, () ->{
            new Subject("tma4114", "A");
        }, "Fagkoden må ha store bokstaver");

        Assertions.assertThrows(IllegalArgumentException.class, () ->{
            new Subject("TMA433", "A");
        }, "Fagkoden må ha riktig regex (ABC1234)");
    }

    
    
}
