package gradeProject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import gradeProject.logic.Subject;

import java.util.ArrayList;

public interface FileManager { 
    
    void writeDiplomaToFile(String filename, HashMap<String, ArrayList<Subject>> diplomas) throws IOException;
    
    HashMap<String, ArrayList<Subject>> readDiplomaFromFile(String filename) throws IOException;
    
    File getFile(String filename);
}
