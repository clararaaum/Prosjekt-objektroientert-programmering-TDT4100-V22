package gradeProject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gradeProject.logic.Computations;
import gradeProject.logic.Diploma;
import gradeProject.logic.FilterStudies;
import gradeProject.logic.Study;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class StudiesController extends Controller {
    private FilterStudies filterStudies;

    @FXML
    private Label usernameLabel;
    @FXML
    private Label informationField;
    @FXML
    private Label averageField;

    @FXML
    private ListView<String> studiesListView;

    @FXML
    @Override
    protected void initialize() {
        subjectView.setEditable(false);
        studiesListView.setEditable(false);
    }

    @FXML
    public void setUsername(String username) {
        usernameLabel.setText(username + " sitt vitnemål: ");
    }

    @FXML
    public void setDiploma(Diploma diploma) { 
        informationField.setText("Studier du kan komme\ninn på er:");

        this.diploma = diploma;
        this.computations = new Computations(this.diploma);
        this.filterStudies = new FilterStudies(this.diploma);

        computations.computeAverage();
        averageField.setText(computations.getAverage());

        this.updateDisplay();
    }

    // Metode for å skrive ut listene
    @Override
    protected void updateDisplay() {
        subjectView.getItems().setAll(printSubjectList());
        studiesListView.getItems().setAll(printStudiesList());
    }

    private List<String> printStudiesList() {
        List<String> displayList = new ArrayList<>();
        for (Study study : filterStudies.getFilteredStudies()) {
            displayList.add(study.getStudyName() + "                - " + study.getAverageLimit());
        }
        return displayList;
    }

    @FXML
    private void onBackButton(ActionEvent event) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("Database.fxml"));
        this.scene = new Scene(this.root);
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(this.scene);
        stage.show();
    }

}
