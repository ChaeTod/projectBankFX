package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    public Label lblErrorText;
    public Label lblMainName;
    public Label lblMainName1;
    public Label lblErrorText1;
    @FXML
    private Button btnLogIn;
    @FXML
    private TextField txtLogin;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private MenuItem MnBrHelp;

    //private static ServerController serverController = new ServerController();

    public void helpPressed() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Useful tips");
        alert.setHeaderText(null);
        alert.setContentText("Please, input your login and password to log on the server. Or sign up if you're here for the first time. Have a nice day!");
        alert.showAndWait();
    }

    public void signInButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("signupform.fxml"));
        Pane root = loader.load();

        Stage newStage = new Stage();
        newStage.setScene(new Scene(root, 420, 470));
        newStage.setTitle("Register new user");
        newStage.setResizable(false);
        newStage.show();

        Stage window = (Stage) btnLogIn.getScene().getWindow();
        window.close();
    }

    public void logInButton() throws IOException {
        ServerController serverController = new ServerController();
        if (!serverController.logIn(txtLogin.getText(), txtPassword.getText())) {
            String serverAnswer = serverController.getServerResponse().toString().replaceAll("\\p{P}", "").replace("Error", " ") + "!";

            lblMainName1.setVisible(false);
            lblMainName.setVisible(true);

            if (!serverAnswer.contains("banned")){
                lblErrorText.setVisible(true);
                lblErrorText.setText(serverAnswer);
                lblErrorText1.setVisible(false);
            }
            else {
                lblErrorText1.setVisible(true);
                lblErrorText1.setText(serverAnswer);
                lblErrorText.setVisible(false);
            }

            //System.out.println(serverAnswer);

            txtLogin.setText("");
            txtPassword.setText("");
            btnLogIn.setDisable(true);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("menuform.fxml"));
            Pane root = loader.load();

            // set to FXML a command to use menuform.fxml instead of first fxml file. This will allow to communicate with instances from menuform.fxml
            MenuFormController menuFormController = loader.getController();
            menuFormController.setLbl(serverController);

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root, 730, 520));
            newStage.setTitle("Stock Banking");
            newStage.setResizable(false);
            newStage.show();

            Stage window = (Stage) btnLogIn.getScene().getWindow();
            window.close();
        }
    }

    public void fieldInputCheck() {
        if (!txtLogin.getText().isEmpty() && !txtPassword.getText().isEmpty()) {
            btnLogIn.setDisable(false);
        }
    }

    public void exitAction() {
        System.exit(0);
    }
}

