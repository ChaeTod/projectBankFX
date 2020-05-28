package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    @FXML private Button btnLogIn;
    @FXML private TextField txtLogin;
    @FXML private  PasswordField txtPassword;
    @FXML private MenuItem MnBrHelp;

    //private static ServerController serverController = new ServerController();

    public void helpPressed() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Useful tips");
        alert.setHeaderText(null);
        alert.setContentText("Please, input your login and password to log on the server. Or sign up if you're here for the first time. Have a nice day!");
        alert.showAndWait();
    }

    public void signInButton() {
        //Main.setPane(2);
    }

    public void logInButton() throws IOException {
        ServerController serverController = new ServerController();
        serverController.logIn(txtLogin.getText(), txtPassword.getText());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("menuform.fxml"));
        Pane root = loader.load();

        // set to FXML a command to use menuform.fxml instead of first fxml file. This will allow to communicate with instances from menuform.fxml
        MenuFormController menuFormController = loader.getController();
        menuFormController.setLbl(serverController);

        Stage newStage = new Stage();
        newStage.setScene(new Scene(root, 730, 444));
        newStage.setTitle("Stock Banking");
        newStage.show();

        Stage window = (Stage) btnLogIn.getScene().getWindow();
        window.close();
    }

    /*
    public Scene thisStage() throws IOException {
        Pane root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Stage curSt = new Stage();
        curSt.setTitle("Welcome to Stock Bank");
        curSt.setScene(new Scene(root, 420, 300));
        curSt.show();
        return curSt.getScene();
    }
*/
    public void fieldInputCheck() {
        if (!txtLogin.getText().isEmpty() && !txtPassword.getText().isEmpty()) {
            btnLogIn.setDisable(false);
        }
    }

    public void exitAction() {
        System.exit(0);
    }
}

