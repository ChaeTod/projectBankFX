package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpFormController {

    public Button btnSignIn;
    public Button btnCancelToLogForm;
    public TextField fldNewUserFName;
    public TextField fldNewUserLName;
    public TextField fldNewUserLogin;
    public PasswordField fldNewUserPassword;

    public static final Pattern USER_NAME = Pattern.compile("[a-zA-Z0-9.\\-_]{3,}");
    //public static final Pattern USER_NAME = Pattern.compile("^([a-zA-Z])+([\\w]{2,})+$");
    public static final Pattern USER_LOGIN = Pattern.compile("^([a-zA-Z])+([\\w]{2,})+$");
    public static final Pattern USER_PASSWORD = Pattern.compile("^([a-zA-Z])+([\\w]{2,})+$");
    public static final String INFO_LBL = "Input your credentials";
    public Label lblTopInfoText;
    public Label lblHelpInfo;

    public void isFieldEmpty() {
        if (!fldNewUserFName.getText().isEmpty() && !fldNewUserLName.getText().isEmpty() && !fldNewUserLogin.getText().isEmpty() && !fldNewUserPassword.getText().isEmpty()) {
            btnSignIn.setDisable(false);
        }
    }

    public void signInNewUser(ActionEvent actionEvent) throws IOException {
        if (!isVerifyName(fldNewUserFName.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("You put wrong user first name!");
            alert.setHeaderText(null);
            alert.setContentText("Please, make sure that it starts from big letter and has no numbers or symbols!");
            alert.showAndWait();
            btnSignIn.setDisable(true);
        }
        if (!isVerifyName(fldNewUserLName.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("You put wrong user last name!");
            alert.setHeaderText(null);
            alert.setContentText("Please, make sure that it starts from big letter and has no numbers or symbols!");
            alert.showAndWait();
            btnSignIn.setDisable(true);
        }
        if (!isVerifyLogin(fldNewUserLogin.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("You put wrong user login!");
            alert.setHeaderText(null);
            alert.setContentText("Please, make sure that it starts from big letter and has no double words or unsuspected symbols!");
            alert.showAndWait();
            btnSignIn.setDisable(true);
        }
        if (!isVerifyPassword(fldNewUserPassword.getText())) {
            btnSignIn.setDisable(true);
        }

        ServerController serverController = new ServerController();
        if (!serverController.registerNewUser(fldNewUserFName.getText(), fldNewUserLName.getText(), fldNewUserLogin.getText(), fldNewUserPassword.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText(null);
            alert.setContentText("Attempt of a new user creating was failed!");
            alert.showAndWait();
            returnBackWard(actionEvent);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Congratulation!");
            alert.setHeaderText(null);
            alert.setContentText("You have successfully registered in Stock Bank app! Now you can log in to the system.");
            alert.showAndWait();
            returnBackWard(actionEvent);
        }
    }

    public void returnBackWard(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Pane root = loader.load();

        //Controller controller = new Controller();
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root, 420, 300));
        newStage.setResizable(false);
        newStage.show();

        Stage window = (Stage) btnCancelToLogForm.getScene().getWindow();
        window.close();
    }

    // User's inputs verification
    public boolean isVerifyName(String newUserName) {
        Matcher matcher_name = USER_NAME.matcher(newUserName);
        return matcher_name.matches();
    }

    public boolean isVerifyLogin(String newUserLogin) {
        Matcher matcher_login = USER_NAME.matcher(newUserLogin);
        return matcher_login.matches();
    }

    public boolean isVerifyPassword(String newUserPassword) {
        boolean hasLetter = false;
        boolean hasDigit = false;

        if (newUserPassword.length() >= 8) {
            for (int i = 0; i < newUserPassword.length(); i++) {
                char x = newUserPassword.charAt(i);
                if (Character.isLetter(x))
                    hasLetter = true;
                else if (Character.isDigit(x))
                    hasDigit = true;
                if (hasLetter && hasDigit)
                    break;
            }
            if (hasLetter && hasDigit) {
                System.out.println("STRONG");
                return true;
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Your password isn't strong enough!");
                alert.setHeaderText(null);
                alert.setContentText("For your own safety we suggest you to make it more complicated. For example - add to it several symbols with different case and more numbers!");
                alert.showAndWait();
                if (alert.getResult() != ButtonType.OK) {
                    System.out.println("I'm gonna leave with it");
                    return false;
                } else {
                    System.out.println("OK, I'm gonna change it!");
                    return true;
                }
            }
        } else {
            System.out.println("You need to have at least 8 characters!");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText(null);
            alert.setContentText("You need to have at least 8 characters for a password");
            alert.showAndWait();
            return false;
        }
    }

    public void showHelpInfoFname(MouseEvent mouseEvent) {
        lblTopInfoText.setText("Please, input your first name");
    }

    public void resertHelpInfo(MouseEvent mouseEvent) {
        lblHelpInfo.setText(INFO_LBL);
    }

    public void showHelpInfoLname(MouseEvent mouseEvent) {
        lblHelpInfo.setText("Please, input your last name");
    }

    public void showHelpInfoLogin(MouseEvent mouseEvent) {
        lblHelpInfo.setText("Please, input your appreciated user login. You will need it make a login to the system.");
    }

    public void showHelpInfoPassword(MouseEvent mouseEvent) {
        lblHelpInfo.setText("Please, input your user password. Note - it must be at least 8 characters in it.");
    }
}
