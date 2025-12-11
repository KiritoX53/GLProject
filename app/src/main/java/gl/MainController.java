package gl;

import gl.data.User;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainController implements SceneController {
    private User user;

    @FXML
    private Label welcomeLabel;
    public void initialize() {
        user = Util.currentUser;
        welcomeLabel.setText("Welcome, " + user.getUsername() + "~\n Let's get to planning!");
    }
    @FXML
    private Button goalsButton;
    @FXML
    private Button transactionsButton;
    @FXML
    private Button budgetsButton;

    @FXML
    private void goToGoals() throws Exception {
        Scene scene = getScene("goals.fxml");
        Stage stage = (Stage) goalsButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
}
    
    @FXML
    private void goToTransactions() throws Exception {
        Scene scene = getScene("transactions.fxml");
        Stage stage = (Stage) transactionsButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void goToBudgets() throws Exception {
        Scene scene = getScene("budgets.fxml");
        Stage stage = (Stage) budgetsButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

}
}


//  linear-gradient(to bottom,rgba(252, 176, 69, 1) 0%, rgba(253, 29, 29, 1) 50%, rgba(131, 58, 180, 1) 95%);