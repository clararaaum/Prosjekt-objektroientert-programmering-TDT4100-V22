package gradeProject.logic;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Database { 
    
    private HashMap<String, ArrayList<Subject>> database; 

    public Database(){ // Brukes for å skrive til fil
        database = new HashMap<>();
    } 

    public Database(HashMap<String, ArrayList<Subject>> database){ // Brukes for å lese fra fil
        this.database = new HashMap<>(database);
    }

    public void addDiplomaToDatabase(Diploma diploma) {
        if(diploma == null){
            throw new NullPointerException("Du må skrive inn et brukernavn og legge til emner først!");
        }
        if(diploma.getSubjects().size() < 1){
            throw new IllegalStateException("Du må legge til emner i vitnemålet for å lagre det");
        }     
        database.put(diploma.getUsername(), (ArrayList<Subject>) diploma.getSubjects());
    }

    public Diploma getDiplomaFromDatabase(String username){
        Diploma diploma = new Diploma(username);
        for (int i = 0; i < database.get(username).size(); i++) {
            diploma.addSubject(database.get(username).get(i));            
        }
        return diploma;
    }

    public HashMap<String, ArrayList<Subject>> getDatabase(){ 
        HashMap<String, ArrayList<Subject>> databaseCopy = new HashMap<>();
        for (String key : database.keySet()) { //For hver key i databasen
            databaseCopy.put(key, database.get(key));            
        }
        return databaseCopy; // Returnerer en kopi for å unngå modifisering og brudd på innkapsling av det opprinnelige HashMapet
    }

    public String validateUsername(String username) { 
        if(username == ""){
            throw new IllegalArgumentException("Du må skrive inn et brukernavn");
        }
        if(fileInSavedDiplomas(username)){ // Sjekker om brukernavnet allerede er tatt i bruk
            throw new IllegalArgumentException("Brukernavnet er opptatt");
        }
        if(this.database == null){ // Førstemann som skriver inn
            return username;
        }
        return username;   
    }

    private boolean fileInSavedDiplomas(String username){ // private fordi det er en hjelpemetode
        File[] files = new File("src" + File.separator + "main"+ File.separator + "resources"+File.separator+"gradeProject" +File.separator+ "savedDiplomas" +File.separator).listFiles();
       
        for(File file : files){
            String fileName = file.getName(); // Navnet på en fil er username.txt
            String[] onlyFileName = fileName.split("\\."); // \\ for å få tak i punktum
            if(onlyFileName[0].equals(username)){ 
                return true;
            }
        }
        return false;
    }
}
