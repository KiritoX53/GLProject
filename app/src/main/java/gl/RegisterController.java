package gl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import gl.data.Data;
import gl.data.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class RegisterController implements SceneController {
    
    private static final String USERS_FILE = "data/users/users.json";
    private final ObjectMapper objectMapper;
    
    public RegisterController() {
        objectMapper = new ObjectMapper();
        // Configure ObjectMapper to work with Java modules
        objectMapper.findAndRegisterModules();
    }
    
    private File selectedFile = null;
    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private TextField filePathField;
    
    @FXML
    private Button browseButton;
    
    @FXML
    private Button validateButton;
    
    @FXML
    private Button toLogin;
    @FXML
private void validate(ActionEvent e) throws IOException {
    String username = usernameField.getText().trim();
    String password = passwordField.getText().trim();

    if (selectedFile != null) {
        try {
            Path sourcePath = selectedFile.toPath();
            Path targetPath = Path.of(USERS_FILE);

            // Ensure the directory exists
            Path targetDir = targetPath.getParent();
            if (targetDir != null && !Files.exists(targetDir)) {
                Files.createDirectories(targetDir);
            }

            // Copy to the target location
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

            showAlert(
                Alert.AlertType.INFORMATION,
                "Import Success",
                "User data successfully imported from: " + selectedFile.getName()
            );

        } catch (IOException er) {
            showAlert(
                Alert.AlertType.ERROR,
                "Import Error",
                "Failed to copy file: " + er.getMessage()
            );
            System.err.println("File copy failed: " + er.getMessage());
            er.printStackTrace();
        }
    }

    // Validate input
    if (username.isEmpty() || password.isEmpty()) {
        showAlert(Alert.AlertType.ERROR, "Validation Error", "Username and password are required.");
        return;
    }

    List<User> users = loadUsers(objectMapper);

    if (checkUserExist(username, objectMapper)) {
        showAlert(Alert.AlertType.ERROR, "Registration Error", "Username already exists. Please choose another.");
        return;
    }

    // Create new user
    User newUser = new User(username, password, new Data());
    users.add(newUser);
    Util.currentUser = newUser;

    saveCurrentUser(objectMapper);

    showAlert(Alert.AlertType.INFORMATION, "Success", "User registered successfully!");
    goToMain();
}




   @FXML
    private void goToLogin(ActionEvent e) throws IOException {
        Scene scene = getScene("login.fxml");
        Stage stage = (Stage) toLogin.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void goToMain() throws IOException {
        Scene scene = getScene("main.fxml");
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
}






    @FXML
    private void browseFile(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select JSON Configuration File");
        
        // Add filter for JSON files only
        FileChooser.ExtensionFilter jsonFilter = 
            new FileChooser.ExtensionFilter("JSON Files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(jsonFilter);
        
        // Show open dialog
        selectedFile = fileChooser.showOpenDialog(browseButton.getScene().getWindow());
        
        // Update text field if a file was selected
        if (selectedFile != null) {
            filePathField.setText(selectedFile.getAbsolutePath());
        }
    }
}