package gradeProject;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ForsideController {

    @FXML private Label header;
    @FXML private Button makeDiplomaButton;
    @FXML private Button lookUpDipButton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private void initialize(){
        header.setText("Velkommen til dette karaktersystemet!");
        //Dette er for å lage en mappe hvor alle vitnemålene etterhvert lagres
        //Hadde egentlig en tom mappe liggende i resources for dette, istedenfor å opprette den her,
        //men det ga problem med komprimeringsalgoritmen når vi skulle levere. På denne måten kommer vi rundt problemet
        File dir = new File("src" + File.separator + "main"+ File.separator + "resources"+File.separator+"gradeProject" +File.separator+ "savedDiplomas");
        dir.mkdirs();
    }

    @FXML
    //Når vi kaller denne metoden skal "scenen" endre seg slik at man kommer inn på Diploma.fxml
    //Fremgangsmåten er inspirert av denne videoen: https://www.youtube.com/watch?v=hcM-R-YOKkQ (03/03/2022)
    private void onMakeDiploma(ActionEvent event) throws IOException{
        System.out.println("Følgende er event "+event);
        this.root = FXMLLoader.load(getClass().getResource("Diploma.fxml"));
        this.scene = new Scene(this.root);
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        stage.setScene(this.scene);
        stage.show();
    }

    @FXML
    //Når vi kaller denne metoden skal "scenen" endre seg slik at man kommer inn på DiplomaDatabase.fxml
    //Fremgangsmåten er inspirert av denne videoen: https://www.youtube.com/watch?v=hcM-R-YOKkQ (03/03/2022)
    //Dette er altså samme kode som den rett over for onMakeDiploma(), men for å åpne den andre fxml-filen vår
    private void onLookUpDiploma(ActionEvent event) throws IOException{
        this.root = FXMLLoader.load(getClass().getResource("Database.fxml"));
        this.scene = new Scene(this.root);
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        stage.setScene(this.scene);
        stage.show();

    }
}
