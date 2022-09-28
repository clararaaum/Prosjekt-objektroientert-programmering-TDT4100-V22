package gradeProject;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gradeProject.logic.Study;

public class TestStudy {
    @Test
    public void testConstructor(){
        Study study = new Study("DKMMM", 1.23);
        //Study study2 = new Study("DKMMM", 6);
       
        Assertions.assertEquals("DKMMM", study.getStudyName());
        Assertions.assertEquals(1.23, study.getAverageLimit());

        assertThrows(IllegalArgumentException.class, () -> {
            new Study("MTiO", 4.10);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Study("MTIO", 7.34);
        });
    }
}
