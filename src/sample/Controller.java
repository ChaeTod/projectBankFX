package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Controller {
    @FXML private Label userlbl;
    @FXML private Button btnLogIn;
    @FXML private TextField txtLogin;
    @FXML private TextField txtPassword;
    @FXML private MenuItem MnBrHelp;

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
        serverController.logIn(txtLogin.getText(), txtPassword.getText()); //make user login to the server
        System.out.println(ServerController.getFname() +" " + ServerController.getLname() + " " + ServerController.getLogin() + " " + ServerController.getToken());

        userlbl.setText(ServerController.getFname() + " " + ServerController.getLname());

        Stage window = (Stage) btnLogIn.getScene().getWindow();

        //FXMLLoader fxmlLoader = new FXMLLoader().load(getClass().getResource("D:\\Study\\projectBankFX\\src\\sample\\menuform.fxml").openStream());
        Stage newStage = new Stage(new FXMLLoader().load(getClass().getResource("D:\\Study\\projectBankFX\\src\\sample\\menuform.fxml").openStream()));
        try {

            //Pane root = fxmlLoader.load(getClass().getResource("D:\\Study\\projectBankFX\\src\\sample\\menuform.fxml").openStream());
            newStage.setScene(new Scene(newStage.getScene().getRoot(), 1000, 500));
            newStage.setTitle("Stock Bank Main");
            newStage.initStyle(StageStyle.TRANSPARENT);
            //Sfxml sfxml=fxmlLoader.getController();
            //sfxml.initData(stage);
            newStage.showAndWait();
            //Main st = new Main();
            //st.setNewForm();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        window.close();
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

