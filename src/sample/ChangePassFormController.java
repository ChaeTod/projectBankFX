package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.AccessibleRole;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangePassFormController {

    public PasswordField txtOldUserPassword;
    public TextField txtOldUserPasswordText;
    public Button btnStepBack;
    public Button btnChangePass;
    public ToggleButton tglBtnShowInput;
    public PasswordField txtNewUserPass;
    public PasswordField txtRepeatNewUserPass;
    public TextField txtNewUserPassTxt;
    public TextField txtRepeatNewUserPassTxt;
    private static ServerController serverController = null;
    public Label lblTextErrorOldPass;
    public Label lblTextErrorNewPass;

    public void getServerController(ServerController serverController){
        ChangePassFormController.serverController = serverController;
    }

    public void makeChangePass(ActionEvent actionEvent) throws IOException {
        if (!serverController.changePassword(txtOldUserPassword.getText(), txtNewUserPass.getText())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Password hasn't been changed!");
            alert.setHeaderText(null);
            alert.setContentText("Please, try again!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Password has been changed successfully!");
            alert.setHeaderText(null);
            alert.setContentText("Your password has been changed! Next login use your new password!");
            alert.showAndWait();
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("sample.fxml"));
        Pane root = loader.load();

        //FXMLLoader mainLoader = new

        Stage newStage = new Stage();
        newStage.setScene(new Scene(root, 420, 300));
        newStage.setResizable(false);
        newStage.show();

        Stage window = (Stage) btnChangePass.getScene().getWindow();
        window.close();

       /* ServerController serverController = new ServerController();
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("menuform.fxml"));
        //Pane mainRoot = mainLoader.load();

        // set to FXML a command to use menuform.fxml instead of first fxml file. This will allow to communicate with instances from menuform.fxml
        MenuFormController menuFormController = mainLoader.getController();
        menuFormController.setLbl(serverController);

        Stage mainWindow = (Stage) menuFormController.btnLogOut.getScene().getWindow();
        mainWindow.close();
        ChangePassFormController.serverController.logOut();*/
    }

    public void x_FormClosed(Object sender){

    }

    public void closeThisWindow(ActionEvent actionEvent) throws IOException { // update the changePass button after Cancel button has been pushed
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("menuform.fxml"));
        AnchorPane pane = loader.load();

        MenuFormController menuFormController = loader.getController();
        menuFormController.btnChangeUserPass.setDisable(false);

        Stage window = (Stage) btnStepBack.getScene().getWindow();
        window.close();
    }

    public void toggleVisibleInputs(ActionEvent actionEvent) {  // show/hide user inputs
        txtOldUserPasswordText.textProperty().bindBidirectional(txtOldUserPassword.textProperty());
        txtOldUserPasswordText.visibleProperty().bind(tglBtnShowInput.selectedProperty().not());
        txtOldUserPassword.visibleProperty().bind(tglBtnShowInput.selectedProperty());

        txtNewUserPassTxt.textProperty().bindBidirectional(txtNewUserPass.textProperty());
        txtNewUserPassTxt.visibleProperty().bind(tglBtnShowInput.selectedProperty().not());
        txtNewUserPass.visibleProperty().bind(tglBtnShowInput.selectedProperty());

        txtRepeatNewUserPassTxt.textProperty().bindBidirectional(txtRepeatNewUserPass.textProperty());
        txtRepeatNewUserPassTxt.visibleProperty().bind(tglBtnShowInput.selectedProperty().not());
        txtRepeatNewUserPass.visibleProperty().bind(tglBtnShowInput.selectedProperty());

       /* System.out.println(txtOldUserPasswordText.getText());
        System.out.println(txtOldUserPassword.getText());*/

        System.out.println(txtNewUserPassTxt.getText());
        System.out.println(txtRepeatNewUserPassTxt.getText());
    }

    public void toggleInputs(ActionEvent actionEvent) {

    }

    public void isOldPassFieldEmpty(KeyEvent keyEvent) {  // check if there are no empty fields and is the old password not equal with new password and is new passwords are equals
        lblTextErrorOldPass.setText("");
        lblTextErrorNewPass.setText("");

        if (tglBtnShowInput.isSelected()) {
            if (!txtOldUserPassword.getText().isEmpty() && !txtNewUserPass.getText().isEmpty() && !txtRepeatNewUserPass.getText().isEmpty()) {
                if (txtOldUserPassword.getText().equalsIgnoreCase(txtNewUserPass.getText())) {
                    lblTextErrorOldPass.setText("New password cannot be equal with the old password!");
                    btnChangePass.setDisable(true);
                }
                else {
                    if (!txtNewUserPass.getText().equalsIgnoreCase(txtRepeatNewUserPass.getText())) {
                        lblTextErrorOldPass.setText("New passwords are not the same!");
                        btnChangePass.setDisable(true);
                    }
                    else
                        btnChangePass.setDisable(false);
                }
            }
        } else if (!txtOldUserPasswordText.getText().isEmpty() && !txtNewUserPassTxt.getText().isEmpty() && !txtRepeatNewUserPassTxt.getText().isEmpty()) {
            if (txtOldUserPasswordText.getText().equalsIgnoreCase(txtNewUserPassTxt.getText())) {
                lblTextErrorOldPass.setText("New password cannot be equal with the old password!");
                btnChangePass.setDisable(true);
            }
            else {
                if (!txtNewUserPassTxt.getText().equalsIgnoreCase(txtRepeatNewUserPassTxt.getText())) {
                    lblTextErrorOldPass.setText("New passwords are not the same!");
                    btnChangePass.setDisable(true);
                }
                else
                    btnChangePass.setDisable(false);
            }
        }
    }
}


    /*public boolean isNewPassDiffSelected(){
       if (txtOldUserPassword.equals(txt))
    }*/

