package gl;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;


public interface SceneController {

    default Scene getScene(String sceneName) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(sceneName));
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

}
