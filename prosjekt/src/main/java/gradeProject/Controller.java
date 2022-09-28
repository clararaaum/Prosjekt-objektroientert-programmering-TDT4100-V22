package gradeProject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gradeProject.logic.Computations;
import gradeProject.logic.Diploma;
import gradeProject.logic.Subject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public abstract class Controller {
    protected Stage stage;
    protected Scene scene;
    protected Parent root;

    protected Diploma diploma;
    protected Computations computations;


    @FXML protected ListView<String> subjectView;

    protected abstract void initialize();

    public List<String> printSubjectList(){ 
        List<String> displayList = new ArrayList<>();

        for (Subject subject : diploma.getSortedSubjects()) {
            displayList.add(subject.getSubjectName() + "            - " + subject.getGrade());
        }
        return displayList;
    } 

    protected void updateDisplay(){ // protected fordi StudiesController redefinerer denne
        subjectView.getItems().setAll(printSubjectList());
    }

    @FXML
    //Bruker samme prinsipp som de to metodene vi finner i ForsideController.java, se forklaring og kildebruk der
     private void onFrontpageButton(ActionEvent event) throws IOException{
         this.root = FXMLLoader.load(getClass().getResource("Forside.fxml"));
         this.scene = new Scene(this.root);
         this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
         stage.setScene(this.scene);
         stage.show();       
     }
    
}
