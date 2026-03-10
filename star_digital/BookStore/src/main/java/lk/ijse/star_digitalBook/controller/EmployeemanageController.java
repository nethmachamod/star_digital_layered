package lk.ijse.star_digitalBook.controller;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
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
import lk.ijse.star_digitalBook.bo.custom.EmployeeBO;
import lk.ijse.star_digitalBook.bo.BOFactory;
import lk.ijse.star_digitalBook.dto.employeeDTO;

public class EmployeemanageController implements Initializable {

    @FXML
    private TextField idfield;
    @FXML
    private TextField namefield;
    @FXML
    private TextField nicfield;
    @FXML
    private TextField contactfield;
    @FXML
    private ComboBox<String> jobfield;
    @FXML
    private TextField salaryfield;
    @FXML
    private DatePicker datefield;
    @FXML
    private ComboBox<String> statusfield;
    @FXML
    private TableView<employeeDTO> tblemployee;
    @FXML
    private TableColumn<employeeDTO, Integer> colId;
    @FXML
    private TableColumn<employeeDTO, String> colName;
    @FXML
    private TableColumn<employeeDTO, String> colNic;
    @FXML
    private TableColumn<employeeDTO, String> colContact;
    @FXML
    private TableColumn<employeeDTO, String> colJob;
    @FXML
    private TableColumn<employeeDTO, Double> colSalary;
    @FXML
    private TableColumn<employeeDTO, LocalDate> colHireDate;
    @FXML
    private TableColumn<employeeDTO, String> colStatus;
    @FXML
    private ImageView itemphoto;

    private File selectedImageFile;
   EmployeeBO employeeBO =(EmployeeBO) BOFactory.getInstance().getBO(BOFactory.BOType.EMPLOYEE);
    
    private final String EMPLOYEE_NAME_REGEX = "^[A-Za-z ]{2,}$";
    private final String EMPLOYEE_NIC_REGEX = "^[0-9]{9}[VvXx]$|^[0-9]{12}$";
    private final String EMPLOYEE_NUMBER_REGEX = "^07[0-9]{8}$";
    private final String EMPLOYEE_SALARY_REGEX = "^[0-9]+(\\.[0-9]{1,2})?$";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableColumns();
        loademptable();
        loadNextEmployeeId();
        setupComboBoxes();
        setupTableRowClickListener();
    }

    private void setupTableColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colJob.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colHireDate.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void setupComboBoxes() {
        jobfield.getItems().addAll("Cashier", "Manager", "Sales Assistant", "Stock Keeper", "Accountant");
        statusfield.getItems().addAll("Active", "Inactive");
    }

    private void setupTableRowClickListener() {
        tblemployee.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                employeeDTO selected = (employeeDTO) tblemployee.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    populateFields(selected);
                }
            }
        });
    }

    private void populateFields(employeeDTO emp) {
        idfield.setText(String.valueOf(emp.getId()));
        namefield.setText(emp.getFullName());
        nicfield.setText(emp.getNic());
        contactfield.setText(emp.getContact());
        jobfield.setValue(emp.getJobTitle());
        salaryfield.setText(String.valueOf(emp.getSalary()));
        datefield.setValue(emp.getHireDate());
        statusfield.setValue(emp.getStatus());

        String imgPath = emp.getImagePath();
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

    private void loadNextEmployeeId() {
        try {
            int nextId = employeeBO.getNextEmployeeId();
            idfield.setText(String.valueOf(nextId));
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load next employee ID");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void saveEmployee() {
        try {
            String name = namefield.getText().trim();
            String nic = nicfield.getText().trim();
            String contact = contactfield.getText().trim();
            String job = jobfield.getValue();
            String salaryTxt = salaryfield.getText().trim();
            LocalDate date = datefield.getValue();
            String status = statusfield.getValue();

           
            if (!validateInputs(name, nic, contact, job, salaryTxt, date, status)) {
                return;
            }

            if (employeeBO.isNicExists(nic)) {
                showAlert(Alert.AlertType.ERROR, "Duplicate NIC", "An employee with this NIC already exists");
                return;
            }

            double salary = Double.parseDouble(salaryTxt);
            String imagePath = selectedImageFile != null ? selectedImageFile.getAbsolutePath() : null;

            employeeDTO empdto = new employeeDTO(
                    name, nic, contact, job, salary, date, status, imagePath
            );

            boolean ok = employeeBO.saveemp(empdto);

            if (ok) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Employee saved successfully");
                cleanFields();
                loademptable();
                loadNextEmployeeId();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to save employee");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Something went wrong: " + e.getMessage());
        }
    }

    @FXML
    private void updateEmployee() {
        try {
            String idTxt = idfield.getText().trim();
            if (!idTxt.matches("\\d+")) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Invalid Employee ID");
                return;
            }

            int id = Integer.parseInt(idTxt);
            String name = namefield.getText().trim();
            String nic = nicfield.getText().trim();
            String contact = contactfield.getText().trim();
            String job = jobfield.getValue();
            String salaryTxt = salaryfield.getText().trim();
            LocalDate date = datefield.getValue();
            String status = statusfield.getValue();

          
            if (!validateInputs(name, nic, contact, job, salaryTxt, date, status)) {
                return;
            }

            
            if (employeeBO.isNicExistsForOther(nic, id)) {
                showAlert(Alert.AlertType.ERROR, "Duplicate NIC", "Another employee with this NIC already exists");
                return;
            }

            double salary = Double.parseDouble(salaryTxt);
            String imagePath = selectedImageFile != null ? selectedImageFile.getAbsolutePath() : null;

            employeeDTO empdto = new employeeDTO(
                    id, name, nic, contact, job, salary, date, status, imagePath
            );

            boolean result = employeeBO.updateemp(empdto);

            if (result) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Employee updated successfully");
                cleanFields();
                loademptable();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Update failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Something went wrong: " + e.getMessage());
        }
    }

    @FXML
    private void searchEmployee(KeyEvent event) {
        try {
            if (event.getCode() == KeyCode.ENTER) {
                String id = idfield.getText().trim();

                if (!id.matches("\\d+")) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Input", "Invalid Employee ID");
                    return;
                }

                employeeDTO empdto = employeeBO.searchemp(Integer.parseInt(id));

                if (empdto == null) {
                    showAlert(Alert.AlertType.ERROR, "Not Found", "Employee not found");
                    return;
                }

                populateFields(empdto);
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
    private void deleteEmployee() {
        try {
            String id = idfield.getText().trim();
            if (id.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please select an employee to delete");
                return;
            }

            
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Delete Confirmation");
            confirmAlert.setHeaderText("Delete Employee");
            confirmAlert.setContentText("Are you sure you want to delete this employee?");

            if (confirmAlert.showAndWait().get() != ButtonType.OK) {
                return;
            }

            boolean result = employeeBO.deleteemp(id);

            if (result) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Employee deleted successfully");
                cleanFields();
                loademptable();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete employee");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Something went wrong: " + e.getMessage());
        }
    }

    @FXML
    private void clearfields() {
        cleanFields();
    }

    private void cleanFields() {
        
        loadNextEmployeeId();
        namefield.clear();
        nicfield.clear();
        contactfield.clear();
        jobfield.setValue(null);
        salaryfield.clear();
        datefield.setValue(null);
        statusfield.setValue(null);
        selectedImageFile = null;
        itemphoto.setImage(null);
    }

    private void loademptable() {
        try {
            List<employeeDTO> employeelist = employeeBO.getEmployees();
            ObservableList<employeeDTO> oblist = FXCollections.observableArrayList(employeelist);
            tblemployee.setItems(oblist);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load employees: " + e.getMessage());
        }
    }

    
    private boolean validateInputs(String name, String nic, String contact,
                                   String job, String salaryTxt, LocalDate date, String status) {
        if (name.isEmpty() || !name.matches(EMPLOYEE_NAME_REGEX)) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Invalid name. Only letters and spaces allowed, minimum 2 characters");
            return false;
        }

        if (nic.isEmpty() || !nic.matches(EMPLOYEE_NIC_REGEX)) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Invalid NIC. Format: 123456789V or 123456789012");
            return false;
        }

        if (contact.isEmpty() || !contact.matches(EMPLOYEE_NUMBER_REGEX)) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Invalid contact. Format: 07XXXXXXXX");
            return false;
        }

        if (job == null || job.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please select a job title");
            return false;
        }

       
        if (salaryTxt.isEmpty() || !salaryTxt.matches(EMPLOYEE_SALARY_REGEX)) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Invalid salary format. Use numbers with up to 2 decimal places");
            return false;
        }

        try {
            double salary = Double.parseDouble(salaryTxt);
            if (salary < 0) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Salary cannot be negative");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Invalid salary format");
            return false;
        }

        if (date == null) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please select hire date");
            return false;
        }

       
        if (date.isAfter(LocalDate.now())) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Hire date cannot be in the future");
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