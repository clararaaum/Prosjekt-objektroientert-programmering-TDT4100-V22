package gradeProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import gradeProject.logic.Subject;

public class DatabaseManager implements FileManager {

    @Override
    public File getFile(String filename){
        return new File("src" + File.separator + "main"+ File.separator + "resources"+File.separator+"gradeProject" +File.separator+ "savedDiplomas" +File.separator+ filename + ".txt");
    }

    @Override
    public void writeDiplomaToFile(String filename, HashMap<String, ArrayList<Subject>> diplomasCopy) throws FileNotFoundException {
        HashMap<String, ArrayList<Subject>> diplomas = new HashMap<>(diplomasCopy);
        // Try-with-resources 
        try (PrintWriter writer = new PrintWriter(getFile(filename))){
            for (String key : diplomas.keySet()) {
                ArrayList<Subject> subjects = new ArrayList<>(diplomas.get(key));
                String subjectsToString = subjects.toString();
                String subjectsFormatertToString = subjectsToString.replace("]", "").replace("[", ";");
                writer.println(key + subjectsFormatertToString);
            }
        } 
    }

    public HashMap<String, ArrayList<Subject>> readDiplomaFromFile(String filename) throws FileNotFoundException{
        // Try-with-resources 
        try(Scanner scanner = new Scanner(getFile(filename))){
            HashMap<String,ArrayList<Subject>> database = new HashMap<>();
            ArrayList<Subject> subjects = new ArrayList<Subject>();

            String string = scanner.nextLine();
            String[] liste = string.split(";");
            String username = liste[0];
            String data = liste[1];
            String[] eachSubject = data.split(",");

            for (String sub : eachSubject){
                String[] subjectNameAndGrade = sub.replaceAll(" ", "").split(":");            
                Subject subject = new Subject(subjectNameAndGrade[0], subjectNameAndGrade[1]);
                subjects.add(subject);
            }
            database.put(username, subjects);
            return database;
        }
    }
}
