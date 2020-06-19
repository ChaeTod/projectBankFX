package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

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
    @FXML
    public Button btnChangeUserPass;
    public Button btnDeleteUserChat;
    public Label lblInfoTextMessage;
    public ListView lblVIewChat;
    @FXML
    private Label userlbl;
    private static String userName = null;
    private ServerController serverController = null;
    private Stage newStage = null;

    public void helpPressed() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Useful tips");
        alert.setHeaderText(null);
        alert.setContentText("Welcome " + userName + " to your personal form in out Stock Bank!");
        alert.showAndWait();
    }

    public void setLbl(ServerController serverController) { // update the top label with current use first and last name
        userlbl.setText(serverController.getFname() + " " + serverController.getLname());
        userName = serverController.getLogin();
        this.serverController = serverController;
    }

    public void doLogOut(ActionEvent actionEvent) throws IOException { // make logout = exit
        if (!serverController.logOut()) {
            String serverAnswer = serverController.getServerResponse().toString().replaceAll("\\p{P}", "").replace("Error", " ") + "!";
            lblInfoTextMessage.setText(serverAnswer);
        } else {
            System.exit(0);
        }
    }

    public void exitAction() {
        System.exit(0);
    } // make exit

    public void showLog(ActionEvent actionEvent) throws IOException { // get and show current user's log from DB
        btnShowLog.setDisable(true);
        btnChangeUserPass.setDisable(false);

        lblVIewChat.setItems(serverController.showLog());

        //txtLogArea.setText(serverController.showLog());

        btnUserChat.setDisable(false);
    }

    public void makeStepBack(ActionEvent actionEvent) throws IOException {  // return to the login form
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Pane root = loader.load();

        Stage newStage = new Stage();
        newStage.setScene(new Scene(root, 423, 350));
        newStage.setResizable(false);
        newStage.show();

        serverController.logOut();
        Stage window = (Stage) btnLogOut.getScene().getWindow();
        window.close();
    }

    public void sendNewMessage(ActionEvent actionEvent) throws IOException { // make a send message to chat
        if (txtMessageBody.getText().length() <= 100) {
            if (!serverController.sendMessage(txtMessageToUser.getText(), txtMessageBody.getText())) {
                String serverAnswer = serverController.getServerResponse().toString().replaceAll("\\p{P}", "").replace("Error", " ") + "!";
                lblInfoTextMessage.setText(serverAnswer);
                System.out.println(serverAnswer);
                /*txtLogin.setText("");    // show more errors if need
                txtPassword.setText("");
                btnLogIn.setDisable(true);*/
            } else {
                if (btnUserChat.isDisable()) {
                    Thread thread = new Thread(() -> Platform.runLater(() -> {
                        try {
                            btnDeleteUserChat.setDisable(false);
                            lblVIewChat.setItems(serverController.showUserChat());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }));
                    thread.start();
                    txtMessageBody.setText("");
                    btnSendMessage.setDisable(true);
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Too long message!");
            alert.setHeaderText(null);
            alert.setContentText("You're trying to send a very huge message! Please, cut it into smaller pieces.");
            alert.showAndWait();
        }
    }

    public void resetChangePassBtn() {
        this.btnChangeUserPass.setDisable(false);
    }

    public void isMessageAreaEmpty(KeyEvent keyEvent) {  // check is the message field is empty before unable send message button
        if (!txtMessageBody.getText().isEmpty() && !txtMessageToUser.getText().isEmpty())
            btnSendMessage.setDisable(false);
    }

    public void showUserChat(ActionEvent actionEvent) throws IOException { // show in the main text area a current user char from DB
        btnUserChat.setDisable(true);
        btnShowLog.setDisable(false);
        btnChangeUserPass.setDisable(false);

        if (btnUserChat.isDisable()) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        //txtLogArea.setText(serverController.showUserChat()); // not in use
                        lblVIewChat.setItems(serverController.showUserChat());
                        if (txtMessageBody.getText().isEmpty()){
                            btnSendMessage.setDisable(true);
                        }
                        if (!lblVIewChat.getItems().isEmpty())
                            btnDeleteUserChat.setDisable(false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void makeChangePass(ActionEvent actionEvent) throws IOException {  // show pop-up form with the change current message
        btnChangeUserPass.setDisable(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("changepassform.fxml"));
        Pane root = loader.load();

        ChangePassFormController changePassFormController = loader.getController();
        changePassFormController.getServerController(serverController);

        this.newStage = new Stage();
        this.newStage.setScene(new Scene(root, 340, 420));
        this.newStage.setResizable(false);
        this.newStage.show();
    }

    public void makeDeleteUserChat(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Do you really want to delete all chat with this user?");

        Optional<ButtonType> result = alert.showAndWait();  // question dialog with conformation
        if (result.get() == ButtonType.OK) {
            if (!serverController.deleteUserChat()) {
                Alert alert_1 = new Alert(Alert.AlertType.INFORMATION);
                alert_1.setTitle("Error!");
                alert_1.setHeaderText(null);
                alert_1.setContentText("Something went wrong! Re-check all the inputs!");
                alert_1.showAndWait();
            } else {
                Alert alert_2 = new Alert(Alert.AlertType.INFORMATION);
                alert_2.setTitle("Success!");
                alert_2.setHeaderText(null);
                alert_2.setContentText("User chat has been deleted!");
                alert_2.showAndWait();
                showUserChat(actionEvent);
            }
        } else {
            alert.close();
        }
    }

    public void checkIsItEmpty(MouseEvent mouseEvent) {
        if (!txtLogArea.getText().isEmpty())
            btnDeleteUserChat.setDisable(false);
    }

    public void showInfoChat(MouseEvent mouseEvent) {
        lblInfoTextMessage.setText("Start using chat between users!");
    }

    public void resetInfoLabel(MouseEvent mouseEvent) {
        lblInfoTextMessage.setText("");
    }

    public void showInfoLog(MouseEvent mouseEvent) {
        lblInfoTextMessage.setText("Show user's log");
    }

    public void showChangePassInfoLabel(MouseEvent mouseEvent) {
        lblInfoTextMessage.setText("Open change password form");
    }
}
