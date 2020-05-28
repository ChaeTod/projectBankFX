package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.omg.CORBA.CODESET_INCOMPATIBLE;

import java.io.IOException;

public class MenuFormController {

    public MenuItem mnHlpBtb;
    public Button btnLogOut;
    public TextArea txtLogArea;
    public Button btnStepBack;
    @FXML
    private Label userlbl;
    private static String userName = null;
    private ServerController serverController = null;

    public void helpPressed() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Useful tips");
        alert.setHeaderText(null);
        alert.setContentText("Welcome " + userName + " to your personal form in out Stock Bank!");
        alert.showAndWait();
    }

    public void setLbl(ServerController serverController){
        userlbl.setText(serverController.getFname() + " " + serverController.getLname());
        userName = serverController.getLogin();
        this.serverController = serverController;
    }

    public void doLogOut(ActionEvent actionEvent) throws IOException {
        serverController.logOut();
        Stage window = (Stage) btnLogOut.getScene().getWindow();
        window.close();
    }

    public void exitAction() {
        System.exit(0);
    }

    public void showLog(ActionEvent actionEvent) throws IOException {
        txtLogArea.setText(serverController.showLog());
    }

    public void makeStepBack(ActionEvent actionEvent) throws IOException {
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        //Pane root = loader.load();
/*
        Controller controller = new Controller();
        Stage newStage = new Stage();
        newStage.setScene(controller.thisStage());
        newStage.show();

        Stage window = (Stage) btnLogOut.getScene().getWindow();
        window.close();

 */
    }
}
