package lk.ijse.star_digitalBook.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;  // FIXED: Added missing import
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.star_digitalBook.controller.models.InventoryModel;
import lk.ijse.star_digitalBook.dto.inventoryDTO;

public class InventorymanageController implements Initializable {

    @FXML
    private TextField inventoryIdField;
    @FXML
    private TextField inventoryNameField;
    @FXML
    private TextField qtyOnHandField;
    @FXML
    private TextField inventoryTypeField;
    @FXML
    private DatePicker stockInDatePicker;

    @FXML
    private TableView<inventoryDTO> tableView;
    @FXML
    private TableColumn<inventoryDTO, String> colInventoryId;
    @FXML
    private TableColumn<inventoryDTO, String> colInventoryName;
    @FXML
    private TableColumn<inventoryDTO, String> colQtyOnHand;
    @FXML
    private TableColumn<inventoryDTO, String> colInventoryType;
    @FXML
    private TableColumn<inventoryDTO, String> colStockInDate;

    private final InventoryModel model = new InventoryModel();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colInventoryId.setCellValueFactory(new PropertyValueFactory<>("inventoryId"));
        colInventoryName.setCellValueFactory(new PropertyValueFactory<>("inventoryName"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colInventoryType.setCellValueFactory(new PropertyValueFactory<>("inventoryType"));
        colStockInDate.setCellValueFactory(new PropertyValueFactory<>("stockInDate"));

        loadTable();
        try {
            inventoryIdField.setText(InventoryModel.getNextInventoryId());
        } catch (Exception e) {
            e.printStackTrace();
            alert("Failed to load next inventory ID", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void saveInventory(ActionEvent event) {
        try {
            // Validate inputs
            if (!validateInputs()) {
                return;
            }

            inventoryDTO dto = new inventoryDTO(
                    inventoryIdField.getText().trim(),
                    inventoryNameField.getText().trim(),
                    qtyOnHandField.getText().trim(),
                    inventoryTypeField.getText().trim(),
                    stockInDatePicker.getValue()
            );

            if (InventoryModel.saveInventory(dto)) {
                alert("Inventory saved successfully", Alert.AlertType.INFORMATION);
                clearFields();
                loadTable();
                inventoryIdField.setText(InventoryModel.getNextInventoryId());
            } else {
                alert("Failed to save inventory", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            alert("Save failed: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void searchInventory(ActionEvent event) {
        String id = inventoryIdField.getText().trim();

        if (id.isEmpty()) {
            alert("Please enter inventory ID", Alert.AlertType.WARNING);
            return;
        }

        try {
            inventoryDTO dto = InventoryModel.searchInventory(id);

            if (dto != null) {
                inventoryNameField.setText(dto.getInventoryName());
                qtyOnHandField.setText(String.valueOf(dto.getQtyOnHand()));
                inventoryTypeField.setText(dto.getInventoryType());
                stockInDatePicker.setValue(dto.getStockInDate());
            } else {
                alert("Inventory not found", Alert.AlertType.WARNING);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            alert("Search failed: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void updateInventory(ActionEvent event) {
        try {
            if (inventoryIdField.getText().trim().isEmpty()) {
                alert("Please select an inventory item to update", Alert.AlertType.WARNING);
                return;
            }

            if (!validateInputs()) {
                return;
            }

            inventoryDTO dto = new inventoryDTO(
                    inventoryIdField.getText().trim(),
                    inventoryNameField.getText().trim(),
                    qtyOnHandField.getText().trim(),
                    inventoryTypeField.getText().trim(),
                    stockInDatePicker.getValue()
            );

            if (InventoryModel.updateInventory(dto)) {
                alert("Inventory updated successfully", Alert.AlertType.INFORMATION);
                clearFields();
                loadTable();
                inventoryIdField.setText(InventoryModel.getNextInventoryId());
            } else {
                alert("Failed to update inventory", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            alert("Update failed: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void deleteInventory(ActionEvent event) {
        String id = inventoryIdField.getText().trim();

        if (id.isEmpty()) {
            alert("Please select an inventory item to delete", Alert.AlertType.WARNING);
            return;
        }

        // Confirmation dialog
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Confirmation");
        confirmAlert.setHeaderText("Delete Inventory Item");
        confirmAlert.setContentText("Are you sure you want to delete this inventory item?");

        if (confirmAlert.showAndWait().get() != ButtonType.OK) {
            return;
        }

        try {
            if (InventoryModel.deleteInventory(id)) {
                alert("Inventory deleted successfully", Alert.AlertType.INFORMATION);
                clearFields();
                loadTable();
                inventoryIdField.setText(InventoryModel.getNextInventoryId());
            } else {
                alert("Failed to delete inventory", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            alert("Delete failed: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void resetFields(ActionEvent event) {
        clearFields();
        try {
            inventoryIdField.setText(InventoryModel.getNextInventoryId());
        } catch (Exception e) {
            e.printStackTrace();
            alert("Failed to reset: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // FIXED: Added missing helper methods

    /**
     * Validates all input fields
     * @return true if all inputs are valid
     */
    private boolean validateInputs() {
        if (inventoryNameField.getText().trim().isEmpty()) {
            alert("Inventory name cannot be empty", Alert.AlertType.WARNING);
            return false;
        }

        String qty = qtyOnHandField.getText().trim();
        if (qty.isEmpty()) {
            alert("Quantity cannot be empty", Alert.AlertType.WARNING);
            return false;
        }

        try {
            int qtyValue = Integer.parseInt(qty);
            if (qtyValue < 0) {
                alert("Quantity cannot be negative", Alert.AlertType.WARNING);
                return false;
            }
        } catch (NumberFormatException e) {
            alert("Quantity must be a valid number", Alert.AlertType.WARNING);
            return false;
        }

        if (inventoryTypeField.getText().trim().isEmpty()) {
            alert("Inventory type cannot be empty", Alert.AlertType.WARNING);
            return false;
        }

        if (stockInDatePicker.getValue() == null) {
            alert("Please select stock-in date", Alert.AlertType.WARNING);
            return false;
        }

        return true;
    }

    /**
     * Clears all input fields
     */
    private void clearFields() {
        inventoryIdField.clear();
        inventoryNameField.clear();
        qtyOnHandField.clear();
        inventoryTypeField.clear();
        stockInDatePicker.setValue(null);
    }

    /**
     * Loads all inventory data into the table
     */
    private void loadTable() {
        try {
            List<inventoryDTO> inventoryList = model.getAllInventory();
            ObservableList<inventoryDTO> obList = FXCollections.observableArrayList(inventoryList);
            tableView.setItems(obList);
        } catch (Exception e) {
            e.printStackTrace();
            alert("Failed to load inventory data: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Displays an alert dialog
     * @param message The message to display
     * @param type The type of alert
     */
    private void alert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }
}