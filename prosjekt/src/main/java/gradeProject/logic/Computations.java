package gradeProject.logic;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class Computations {
    private double median; 
    private double average;
    private List<Subject> subjects;

    public Computations(Diploma diploma){
        this.subjects = diploma.getSubjects();
    }

    public void computeAverage() { 
        double sumGrades = 0; 
        if(subjects.size() > 0){
            for (Subject subject : subjects) {
                sumGrades += getGradeValue(subject);
            }
            this.average = sumGrades/subjects.size();  
        } else{
            throw new IllegalStateException("Kan ikke beregene gjennomsnitt på en tom liste");    
        }
    }

    public void computeMedian() { 
        if(subjects.size() > 0){
            subjects.sort((a, b) -> getGradeValue(b) - getGradeValue(a)); // sorterer beste til verste med beste først
            int indexMiddleOfList = subjects.size()/2; 
            if(subjects.size() % 2 != 0){ // oddetall
                this.median = (double) getGradeValue(subjects.get(indexMiddleOfList));
            } 
            else { 
                int sumTwoMid = getGradeValue(subjects.get(indexMiddleOfList)) + getGradeValue(subjects.get(indexMiddleOfList-1));
                this.median = (double) sumTwoMid/2;  
            }
        }
        else{
            throw new IllegalStateException("Kan ikke beregene median på en tom liste");
        }
    }

    public static int getGradeValue(Subject subject){ // Diploma.java bruker for å kunne sortere
        switch(subject.getGrade()){
            case "A": return 5;
            case "B": return 4;
            case "C": return 3;
            case "D": return 2;
            case "E": return 1;
            default: return 0; //Kan sette default til å få F=0 siden vi allerede har sjekket at man ikke kan legge inn noe annet enn A-F
        }
    }

    // For å rette opp i spetakkelet komma VS punktum skapte, ble vi inspirert av https://java.tutorialink.com/how-to-change-the-decimal-separator-of-decimalformat-from-comma-to-dot-point/
    public String getAverage(){
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator(',');
        DecimalFormat df = new DecimalFormat("#.##", otherSymbols);
        return df.format(this.average);
    }

    public String getMedian(){
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator(',');
        DecimalFormat df = new DecimalFormat("#.##", otherSymbols);
        return df.format(this.median);
    }

    public double getNumAverage(){ // Ikke avrundet; For å kunne filtere på studier
        return this.average;
    }
}
