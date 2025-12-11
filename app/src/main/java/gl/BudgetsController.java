package gl;


import com.fasterxml.jackson.databind.ObjectMapper;

import gl.data.Budget;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class BudgetsController implements SceneController {
    
    
    @FXML
    private HBox budgetsB0x;
    
    @FXML
    private Label budgetName;
    
    @FXML
    private Label BudgetDescription;
    
    @FXML
    private Label budgetBalance;
    
    @FXML
    private HBox transactionsBox;
    
    @FXML
    private Button CreatButton;
    
    @FXML
    private Button alterBudget;
    
    @FXML
    private Button DeleteButton;
    
    @FXML
    public void initialize() {
        // Initialization logic goes here
    }
    
    @FXML
    private void createBudget() {
        // Create popup
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Create New Budget");
        dialog.setHeaderText("Enter Budget Details");
        
        // Set button types
        ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);
        
        // Create form fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField nameField = new TextField();
        nameField.setPromptText("Budget Name");
        
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Budget Description");
        descriptionArea.setPrefRowCount(3);
        descriptionArea.setWrapText(true);
        
        TextField amountField = new TextField();
        amountField.setPromptText("Amount (must be > 0)");
        
        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(descriptionArea, 1, 1);
        grid.add(new Label("Amount:"), 0, 2);
        grid.add(amountField, 1, 2);
        
        dialog.getDialogPane().setContent(grid);
        
        // Enable/disable create button based on validation
        Button createBtn = (Button) dialog.getDialogPane().lookupButton(createButtonType);
        createBtn.setDisable(true);
        
        // Validation listener
        amountField.textProperty().addListener((observable, oldValue, newValue) -> {
            createBtn.setDisable(!isValidInput(nameField.getText(), amountField.getText()));
        });
        
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            createBtn.setDisable(!isValidInput(nameField.getText(), amountField.getText()));
        });
        
        // Show popup and process result
        dialog.showAndWait().ifPresent(response -> {
            if (response == createButtonType) {
                String name = nameField.getText().trim();
                String description = descriptionArea.getText().trim();
                float amount = Float.parseFloat(amountField.getText());
                

                Budget newBudget = new Budget(name, amount, description);
                
   
                Util.currentUser.getData().getBudgets().add(newBudget);
                saveCurrentUser(new ObjectMapper());
                // Add budget button to budgetsB0x(B0x)
                addBudgetButton(newBudget);
            }
        });
    }
    
    //Self evident
    private boolean isValidInput(String name, String amountText) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        
        try {
            float amount = Float.parseFloat(amountText);
            return amount > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private void addBudgetButton(Budget budget) {
        //Button setup, nothing to see here
        Button budgetButton = new Button(budget.getName());
        budgetButton.setMinHeight(96.0);
        budgetButton.setMinWidth(183.0);
        budgetButton.setPrefHeight(96.0);
        budgetButton.setPrefWidth(183.0); //I said nothing to see here
        budgetButton.setMaxHeight(96.0);
        budgetButton.setMaxWidth(183.0);
        budgetButton.setStyle("-fx-font-weight: bold;");
        budgetButton.setFont(new Font(15.0));
        
        // Set action(On click) to show budget details
        budgetButton.setOnAction(event -> showBudgetDetails(budget));
        
        // Add to budgetsB0x(Yes the typo became official)
        budgetsB0x.getChildren().add(budgetButton);
    }
    


    private void showBudgetDetails(Budget budget) {
        budgetName.setText(budget.getName());
        BudgetDescription.setText(budget.getDescription());
        budgetBalance.setText("Current budget balance: $" + String.format("%.2f", budget.getAmount()));
    }
    
    @FXML
    private void deleteBudget() {
        // TODO: Implement delete budget logic
    }
}