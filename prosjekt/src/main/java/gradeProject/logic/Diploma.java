package gradeProject.logic;

import java.util.ArrayList;
import java.util.List;

public class Diploma {
    private String username;
    private List<Subject> subjects = new ArrayList<Subject>();

    // Unntak som utløses under brukes av Controlleren vha getMessage() som skriver ut til GUI

    public Diploma(String username) {
        if(this.validateUsername(username)) this.username = username;
        else throw new IllegalArgumentException("Ugydlig brukernavn, må være mellom 2 og 20 tegn. Kan inneholde: .-_");
    }

    public String getUsername(){
        return username;
    }

    public void addSubject(Subject subject){
        if(!this.subjectExists(subject)) this.subjects.add(subject);  
        else throw new IllegalStateException("Faget er allerede lagt til");
    }

    private boolean subjectExists(Subject subject){
        return this.subjects.stream().anyMatch(s -> s.equals(subject)); 
    }

    public void removeSubject(int index){ 
        if(index == -1){
            throw new IllegalStateException("Du må markere et fag for å fjerne det");
        }
        this.subjects.remove(index);
    }

    private boolean validateUsername(String username){
        if(username == null) return false;
        return username.matches("[A-Za-z.-_ØÆÅøæå]{2,20}");
    }
    
    public List<Subject> getSubjects(){
        return new ArrayList<Subject>(subjects); // Returnerer en kopi av listen for å unngå modifisering og brudd på innkapsling av den opprinnelige listen 
    }

    public List<Subject> getSortedSubjects(){
        List<Subject> sortSubjects = new ArrayList<Subject>(subjects);
        sortSubjects.sort((a, b) -> Computations.getGradeValue(b) - Computations.getGradeValue(a)); 
        return sortSubjects; 
    }

    public boolean equals(Diploma otherDiploma){ // Bruker testkoden for DatabaseTest
        for(int i = 0; i < subjects.size(); i++){
            if(!subjects.get(i).equals(otherDiploma.subjects.get(i))) return false;
        }
        return true; 
    }

    public int getInstanceOfGrade(String grade){ // Brukes for å lage søylediagram i Controlleren
        int counter = 0;
        for(Subject subject : subjects){
            if(subject.getGrade().equals(grade)){
                counter++;
            }
        }
        return counter;
    }

    @Override
    public String toString() {
        return this.getUsername() +"="+ subjects.toString();
    }

}
