package lk.ijse.star_digitalBook.controller;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
import lk.ijse.star_digitalBook.bo.custom.ItemBO;
import lk.ijse.star_digitalBook.bo.custom.ItemBO;
import lk.ijse.star_digitalBook.dto.ItemDTO;
import lk.ijse.star_digitalBook.util.MenuRefresher;

public class ItemmanageController implements Initializable {

    @FXML
    private TextField itemid;
    @FXML
    private TextField itemname;
    @FXML
    private TextField itemcategory;
    @FXML
    private TextField itemprice;
    @FXML
    private ImageView itemphoto;
    @FXML
    private TableView<ItemDTO> tblitem;
    @FXML
    private TableColumn<ItemDTO, Integer> idcol;
    @FXML
    private TableColumn<ItemDTO, String> namecol;
    @FXML
    private TableColumn<ItemDTO, String> categorycol;
    @FXML
    private TableColumn<ItemDTO, Double> pricecol;

    private File selectedImageFile;
    ItemBO itemBO=(ItemBO) BOFactory.getInstance().getBO(BOFactory.BOType.ITEM);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTableColumns();
        loaditemtable();
        loadNextItemId();
        setupTableRowClickListener();
    }

    private void setupTableColumns() {
        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        categorycol.setCellValueFactory(new PropertyValueFactory<>("category"));
        pricecol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void setupTableRowClickListener() {
        tblitem.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                ItemDTO selected = tblitem.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    populateFields(selected);
                }
            }
        });
    }

    private void populateFields(ItemDTO item) {
        itemid.setText(String.valueOf(item.getId()));
        itemname.setText(item.getName());
        itemcategory.setText(item.getCategory());
        itemprice.setText(String.valueOf(item.getPrice()));

        String imagePath = item.getImagePath();
        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(imagePath);
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

    private void loadNextItemId() {
        try {
            int nextId = itemBO.getNextItemId();
            itemid.setText(String.valueOf(nextId));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load next Item ID");
        }
    }

    @FXML
    public void saveItem() {
        String name = itemname.getText().trim();
        String category = itemcategory.getText().trim();
        String priceText = itemprice.getText().trim();

        
        if (!validateInputs(name, category, priceText)) {
            return;
        }

        try {
            
            if (itemBO.isItemNameExists(name)) {
                showAlert(Alert.AlertType.ERROR, "Duplicate Item",
                        "An item with this name already exists");
                return;
            }

            Double price = Double.parseDouble(priceText);
            String imagePath = (selectedImageFile != null) ? selectedImageFile.getAbsolutePath() : null;

            ItemDTO bookDto = new ItemDTO(name, category, price, imagePath);
            boolean result = itemBO.saveItem(bookDto);

            if (result) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Item added successfully");
                cleanFields();
                loaditemtable();
                loadNextItemId();

                
                if (MenuRefresher.refreshMenu != null) {
                    MenuRefresher.refreshMenu.run();
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to save item");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Something went wrong: " + e.getMessage());
        }
    }

    @FXML
    public void updateItem() {
        String idText = itemid.getText().trim();
        String name = itemname.getText().trim();
        String category = itemcategory.getText().trim();
        String priceText = itemprice.getText().trim();

        if (idText.isEmpty() || !idText.matches("\\d+")) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Invalid Item ID");
            return;
        }

        
        if (!validateInputs(name, category, priceText)) {
            return;
        }

        try {
            int id = Integer.parseInt(idText);

            
            if (itemBO.isItemNameExistsForOther(name, id)) {
                showAlert(Alert.AlertType.ERROR, "Duplicate Item",
                        "Another item with this name already exists");
                return;
            }

            Double price = Double.parseDouble(priceText);
            String imagePath = (selectedImageFile != null) ? selectedImageFile.getAbsolutePath() : null;

            ItemDTO itemdto = new ItemDTO(id, name, category, price, imagePath);
            boolean result = itemBO.updateItem(itemdto);

            if (result) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Item updated successfully");
                cleanFields();
                loaditemtable();
                loadNextItemId();

                
                if (MenuRefresher.refreshMenu != null) {
                    MenuRefresher.refreshMenu.run();
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update item");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Something went wrong: " + e.getMessage());
        }
    }

    @FXML
    public void deleteItem() {
        String id = itemid.getText().trim();

        if (id.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please select an item to delete");
            return;
        }

        
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Confirmation");
        confirmAlert.setHeaderText("Delete Item");
        confirmAlert.setContentText("Are you sure you want to delete this item?");

        if (confirmAlert.showAndWait().get() != ButtonType.OK) {
            return;
        }

        try {
            boolean result = itemBO.deleteItem(id);

            if (result) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Item deleted successfully");
                cleanFields();
                loaditemtable();
                loadNextItemId();

                
                if (MenuRefresher.refreshMenu != null) {
                    MenuRefresher.refreshMenu.run();
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete item");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Something went wrong: " + e.getMessage());
        }
    }

    @FXML
    public void uploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Item Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            selectedImageFile = file;
            itemphoto.setImage(new Image(file.toURI().toString()));
        }
    }

    @FXML
    public void searchItem(KeyEvent event) {
        try {
            if (event.getCode() == KeyCode.ENTER) {
                String id = itemid.getText().trim();

                if (!id.matches("[0-9]+")) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Input", "Invalid Item ID");
                    return;
                }

                ItemDTO ItemDTO = itemBO.searchItem(Integer.parseInt(id));

                if (ItemDTO != null) {
                    populateFields(ItemDTO);
                } else {
                    showAlert(Alert.AlertType.ERROR, "Not Found", "No item found");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Something went wrong: " + e.getMessage());
        }
    }

    @FXML
    public void cleanFields() {
        
        loadNextItemId();
        itemname.clear();
        itemcategory.clear();
        itemprice.clear();
        itemphoto.setImage(null);
        selectedImageFile = null;
    }

    @FXML
    public void printReport() {
        try {
            itemBO.printitemreport();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to generate report: " + e.getMessage());
        }
    }

    private void loaditemtable() {
        try {
            List<ItemDTO> itemList = itemBO.getItem();
            ObservableList<ItemDTO> oblist = FXCollections.observableArrayList(itemList);
            tblitem.setItems(oblist);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load items: " + e.getMessage());
        }
    }

    
    private boolean validateInputs(String name, String category, String priceText) {
        if (name.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Item name cannot be empty");
            return false;
        }

        if (name.length() < 2) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Item name must be at least 2 characters");
            return false;
        }

        if (category.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Category cannot be empty");
            return false;
        }

        if (priceText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Price cannot be empty");
            return false;
        }

        try {
            double price = Double.parseDouble(priceText);
            if (price < 0) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Price cannot be negative");
                return false;
            }
            if (price == 0) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Price is zero. Continue?");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Price must be a valid number");
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