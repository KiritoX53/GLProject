module gl {
    
    //Dependencies

    requires transitive javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.fasterxml.jackson.databind; 
    requires com.fasterxml.jackson.core;
    
    //Open Packages for Reflection (FXML/Scene Builder)
    // JavaFX uses reflection to access the controller class and its annotated methods (like @FXML). 
    opens gl to javafx.fxml;
    opens gl.data to com.fasterxml.jackson.databind;
    
 
    // Exporting the main package containing the main class so the Java runtime can find and launch it.
 
    exports gl;
}