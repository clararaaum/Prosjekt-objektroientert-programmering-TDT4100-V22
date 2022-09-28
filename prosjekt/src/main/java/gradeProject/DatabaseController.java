package gradeProject;

import java.io.IOException;
import java.util.NoSuchElementException;

import gradeProject.logic.Computations;
import gradeProject.logic.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DatabaseController extends Controller{
    private Database database; 
    
    private String username;
    
    @FXML private TextField usernameField;
    
    @FXML private Pane resultsPane;
    @FXML private Label averageField;
    @FXML private Label medianField;
    @FXML private AnchorPane testPane;

    @FXML private BarChart<String, Number> barChart;


    @FXML
    @Override
    protected void initialize(){ 
        usernameField.setEditable(true);
        subjectView.setEditable(false);
    }

    @FXML 
    private void onSearchUsernameButton() {
        try{
            FileManager fileManager = new DatabaseManager(); 

            // Prøver å lese fra en filen gitt ved username.txt 
            this.username = usernameField.getText();
            this.database = new Database(fileManager.readDiplomaFromFile(username));
            this.diploma = database.getDiplomaFromDatabase(username);

            // Snitt og median skal printes til GUI
            this.computations = new Computations(this.diploma);
            this.computations.computeAverage();
            this.computations.computeMedian();
            averageField.setText(computations.getAverage());
            medianField.setText(computations.getMedian());

            // Oppdaterer søylediagrammet
            this.updateBarChart(); 
            this.updateDisplay();
        } 
        //Bruker konsekvent e.getMessage() for å hente unntakene som utløses i Database.java
        catch (IOException e){
            showErrorMessage("Finner ikke noe vitnemål til dette brukernavnet");
            System.out.println(e);
        }
        catch (NoSuchElementException e){
            //Ingenting skjer hvis man trykker på "cancel"/lukker alerten
        }
    }


    

    private void showErrorMessage(String message){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Obs, en feil har oppstått");
        alert.setContentText(message);
        alert.showAndWait();
    }


    @SuppressWarnings("unchecked") //Unchecked betyr bare at man slipper en oransje linje under nederste linje i metoden
    //https://www.youtube.com/watch?v=shB0AHNNLOw (31/03/22) 
    private void updateBarChart(){        
        barChart.setTitle("Karakterfordeling");
		Series<String, Number> chart = new XYChart.Series<>();
        chart.getData().add(new XYChart.Data<>("A", diploma.getInstanceOfGrade("A")));
        chart.getData().add(new XYChart.Data<>("B", diploma.getInstanceOfGrade("B")));
        chart.getData().add(new XYChart.Data<>("C", diploma.getInstanceOfGrade("C")));
        chart.getData().add(new XYChart.Data<>("D", diploma.getInstanceOfGrade("D")));
        chart.getData().add(new XYChart.Data<>("E", diploma.getInstanceOfGrade("E")));
        chart.getData().add(new XYChart.Data<>("F", diploma.getInstanceOfGrade("F")));
        barChart.getData().setAll(chart);
        barChart.setAnimated(false);
    }


    

    @FXML
    // Her er en annen kilde brukt enn for de andre, dette er fordi denne hjelper oss med 
    // å kunne sende data mellom kontrollerne: 
    // https://jagar.me/post/passingdatainjavafx/ (31/03/22)
    private void onNextButton(ActionEvent event) throws IOException{
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Studies.fxml"));
            this.root = loader.load();

            // Finner til å skrive ut brukernavnet og vitnemål i StudiesController
            StudiesController studiesController = loader.getController();
            studiesController.setUsername(usernameField.getText()); 
            studiesController.setDiploma(diploma);
            
            this.scene = new Scene(this.root);
            this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            stage.setScene(this.scene);
            stage.show();
        } catch(NullPointerException e){
            showErrorMessage("Du kan ikke få videre før du har søkt opp et vitnemål");
        } catch (NoSuchElementException e){
            //Ingenting skjer hvis man trykker på "cancel"/lukker alerten
        }
    }
    
}
