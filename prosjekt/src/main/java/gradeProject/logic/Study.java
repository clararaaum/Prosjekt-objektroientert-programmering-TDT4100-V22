package gradeProject.logic;

public class Study {
    private String studyName;
    private double averageLimit;

    public Study(String studyName, double averageLimit){ 
        if(validateStudyName(studyName)) this.studyName = studyName;
        else throw new IllegalArgumentException(studyName + " er ugyldig. Referansen/navnet til studiet skal bestå av fem store bokstaver");
        if(validateAverageLimit(averageLimit)) this.averageLimit = averageLimit;
        else throw new IllegalArgumentException(averageLimit + " er ugyldig. Snittet til studier må være mellom 0 og 10");
    }

    private boolean validateStudyName(String studyName){
        return studyName.matches("[A-Z]{5}"); 
    }

    private boolean validateAverageLimit(double averageLimit){
        if(averageLimit < 0  || averageLimit > 5){
            return false;
        }
        return true;
    }

    public String getStudyName() {
        return studyName;
    }

    public double getAverageLimit() {
        return averageLimit;
    }

    @Override
    public boolean equals(Object study){
        if(!this.studyName.equals(((Study) study).getStudyName())) return false;
        else if(!(this.averageLimit == (((Study) study).getAverageLimit()))) return false;
        else return true; 
    }
}
