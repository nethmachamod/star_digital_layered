package lk.ijse.star_digitalBook.controller;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import lk.ijse.star_digitalBook.bo.BOFactory;
import lk.ijse.star_digitalBook.bo.custom.InventoryBO;
import lk.ijse.star_digitalBook.bo.custom.SupplierBO;
import lk.ijse.star_digitalBook.dto.supplierDTO;
import lk.ijse.star_digitalBook.entity.supplierentity;

public class SupplierController implements Initializable {
    @FXML
    private TextField idfield;
    @FXML
    private TextField namefield;
    @FXML
    private TextField contactfield;
    @FXML
    private DatePicker datefield;
    @FXML
    private TextField agencyfield;
    @FXML
    private TextField companyfield;
    @FXML
    private ComboBox<String> statusfield;
    @FXML
    private ImageView itemphoto;
    private File selectedImageFile;

    @FXML
    private TableView<supplierDTO> tblsup;
    @FXML
    private TableColumn<supplierDTO, Integer> colid;
    @FXML
    private TableColumn<supplierDTO, String> colname;
    @FXML
    private TableColumn<supplierDTO, String> colnumber;
    @FXML
    private TableColumn<supplierDTO, LocalDate> coldate;
    @FXML
    private TableColumn<supplierDTO, String> colagency;
    @FXML
    private TableColumn<supplierDTO, String> colcompany;
    @FXML
    private TableColumn<supplierDTO, String> colstatus;

    SupplierBO supplierBO=(SupplierBO) BOFactory.getInstance().getBO(BOFactory.BOType.SUPPLIER);

    
    private final String CONTACT_REGEX = "^07[0-9]{8}$";
    private final String NAME_REGEX = "^[A-Za-z ]{2,}$";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTableColumns();
        loadsuptable();
        loadNextSupplierId();
        setupComboBox();
        setupTableRowClickListener();
    }

    private void setupTableColumns() {
        colid.setCellValueFactory(new PropertyValueFactory<>("supid"));
        colname.setCellValueFactory(new PropertyValueFactory<>("supname"));
        colnumber.setCellValueFactory(new PropertyValueFactory<>("supcontact"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("supdate"));
        colagency.setCellValueFactory(new PropertyValueFactory<>("supagency"));
        colcompany.setCellValueFactory(new PropertyValueFactory<>("companyname"));
        colstatus.setCellValueFactory(new PropertyValueFactory<>("supstatus"));
    }

    private void setupComboBox() {
        statusfield.getItems().addAll("Active", "Inactive");
    }

    private void setupTableRowClickListener() {
        tblsup.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                supplierDTO selected = tblsup.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    supplierentity entity = new supplierentity(
                            selected.getSupid(),
                            selected.getSupname(),
                            selected.getSupcontact(),
                            selected.getSupdate(),
                            selected.getSupagency(),
                            selected.getCompanyname(),
                            selected.getSupstatus(),
                            selected.getImagepath());
                    populateFields(entity);
                }
            }
        });
    }

    private void populateFields(supplierentity sup) {
        idfield.setText(String.valueOf(sup.getSupid()));
        namefield.setText(sup.getSupname());
        contactfield.setText(sup.getSupcontact());
        datefield.setValue(sup.getSupdate());
        agencyfield.setText(sup.getSupagency());
        companyfield.setText(sup.getCompanyname());
        statusfield.setValue(sup.getSupstatus());

        String imgPath = sup.getImagepath();
        if (imgPath != null && !imgPath.isEmpty()) {
            File file = new File(imgPath);
            if (file.exists()) {
                itemphoto.setImage(new Image(file.toURI().toString()));
                selectedImageFile = file;
            } else {
                itemphoto.setImage(null);
                selectedImageFile = null;
            }
        } else {
            itemphoto.setImage(null);
            selectedImageFile = null;
        }
    }

    private void loadNextSupplierId() {
        try {
            int nextId = supplierBO.getNextSuppllierId();
            idfield.setText(String.valueOf(nextId));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load next supplier ID");
        }
    }

    @FXML
    private void savesupplier() {
        try {
            String name = namefield.getText().trim();
            String number = contactfield.getText().trim();
            LocalDate date = datefield.getValue();
            String agency = agencyfield.getText().trim();
            String company = companyfield.getText().trim();
            String status = statusfield.getValue();

            
            if (!validateInputs(name, number, date, agency, company, status)) {
                return;
            }

            String imagePath = selectedImageFile != null ? selectedImageFile.getAbsolutePath() : null;

            supplierDTO supdto = new supplierDTO(
                    name, number, date, agency, company, status, imagePath
            );

            boolean ok = supplierBO.savesup(supdto);

            if (ok) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Supplier saved successfully");
                clearfields();
                loadsuptable();
                loadNextSupplierId();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to save supplier");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Something went wrong: " + e.getMessage());
        }
    }

    @FXML
    private void updatesupplier() {
        try {
            String idTxt = idfield.getText().trim();
            if (!idTxt.matches("\\d+")) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Invalid Supplier ID");
                return;
            }

            int id = Integer.parseInt(idTxt);
            String name = namefield.getText().trim();
            String number = contactfield.getText().trim();
            LocalDate date = datefield.getValue();
            String agency = agencyfield.getText().trim();
            String company = companyfield.getText().trim();
            String status = statusfield.getValue();

            
            if (!validateInputs(name, number, date, agency, company, status)) {
                return;
            }

            String imagePath = selectedImageFile != null ? selectedImageFile.getAbsolutePath() : null;

            supplierDTO supdto = new supplierDTO(
                    id, name, number, date, agency, company, status, imagePath
            );

            boolean result = supplierBO.updatesup(supdto);

            if (result) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Supplier updated successfully");
                clearfields();
                loadsuptable();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Update failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Something went wrong: " + e.getMessage());
        }
    }

    @FXML
    private void searchsupplier(KeyEvent event) {
        try {
            if (event.getCode() == KeyCode.ENTER) {
                String id = idfield.getText().trim();

                if (!id.matches("\\d+")) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Input", "Invalid Supplier ID");
                    return;
                }

                supplierentity supdto = supplierBO.searchsup(Integer.parseInt(id));

                if (supdto == null) {
                    showAlert(Alert.AlertType.ERROR, "Not Found", "Supplier not found");
                    return;
                }

                populateFields(supdto);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Something went wrong: " + e.getMessage());
        }
    }

    @FXML
    private void deletesupplier() {
        try {
            String id = idfield.getText().trim();
            if (id.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please select a supplier to delete");
                return;
            }

            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Delete Confirmation");
            confirmAlert.setHeaderText("Delete Supplier");
            confirmAlert.setContentText("Are you sure you want to delete this supplier?");

            if (confirmAlert.showAndWait().get() != ButtonType.OK) {
                return;
            }

            boolean result = supplierBO.deletesup(id);

            if (result) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Supplier deleted successfully");
                clearfields();
                loadsuptable();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete supplier");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Something went wrong: " + e.getMessage());
        }
    }

    @FXML
    private void importPhoto() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select Image");
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File file = fc.showOpenDialog(null);
        if (file != null) {
            selectedImageFile = file;
            itemphoto.setImage(new Image(file.toURI().toString()));
        }
    }

    @FXML
    private void clearfields() {
        cleanfields();
    }

    private void cleanfields() {
        
        loadNextSupplierId();
        namefield.clear();
        contactfield.clear();
        datefield.setValue(null);
        agencyfield.clear();
        companyfield.clear();
        statusfield.setValue(null);
        if (itemphoto != null) {
            itemphoto.setImage(null);
        }
        selectedImageFile = null;
    }

    private void loadsuptable() {
        try {
            ArrayList<supplierDTO> supplierlist = supplierBO.getsuppliers();
            ObservableList<supplierDTO> oblist = FXCollections.observableArrayList(supplierlist);
            tblsup.setItems(oblist);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load suppliers: " + e.getMessage());
        }
    }

    
    private boolean validateInputs(String name, String number, LocalDate date,
                                   String agency, String company, String status) {
        if (name.isEmpty() || !name.matches(NAME_REGEX)) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input",
                    "Invalid name. Only letters and spaces allowed, minimum 2 characters");
            return false;
        }

        if (number.isEmpty() || !number.matches(CONTACT_REGEX)) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input",
                    "Invalid contact number. Format: 07XXXXXXXX");
            return false;
        }

        if (date == null) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please select a date");
            return false;
        }

        if (date.isAfter(LocalDate.now())) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Date cannot be in the future");
            return false;
        }

        if (agency.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Agency name cannot be empty");
            return false;
        }

        if (company.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Company name cannot be empty");
            return false;
        }

        if (status == null || status.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please select status");
            return false;
        }

        return true;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}