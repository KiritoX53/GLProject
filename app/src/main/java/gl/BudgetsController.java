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
        // Maybe someday you won't be a useless, gaping hole
    }
    











    @FXML
    private void createBudget() {
        // Create custom dialog(Dialog = POPUP basically)
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Create New Budget");
        dialog.setHeaderText("Enter Budget Details");
        

        ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);
        
        // Make the fields
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
        
        //Enable/disable create button based on validation(This is some cool stuff right here)
        Button createBtn = (Button) dialog.getDialogPane().lookupButton(createButtonType);
        createBtn.setDisable(true);
        
        //Validation listeners for the cool stuff
        amountField.textProperty().addListener((observable, oldValue, newValue) -> {
            createBtn.setDisable(!isValidInput(nameField.getText(), amountField.getText()));
        });
        
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            createBtn.setDisable(!isValidInput(nameField.getText(), amountField.getText()));
        });
        
        //Show dialog and process result
        dialog.showAndWait().ifPresent(response -> {
            if (response == createButtonType) {
                String name = nameField.getText().trim();
                String description = descriptionArea.getText().trim();
                float amount = Float.parseFloat(amountField.getText());
                
 
                Budget newBudget = new Budget(name, amount, description);
                Util.currentUser.getData().getBudgets().add(newBudget);
                saveCurrentUser(new ObjectMapper());
                // Add budget button to budgetsB0x(Yes the typo is official)
                addBudgetButton(newBudget);
            }
        });
    }
    










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
        Button budgetButton = new Button(budget.getName());
        budgetButton.setMinHeight(96.0);
        budgetButton.setMinWidth(183.0);
        budgetButton.setPrefHeight(96.0);
        budgetButton.setPrefWidth(183.0);
        budgetButton.setMaxHeight(96.0);
        budgetButton.setMaxWidth(183.0);
        budgetButton.setStyle("-fx-font-weight: bold;");
        budgetButton.setFont(new Font(15.0));

        budgetButton.setOnAction(event -> showBudgetDetails(budget));
        
        // Add to budgetsB0x(budgetsB0x)
        budgetsB0x.getChildren().add(budgetButton);
    }
    










    private void showBudgetDetails(Budget budget) {
        budgetName.setText(budget.getName());
        BudgetDescription.setText(budget.getDescription());
        budgetBalance.setText("Current budget balance: $" + String.format("%.2f", budget.getAmount()));
    }


















    @FXML
    private void deleteBudget() {
    // Get the currently displayed budget name
    String currentBudgetName = budgetName.getText();
    
    // Check if a budget is actually selected (not the default text)
    if (currentBudgetName.equals("Budget Name")) {
        showAlert(Alert.AlertType.ERROR, "No Budget Selected", "Please select a budget to delete.");

        return;
    }
    
    // Find the budget in the user's budget list
    Budget budgetToDelete = null;
    for (Budget budget : Util.currentUser.getData().getBudgets()) {
        if (budget.getName().equals(currentBudgetName)) {
            budgetToDelete = budget;
            break;
        }
    }
    
    if (budgetToDelete == null) {
        showAlert(Alert.AlertType.ERROR, "Budget not found.","COULD NOT find the budget to delete.");
        return;
    }
    
    // Confirm deletion
    Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
    confirmAlert.setTitle("Confirm Deletion");
    confirmAlert.setHeaderText("Delete Budget: " + currentBudgetName);
    confirmAlert.setContentText("Are you sure you want to delete this budget? This action cannot be undone.");
    
    confirmAlert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.OK) {
            System.err.println("yay we can delete now");
        }
        else{
            return;

        }
    });

    // Remove from data
    Util.currentUser.getData().removeBudget(budgetToDelete);
            
    // Remove the corresponding button from budgetsB0x
    budgetsB0x.getChildren().removeIf(node -> {
        if (node instanceof Button) {
                    return ((Button) node).getText().equals(currentBudgetName);
                }
                return false;
            });
            // Reset the display labels to default
            budgetName.setText("Budget Name");
            BudgetDescription.setText("Here goes budget description!!");
            budgetBalance.setText("Current budget balance: Waiting~");
            // Clear transactions box if it has content
            transactionsBox.getChildren().clear();


            saveCurrentUser(new ObjectMapper());
}





@FXML
private void alterBudget() {
    // Get the currently displayed budget name
    String currentBudgetName = budgetName.getText();
    
    // Check if a budget is actually selected (not the default text)
    if (currentBudgetName.equals("Budget Name")) {
        showAlert(Alert.AlertType.ERROR, "No Budget Selected", "Please select a budget to alter.");
        return;
    }
    
    // Find the budget in the user's budget list
    Budget budgetToAlter = null;
    for (Budget budget : Util.currentUser.getData().getBudgets()) {
        if (budget.getName().equals(currentBudgetName)) {
            budgetToAlter = budget;
            break;
        }
    }
    
    if (budgetToAlter == null) {
        showAlert(Alert.AlertType.ERROR, "Budget not found.", "COULD NOT find the budget to alter.");
        return;
    }

    
    // To use in dialog
    final Budget finalBudgetToAlter = budgetToAlter;
    
    // Create custom dialog
    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.setTitle("Alter Budget");
    dialog.setHeaderText("Modify Budget Details (leave fields empty to keep current values)");
    
    ButtonType alterButtonType = new ButtonType("Alter", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(alterButtonType, ButtonType.CANCEL);
    
    // Make the fields
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));
    
    TextField nameField = new TextField();
    nameField.setPromptText("New name (leave empty to keep: " + finalBudgetToAlter.getName() + ")");
    
    TextArea descriptionArea = new TextArea();
    descriptionArea.setPromptText("New description (leave empty to keep current)");
    descriptionArea.setPrefRowCount(3);
    descriptionArea.setWrapText(true);
    
    TextField amountField = new TextField();
    amountField.setPromptText("Amount to add/subtract (e.g., +100 or -50)");
    
    Label currentAmountLabel = new Label("Current balance: $" + String.format("%.2f", finalBudgetToAlter.getAmount()));
    
    grid.add(new Label("Name:"), 0, 0);
    grid.add(nameField, 1, 0);
    grid.add(new Label("Description:"), 0, 1);
    grid.add(descriptionArea, 1, 1);
    grid.add(new Label("Adjust Amount:"), 0, 2);
    grid.add(amountField, 1, 2);
    grid.add(currentAmountLabel, 1, 3);
    
    dialog.getDialogPane().setContent(grid);
    
    // Enable/disable alter button based on validation(Cool stuff AGAIN!)
    Button alterBtn = (Button) dialog.getDialogPane().lookupButton(alterButtonType);
    
 
    amountField.textProperty().addListener((observable, oldValue, newValue) -> {
        alterBtn.setDisable(!isValidAlterInput(newValue, finalBudgetToAlter.getAmount()));
    });
    
    // Show dialog and process result
    dialog.showAndWait().ifPresent(response -> {
        if (response == alterButtonType) {
            String newName = nameField.getText().trim();
            String newDescription = descriptionArea.getText().trim();
            String amountText = amountField.getText().trim();
            
            // Update name if provided
            if (!newName.isEmpty()) {
                // Update the button text in budgetsB0x
                budgetsB0x.getChildren().forEach(node -> {
                    if (node instanceof Button) {
                        Button btn = (Button) node;
                        if (btn.getText().equals(finalBudgetToAlter.getName())) {
                            btn.setText(newName);
                        }
                    }
                });
                finalBudgetToAlter.setName(newName);
            }
            
            // Update description if provided
            if (!newDescription.isEmpty()) {
                finalBudgetToAlter.setDescription(newDescription);
            }
            
            // Update amount if provided
            if (!amountText.isEmpty()) {
                float adjustment = Float.parseFloat(amountText);
                float newAmount = finalBudgetToAlter.getAmount() + adjustment;
                finalBudgetToAlter.setAmount(newAmount);
            }
            
            // Refresh the display
            showBudgetDetails(finalBudgetToAlter);
            
            // Save changes
            saveCurrentUser(new ObjectMapper());
        }
    });
}

private boolean isValidAlterInput(String amountText, float currentAmount) {
    // Empty amount is valid (means no change)
    if (amountText == null || amountText.trim().isEmpty()) {
        return true;
    }
    
    try {
        float adjustment = Float.parseFloat(amountText);
        float newAmount = currentAmount + adjustment;
        // New amount must be non-negative
        return newAmount >= 0;
    } catch (NumberFormatException e) {
        return false;
    }
}



}