package gradeProject;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import gradeProject.logic.Subject;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class FileManagerTest {

    // private static final fordi feltene skal være felles for alle testene
    private static final String test_diploma_file_content = "CorrectFile;TMA4115:C, TDT4100:A, EXP0300:B, TTM4100:F";
    private static final String invalid_diploma_file_content = "InvalidFile;TMA4115:C,,, TDT0A,TTM4100:F";

    private String username = "CorrectFile";
    private HashMap<String, ArrayList<Subject>> database = new HashMap<>(); 
    private ArrayList<Subject> subjects; 

    private HashMap<String, ArrayList<Subject>> getFilledTestDatabase(){
        subjects = new ArrayList<>();
        subjects.add(new Subject("TMA4115", "C"));
        subjects.add(new Subject("TDT4100", "A"));
        subjects.add(new Subject("EXP0300", "B"));
        subjects.add(new Subject("TTM4100", "F"));
        database.put(username, subjects);
        return new HashMap<>(database);       
    } 

    private FileManager getFileManager(){
        return new DatabaseManager();
    }

    @BeforeAll
    public void setup() throws IOException { // Skal ikke håndtere IOException fordi operasjonene lese og skrive fra fil antar vi at funker
        File dir = new File("src" + File.separator + "main"+ File.separator + "resources"+File.separator+"gradeProject" +File.separator+ "savedDiplomas");
        dir.mkdirs(); //Oppretter savedDiplomas hvis den ikke eksisterer

        Path path1 = Path.of(getFileManager().getFile("CorrectFile").getPath());
        Path path2 = Path.of(getFileManager().getFile("InvalidFile").getPath());

        Files.write(path1, test_diploma_file_content.getBytes());
        Files.write(path2, invalid_diploma_file_content.getBytes());
    }

    @Test
    public void testReadDiplomaFromFile() throws IOException {
        HashMap<String, ArrayList<Subject>> expectedDatabase = getFilledTestDatabase(); 
        HashMap<String, ArrayList<Subject>> actualDatabase = getFileManager().readDiplomaFromFile("CorrectFile"); 
        
        System.out.println(actualDatabase.keySet().toArray()[0]);

        Assertions.assertEquals(actualDatabase.keySet().toArray()[0], expectedDatabase.keySet().toArray()[0],
                "Brukernavn er ikke det samme");

        System.out.println(expectedDatabase.get(username));
        Iterator<Subject> expectedIterator = expectedDatabase.get(username).iterator();
        Iterator<Subject> actualIterator = actualDatabase.get(username).iterator();

        while (expectedIterator.hasNext()) {
            try {
                Subject expectedSubject = expectedIterator.next();
                Subject actualSubject = actualIterator.next();
                Assertions.assertTrue(expectedSubject.equals(actualSubject),
                        "Fagene er ulike, forventet: " + expectedSubject + " men fikk: " + actualSubject);
            } catch (IndexOutOfBoundsException e) {
                fail("Listen har ikke nok elementer til å fjerne");
            }
        }

    }

    @Test
    public void testLoadNonExistingFile() {
        Assertions.assertThrows(
                IOException.class,
                () -> getFileManager().readDiplomaFromFile("non_existing_file"),
                "Kaster IOExeption fordi filen ikke eksisterer");
    }

    @Test 
    public void testReadInvalidFile() {
        Assertions.assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> getFileManager().readDiplomaFromFile("InvalidFile"),
                "Kaster en ArrayIndexOutOfBoundsExeption fordi filen har feil format");
    }

    @Test 
    public void testWriteDiplomaToFile() throws IOException {
        getFileManager().writeDiplomaToFile("newFileTest", getFilledTestDatabase());
        Path expectedFile = Path.of(getFileManager().getFile("CorrectFile").getPath());
        Path actualFile = Path.of(getFileManager().getFile("newFileTest").getPath());

        Assertions.assertEquals(Files.readAllLines(expectedFile), Files.readAllLines(actualFile), "Filene har samme innhold");
    }


    @AfterAll 
    public void teardown() {
        getFileManager().getFile("newFileTest").delete();
        getFileManager().getFile("InvalidFile").delete();
        getFileManager().getFile("CorrectFile").delete();
    }
    
}