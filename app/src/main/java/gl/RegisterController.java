package gl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import gl.data.Data;
import gl.data.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
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
    private void browseFile(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select JSON Configuration File");
        
        // Add filter for JSON files only
        FileChooser.ExtensionFilter jsonFilter = 
            new FileChooser.ExtensionFilter("JSON Files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(jsonFilter);
        
        // Show open dialog
        File selectedFile = fileChooser.showOpenDialog(browseButton.getScene().getWindow());
        
        // Update text field if a file was selected
        if (selectedFile != null) {
            filePathField.setText(selectedFile.getAbsolutePath());
        }
    }
    
    @FXML
    private void validate(ActionEvent e) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String filePath = filePathField.getText().trim();
        
        // Validate input
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Username and password are required.");
            return;
        }
        
        try {
            // Read existing users
            File usersFile = new File(USERS_FILE);
            List<User> users = new ArrayList<>();
            
            if (usersFile.exists() && usersFile.length() > 0) {
                try {
                    users = objectMapper.readValue(usersFile, 
                        objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
                } catch (IOException ex) {
                    // If file is corrupted or empty, start with empty list
                    System.err.println("Warning: Could not read users.json, starting fresh: " + ex.getMessage());
                    users = new ArrayList<>();
                }
            }
            
            // Check if username already exists
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    showAlert(Alert.AlertType.ERROR, "Registration Error", "Username already exists.");
                    return;
                }
            }
            
            // Load Data from config file if provided
            Data userData = new Data();
            if (!filePath.isEmpty()) {
                File configFile = new File(filePath);
                if (configFile.exists()) {
                    userData = objectMapper.readValue(configFile, Data.class);
                } else {
                    showAlert(Alert.AlertType.WARNING, "File Not Found", "Config file not found. Proceeding without it.");
                }
            }
            
            // Create new user
            User newUser = new User(username, password, userData);
            users.add(newUser);
            
            // Ensure directory exists
            usersFile.getParentFile().mkdirs();
            
            // Write back to users.json 
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(usersFile, users);
            
            showAlert(Alert.AlertType.INFORMATION, "Success", "User registered successfully!");
            
            // Clear fields
            usernameField.clear();
            passwordField.clear();
            filePathField.clear();

            
        } catch (IOException ex) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to register user: " + ex.getMessage());
            ex.printStackTrace();
        }


    }
    


}
