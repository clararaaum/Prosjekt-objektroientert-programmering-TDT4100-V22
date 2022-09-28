package gradeProject.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterStudies {
    private Computations computations;
    private List<Study> applicableStudies; 

    public static final Study[] studiesList = {new Study("MTIOT", 4.99), new Study("MTFYM", 4.70), new Study("MTMAR", 4.85), new Study("MTDTT", 4.92), new Study("MTING", 4.71), new Study("BAKKF", 2.42), 
        new Study("MARKK", 3.51), new Study("MADOM", 2.12), new Study("BDRAM", 1.20)};

    public FilterStudies(Diploma diploma){
        this.computations = new Computations(diploma);
    }
    
    public List<Study> getFilteredStudies(){
        this.filterStudies();
        return new ArrayList<Study>(this.applicableStudies); // Returnerer en kopi av listen for å unngå modifisering og brudd på innkapsling av den opprinnelige listen 
    }

    private void filterStudies(){  
        computations.computeAverage();
        applicableStudies = Arrays.stream(studiesList)
            .filter(x -> computations.getNumAverage() >= x.getAverageLimit())
            .toList();
    } 
}
