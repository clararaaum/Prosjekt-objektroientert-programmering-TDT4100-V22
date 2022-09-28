package gradeProject.logic;

public class Subject {
    private String subjectName; 
    private String grade;

    public final static String[] validGrades = {"A", "B", "C", "D", "E", "F"};

    public Subject(String subjectName, String grade){
        if ((validateGrade(grade))&& validateSubjectName(subjectName)) {
            this.grade = grade;
            this.subjectName = subjectName;
        }
        else throw new IllegalArgumentException("Ikke gyldig fagkode eller ikke gyldig karakter (A-F)");
    }

    private boolean validateGrade(String grade){
        if(grade.length() == 1){
            for (String g : validGrades) { //Sjekker om karakteren brukeren legger inn er blant de lovlige
                if(grade.substring(0, 1).equals(g)){
                    return true;
                }                     
            } 
        } 
        return false;        
    }
    
    private boolean validateSubjectName(String subjectName){
        return subjectName.matches("[A-Z]{3}[0-9]{4}"); 
    }

    public String getGrade(){
        return grade;
    }
    public String getSubjectName(){
        return subjectName;
    }

    public boolean equals(Subject otherSubject){ // Brukes ved feilstesting og for å sjekke om et emne eksisterer
        if(!this.subjectName.equals(otherSubject.getSubjectName())) return false; 
        else if(!this.grade.equals(otherSubject.getGrade())) return false; 
        else return true; 
    }


    @Override
    public String toString() {
        return  this.getSubjectName() + ":" + this.getGrade();
    }
    
}
