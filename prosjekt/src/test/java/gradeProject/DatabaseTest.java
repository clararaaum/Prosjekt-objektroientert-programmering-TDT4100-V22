package gradeProject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import gradeProject.logic.Database;
import gradeProject.logic.Diploma;
import gradeProject.logic.Subject;

public class DatabaseTest {

    private Database databaseObjekt; 
    private Diploma diploma1; 
    private Diploma diploma2;  
    
    @BeforeEach
    public void setUp(){
        File dir = new File("src" + File.separator + "main"+ File.separator + "resources"+File.separator+"gradeProject" +File.separator+ "savedDiplomas");
        dir.mkdirs(); //Oppretter savedDiplomas hvis den ikke eksisterer

        diploma1 = new Diploma("Alice");   
        diploma1.addSubject(new Subject("TMA4115", "C"));
        diploma1.addSubject(new Subject("TDT4100", "A"));
        diploma1.addSubject(new Subject("EXP0300", "F"));

        diploma2 = new Diploma("Bob");  
        diploma2.addSubject(new Subject("TDT4100", "C"));
        diploma2.addSubject(new Subject("EXP0300", "D"));

        databaseObjekt = new Database();
        

    }

    @Test
    @DisplayName("Tester tom konstruktør")
    public void testDatabase1(){
        Assertions.assertTrue(databaseObjekt.getDatabase().isEmpty());
    }

    @Test
    @DisplayName("Tester konstruktør med en parameter")
    public void testDatabase2(){
        HashMap<String, ArrayList<Subject>> inputHashMap = new HashMap<>();
        ArrayList<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject("TMA4115", "C"));
        inputHashMap.put("Clara", subjects); 
        
        Database databaseWithHashMap = new Database(inputHashMap);
        
        Assertions.assertEquals(1, databaseWithHashMap.getDatabase().size());
    }



    @Test
    @DisplayName("Tester addDiplomaToDatabase")
    public void testAddDiplomaToDatabase(){
        Assertions.assertThrows(IllegalStateException.class, () -> {
            databaseObjekt.addDiplomaToDatabase(new Diploma("Bob")); 
        });
        Assertions.assertEquals(0, databaseObjekt.getDatabase().size());
        databaseObjekt.addDiplomaToDatabase(diploma1);
        Assertions.assertEquals(1, databaseObjekt.getDatabase().size());

        databaseObjekt.addDiplomaToDatabase(diploma2);
        Assertions.assertEquals(2, databaseObjekt.getDatabase().size());
    }

    @Test
    @DisplayName("Tester getDiplomaFromDatabase") // Pga denne definerte vi equals funksjoner i Subject og Diploma
    public void testGetDiplomaFromDatabas(){
        databaseObjekt.addDiplomaToDatabase(diploma1);
        Assertions.assertTrue(diploma1.equals(databaseObjekt.getDiplomaFromDatabase("Alice")));
    }

    @Test
    @DisplayName("Tester validateUsername")
    public void testValidateUsername() throws IOException{
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            databaseObjekt.validateUsername("");
        });
    
        //Skal sjekke om brukernavnet er opptatt
        DatabaseManager manager = new DatabaseManager();

        //Oppretter en path og så en fil for å kunne sjekke om det er en fil lagret med samme vitnemål
        Path path = Path.of(manager.getFile("SavedFile").getPath());
        String test_File_Saved = "SavedFile;TMA4115:C, TDT4100:A";
        Files.write(path, test_File_Saved.getBytes());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            databaseObjekt.validateUsername("SavedFile"); 
        });
        //Sletter så denne filen så den ikke ligger lagret i savedDiplomas:
        manager.getFile("SavedFile").delete();

        Assertions.assertEquals("Alice", databaseObjekt.validateUsername("Alice"));
    }

    

    
}
