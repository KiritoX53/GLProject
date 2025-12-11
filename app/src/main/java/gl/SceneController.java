package gl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import gl.data.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;


public interface SceneController {

    default File getUsersFile() {
        return new File(Util.USERS_FILE);
    }

    default Scene getScene(String sceneName) throws IOException {
        URL url = getClass().getResource(sceneName);
        if (url == null) {
        throw new IOException("FXML resource not found: " + sceneName);
    }
        Parent root = FXMLLoader.load(url);
        return new Scene(root);
    }

    @SuppressWarnings("exports")
    default FXMLLoader getLoader(String sceneName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneName));
        return loader;
    }

    default  void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


@SuppressWarnings("exports")
default void saveCurrentUser(ObjectMapper objectMapper) {
    try {
        File usersFile = new File(Util.USERS_FILE);

        // Ensure parent directory exists
        if (usersFile.getParentFile() != null) {
            usersFile.getParentFile().mkdirs();
        }

        ArrayList<User> users = new ArrayList<>();

        // Load existing users
        if (usersFile.exists() && usersFile.length() > 0) {
            try {
                users = objectMapper.readValue(
                    usersFile,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, User.class)
                );
            } catch (IOException ex) {
                System.err.println("Warning: Could not read users.json, starting fresh: " + ex.getMessage());
                users = new ArrayList<>();
            }
        }

        // Find user
        User foundUser = null;
        for (User user : users) {
            if (user.getUsername().equals(Util.currentUser.getUsername())) {
                foundUser = user;
                user.setUsername(Util.currentUser.getUsername());
                user.setPassword(Util.currentUser.getPassword());
                user.setData(Util.currentUser.getData());
                break;
            }
        }

        // If user not found, add them
        if (foundUser == null) {
            users.add(Util.currentUser);
        }

        // Write back to JSON
        usersFile.getParentFile().mkdirs();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(usersFile, users);

    } catch (Exception e) {
        e.printStackTrace();
    }
}


    default boolean checkUserExist(User user, ObjectMapper objectMapper){
        boolean found = false;

        List<User> users = new ArrayList<>();
        File usersFile = new File(Util.USERS_FILE);
        if (usersFile.exists() && usersFile.length() > 0) {
            try {
                users = objectMapper.readValue(usersFile,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
            } catch (IOException ex) {
                System.err.println("Warning: Could not read users.json, starting fresh: " + ex.getMessage());
                users = new ArrayList<>();
            }
        }
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())) {
                found = true;
                break;
            }
        }
        return found;
    }





    default boolean checkUserExist(String user, ObjectMapper objectMapper){
        boolean found = false;

        List<User> users = new ArrayList<>();
        File usersFile = new File(Util.USERS_FILE);
        if (usersFile.exists() && usersFile.length() > 0) {
            try {
                users = objectMapper.readValue(usersFile,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
            } catch (IOException ex) {
                System.err.println("Warning: Could not read users.json, starting fresh: " + ex.getMessage());
                users = new ArrayList<>();
            }
        }
        for (User u : users) {
            if (u.getUsername().equals(user)) {
                found = true;
                break;
            }
        }
        return found;
    }











    default List<User> loadUsers(ObjectMapper objectMapper) {
        List<User> users = new ArrayList<>();
        File usersFile = new File(Util.USERS_FILE);
        if (usersFile.exists() && usersFile.length() > 0) {
            try {
                users = objectMapper.readValue(usersFile,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
            } catch (IOException ex) {
                System.err.println("Warning: Could not read users.json, starting fresh: " + ex.getMessage());
                users = new ArrayList<>();
            }
        }
        return users;
    }
}
