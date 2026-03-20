package lk.ijse.star_digitalBook.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.star_digitalBook.App;
import lk.ijse.star_digitalBook.bo.BOFactory;
import lk.ijse.star_digitalBook.bo.custom.InventoryBO;
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

    @FXML
    private Button viewSuppliersBtn;


    InventoryBO inventoryBO = (InventoryBO) BOFactory.getInstance().getBO(BOFactory.BOType.INVENTORY);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTableColumns();
        loadTable();
        setupNextInventoryId();
        setupTableRowSelection();
    }

    private void setupTableColumns() {
        colInventoryId.setCellValueFactory(new PropertyValueFactory<>("inventoryId"));
        colInventoryName.setCellValueFactory(new PropertyValueFactory<>("inventoryName"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colInventoryType.setCellValueFactory(new PropertyValueFactory<>("inventoryType"));
        colStockInDate.setCellValueFactory(new PropertyValueFactory<>("stockInDate"));
    }

    private void setupNextInventoryId() {


        try {

            int nextId = inventoryBO.getnexteinventoryid();

            String formattedId = String.format("INV%03d", nextId);

            inventoryIdField.setText(formattedId);

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading next ID").show();
        }
    }

    private void setupTableRowSelection() {
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                populateFieldsFromRow(newVal);
            }
        });
    }

    private void populateFieldsFromRow(inventoryDTO dto) {
        inventoryIdField.setText(dto.getInventoryId());
        inventoryNameField.setText(dto.getInventoryName());
        qtyOnHandField.setText(dto.getQtyOnHand());
        inventoryTypeField.setText(dto.getInventoryType());
        stockInDatePicker.setValue(dto.getStockInDate());
    }

    @FXML
    private void saveInventory() {
        if (!validateInputs()) {
            return;
        }

        try {
            inventoryDTO dto = new inventoryDTO(
                    inventoryIdField.getText().trim(),
                    inventoryNameField.getText().trim(),
                    qtyOnHandField.getText().trim(),
                    inventoryTypeField.getText().trim(),
                    stockInDatePicker.getValue()
            );

            if (inventoryBO.saveInventory(dto)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved successfully").show();
                clearFields();
                loadTable();
                setupNextInventoryId();
            } else {
                new Alert(Alert.AlertType.ERROR, "Save failed").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Save failed").show();
        }
    }

    @FXML
    private void searchInventory() {
        String id = inventoryIdField.getText().trim();

        if (id.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter inventory ID").show();
            return;
        }

        try {
            inventoryDTO dto = inventoryBO.searchInventory(id);

            if (dto != null) {
                inventoryNameField.setText(dto.getInventoryName());
                qtyOnHandField.setText(dto.getQtyOnHand());
                inventoryTypeField.setText(dto.getInventoryType());
                stockInDatePicker.setValue(dto.getStockInDate());
            } else {
                new Alert(Alert.AlertType.ERROR, "Inventory not found").show();
                clearFields();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Search failed").show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void updateInventory() {
        if (!validateInputs()) {
            return;
        }

        try {
            inventoryDTO dto = new inventoryDTO(
                    inventoryIdField.getText().trim(),
                    inventoryNameField.getText().trim(),
                    qtyOnHandField.getText().trim(),
                    inventoryTypeField.getText().trim(),
                    stockInDatePicker.getValue()
            );

            if (inventoryBO.updateInventory(dto)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated successfully").show();
                clearFields();
                loadTable();
                setupNextInventoryId();
            } else {
                new Alert(Alert.AlertType.ERROR, "Update failed").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Update failed").show();
        }
    }

    @FXML
    private void deleteInventory() {
        String id = inventoryIdField.getText().trim();

        if (id.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select an item to delete").show();
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setContentText("Are you sure you want to delete this inventory item?");

        if (confirmAlert.showAndWait().get() == ButtonType.OK) {
            try {
                if (inventoryBO.deleteInventory(id)) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Deleted successfully").show();
                    clearFields();
                    loadTable();
                    setupNextInventoryId();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Delete failed").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Delete failed").show();
            }
        }
    }

    @FXML
    private void resetFields() {
        clearFields();
        try {
            inventoryIdField.setText(String.valueOf(inventoryBO.getnexteinventoryid()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTable() {
        try {
            List<inventoryDTO> list = inventoryBO.getAllInventory();
            ObservableList<inventoryDTO> obList = FXCollections.observableArrayList(list);
            tableView.setItems(obList);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load table").show();
        }
    }

    private void clearFields() {
        inventoryIdField.clear();
        inventoryNameField.clear();
        qtyOnHandField.clear();
        inventoryTypeField.clear();
        stockInDatePicker.setValue(null);
        tableView.getSelectionModel().clearSelection();
    }

    private boolean validateInputs() {
        if (inventoryIdField.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter inventory ID").show();
            return false;
        }
        if (inventoryNameField.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter inventory name").show();
            return false;
        }
        if (qtyOnHandField.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter quantity on hand").show();
            return false;
        }
        if (inventoryTypeField.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter inventory type").show();
            return false;
        }
        if (stockInDatePicker.getValue() == null) {
            new Alert(Alert.AlertType.ERROR, "Please select stock in date").show();
            return false;
        }
        return true;
    }


    @FXML
    private void clicksupp() throws IOException {

        Parent supplierFXML = App.loadFXML("supplier");

        AnchorPane mainContent = (AnchorPane)
                viewSuppliersBtn.getScene()
                        .getRoot()
                        .lookup("#maincontent");

        mainContent.getChildren().setAll(supplierFXML);
    }


}