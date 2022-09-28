package gradeProject;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

import gradeProject.logic.Database;
import gradeProject.logic.Diploma;
import gradeProject.logic.Subject;
import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class DiplomaController extends Controller{
    private Database database; 

    @FXML private TextField usernameField;
    @FXML private TextField subjectField;
    @FXML private TextField gradeField;
    @FXML private Label informationField;

    @FXML private Button enterButton;
    @FXML private Button addSubject;
    @FXML private Button removeSubject;
    @FXML private Button addDiploma;
    @FXML private Button frontPageButton;
    
    
    @FXML
    @Override
    protected void initialize(){           
        usernameField.setEditable(true);
        subjectField.setEditable(false);// Kan ikke skrive inn karakterer før man har skrevet inn brukernavnt
        gradeField.setEditable(false);
        informationField.setText("Velkommen til karaktersystemet!\nLegg inn et brukernavn og\ntrykk 'Enter', dette kan ikke\nendres.");
    }

    @FXML
    //Bruker konsekvent e.getMessage() i unntakshåndteringen. Henter da altså ulike unntak
    //som blir kastet i Diploma.Java
    private void onEnterButton(){ 
        database = new Database();
        try{
            database.validateUsername(this.usernameField.getText()); //Kaller på funksjon i Database.java for å sjekke 
            //om brukernavnet allerede eksisterer i form av sjekk om det finnes en fil med samme navn som brukernavnet som er skrevet inn
            try{ 
                this.diploma = new Diploma(usernameField.getText()); //Sjekker at brukernavnet er gyldig format
                informationField.setText("Legg til karakterene dine og\ntrykk på 'Lagre Vitnemål'\nsenere kan du finne vitnemålet\nmed utregninger.\n\nEmner format: ABC1234\nKarakter på skala: A-F");
                usernameField.setEditable(false); // Nå kan du ikke endre brukernavnet
                subjectField.setEditable(true);// Da kan man begynne å skrive inn karakterer
                gradeField.setEditable(true);
            }catch (IllegalArgumentException e){
                showErrorMessage(e.getMessage()); // Får beskjed om at brukernavnet er ugyldig
            }catch(NoSuchElementException e){
                //Ingenting skjer hvis man trykker på "cancel"/lukker alerten
            }

        }catch(IllegalArgumentException e){
            //showErrorMessage("Brukernavnet er opptatt");
            showErrorMessage(e.getMessage());
        }
        catch(NoSuchElementException e){
            //Ingenting skjer hvis man trykker på "cancel"/lukker alerten
        }
        System.out.println(usernameField.getText()); //For å kunne se brukernavnet i terminalen
    }

    @FXML
    private void onAddSubject(){
        try{
            Subject subject = new Subject(this.subjectField.getText(), this.gradeField.getText());
            diploma.addSubject(subject);
            updateDisplay();
        }
        catch (IllegalArgumentException e){
            showErrorMessage(e.getMessage()); //Ugyldig emnekode
        }
        catch (IllegalStateException e){
            showErrorMessage(e.getMessage()); //Faget er allerede lagt til
        }
        catch(NoSuchElementException e){
            //Ingenting skjer hvis man trykker på "cancel"/lukker alerten
        }
        // Tømmer inntastningsfeltet
        subjectField.clear();
        gradeField.clear();
    }


    @FXML
    private void onRemoveSubject(){
        try{
            int selectedIndex = subjectView.getSelectionModel().getSelectedIndex();
            diploma.removeSubject(selectedIndex);
            updateDisplay();
        }
        catch (NullPointerException n){
            showErrorMessage("Legg inn et brukernavn og emner før du fjerner dem");
        }
        catch (IllegalStateException e){
            showErrorMessage(e.getMessage()); //Må velge et fag for å fjerne det
        }
        catch (NoSuchElementException e){
            //Ingenting skjer hvis man trykker på "cancel"/lukker alerten
        }
        subjectField.clear();
        gradeField.clear();
    }


    @FXML
    private void onAddDiploma(){
        try{
            database.addDiplomaToDatabase(diploma); // logikk implmenteres utenfor controlleren
            
            //Her skal vi skrive vitnemålet til filen
            DatabaseManager fileManager = new DatabaseManager();
            try{ //Skriver til filen
                fileManager.writeDiplomaToFile(usernameField.getText(), database.getDatabase());
                showConfirmMessage("Vitnemålet ble lagret :)");
            } 
            catch (FileNotFoundException e){
                System.out.println("File not found");
            }
            //Gjør slik at man etter å ha lagret vitnemålet kan skrive inn et nytt et
            usernameField.clear();
            subjectView.getItems().clear();
            usernameField.setEditable(true);
            informationField.setText("Velkommen til karaktersystemet!\nLegg inn et brukernavn og\ntrykk 'Enter', dette kan ikke\nendres.");
            subjectField.setEditable(false);
            gradeField.setEditable(false);
        }
        catch(IllegalStateException e){
            showErrorMessage(e.getMessage());//Kan ikke lagre tomt vitnemål
        }
        catch(NoSuchElementException e){
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

    private void showConfirmMessage(String message){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Lagre vitnemål");
        alert.setContentText(message);
        alert.showAndWait();
    }

    

}
