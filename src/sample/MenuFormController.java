package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.omg.CORBA.CODESET_INCOMPATIBLE;

import java.io.IOException;
import java.util.Iterator;

public class MenuFormController {

    public MenuItem mnHlpBtb;
    public Button btnLogOut;
    public TextArea txtLogArea;
    public Button btnStepBack;
    public Button btnShowLog;
    public Button btnUserChat;
    public TextArea txtMessageBody;
    public TextField txtMessageToUser;
    public Button btnSendMessage;
    public Button btnChangeUserPass;
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
        System.exit(0);
    }

    public void exitAction() {
        System.exit(0);
    }

    public void showLog(ActionEvent actionEvent) throws IOException {
        btnShowLog.setDisable(true);
        btnChangeUserPass.setDisable(false);
        txtLogArea.setText(serverController.showLog());
        btnUserChat.setDisable(false);
    }

    public void makeStepBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Pane root = loader.load();

        Stage newStage = new Stage();
        newStage.setScene(new Scene(root, 420, 300));
        newStage.setResizable(false);
        newStage.show();

        serverController.logOut();
        Stage window = (Stage) btnLogOut.getScene().getWindow();
        window.close();
    }

    public void sendNewMessage(ActionEvent actionEvent) throws IOException {
        if (txtMessageBody.getText().length() <= 100){
           serverController.sendMessage(txtMessageToUser.getText(), txtMessageBody.getText());
           //return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Too long message!");
            alert.setHeaderText(null);
            alert.setContentText("You're trying to send a very huge message! Please, cut it into smaller pieces.");
            alert.showAndWait();
            //return false;
        }
    }

    public void isMessageAreaEmpty(KeyEvent keyEvent) {
        if (!txtMessageBody.getText().isEmpty() && !txtMessageToUser.getText().isEmpty())
            btnSendMessage.setDisable(false);
    }

    public void showUserChat(ActionEvent actionEvent) throws IOException {
        btnUserChat.setDisable(true);
        btnShowLog.setDisable(false);
        if (btnUserChat.isDisable()) {
            final DoubleProperty prog = new SimpleDoubleProperty(0){
                @Override protected void invalidated() {
                    try {
                        txtLogArea.setText(serverController.showUserChat());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            Timeline t = new Timeline();
            t.getKeyFrames().add(new KeyFrame(Duration.seconds(20), new KeyValue(prog, 1)));
            t.play();
        }
        //txtLogArea.setText(serverController.showUserChat());
    }

    public void makeChangePass(ActionEvent actionEvent) throws IOException {
        btnChangeUserPass.setDisable(true);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("changepassform.fxml"));
        Pane root = loader.load();

        Stage newStage = new Stage();
        newStage.setScene(new Scene(root, 450, 320));
        newStage.setResizable(false);
        newStage.show();
    }
}
