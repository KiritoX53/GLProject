package gl;

import gl.data.Data;
import gl.data.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginController implements SceneController {
    
    private static final String USERS_FILE = "data/users/users.json";
    private final ObjectMapper objectMapper;
    
    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;
    
    public LoginController() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }
    
    @FXML
    public void validate(ActionEvent event) throws IOException {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        
        // Validate input
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Username and password are required.");
            return;
        }
        
        // Read users file
        File usersFile = new File(USERS_FILE);
        
        if (!usersFile.exists()) {
            showAlert(Alert.AlertType.ERROR, "Login Error", "No users registered. Please register first.");
            return;
        }
        
        List<User> users = new ArrayList<>();
        
        if (usersFile.length() > 0) {
            try {
                users = objectMapper.readValue(usersFile, 
                    objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
            } catch (IOException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to read users file: " + ex.getMessage());
                return;
            }
        }
        
        // Search for user
        User foundUser = null;
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                foundUser = user;
                break;
            }
        }
        
        // Check if user exists
        if (foundUser == null) {
            showAlert(Alert.AlertType.ERROR, "Login Error", "Username not found.");
            return;
        }
        
        // Check password
        if (!foundUser.getPassword().equals(password)) {
            showAlert(Alert.AlertType.ERROR, "Login Error", "Incorrect password.");
            return;
        }
        
        // Login successful
        showAlert(Alert.AlertType.INFORMATION, "Success", "Login successful! Welcome " + username + "!");

        usernameField.clear();
        passwordField.clear();
    }
    
}