package sample;

import javafx.scene.AccessibleRole;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ChangePassFormContoller {

    public PasswordField txtOldUserPassword;
    public TextField txtOldUserPasswordText;

    public void makeInputsVisible(MouseEvent mouseEvent) {
        //txtOldUserPassword.set
        txtOldUserPasswordText.setAccessibleRole(AccessibleRole.PASSWORD_FIELD);
        System.out.println(txtOldUserPassword.getText());
    }
}
