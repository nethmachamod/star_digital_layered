package lk.ijse.star_digitalBook.controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lk.ijse.star_digitalBook.dto.ItemDTO;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Item_cardController implements Initializable {
    @FXML
    private Label lblName;
    @FXML
    private Label lblPrice;
    @FXML
    private ImageView imgItem;
    @FXML
    private Spinner<Integer> spnQuantity;
    @FXML
    private Button btnAdd;
    
    private ItemDTO itemData;
    private OnAddToCartListener addToCartListener;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupSpinner();
        if (btnAdd != null) {
            btnAdd.setOnAction(event -> handleAddToCart());
        }
    }
    
    private void setupSpinner() {
        if (spnQuantity != null) {
            SpinnerValueFactory<Integer> valueFactory = 
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
            spnQuantity.setValueFactory(valueFactory);
        }
    }
    
    public void setData(ItemDTO dto) {
        this.itemData = dto;
        displayItemData();
    }
    
    private void displayItemData() {
        if (itemData != null) {
            if (lblName != null) {
                lblName.setText(itemData.getName());
            }
            
            if (lblPrice != null) {
                lblPrice.setText("RS. " + String.format("%.2f", itemData.getPrice()));
            }
            if (imgItem != null && itemData.getImagePath() != null && !itemData.getImagePath().isEmpty()) {
                try {
                    File imageFile = new File(itemData.getImagePath());
                    if (imageFile.exists()) {
                        Image image = new Image(imageFile.toURI().toString());
                        imgItem.setImage(image);
                    } else {
                        System.out.println("Image file not found: " + itemData.getImagePath());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Failed to load image: " + e.getMessage());
                }
            }
        }
    }
    
    private void handleAddToCart() {
        if (itemData == null) {
            System.err.println("Item data not set");
            return;
        }
        int quantity = spnQuantity.getValue();
        
        if (addToCartListener != null) {
            addToCartListener.onAddToCart(itemData, quantity);
        } else {
            System.out.println("Added " + quantity + " of " + itemData.getName() + " to cart");
        }
        
        if (spnQuantity != null) {
            spnQuantity.getValueFactory().setValue(1);
        }
    }
    
    public void setOnAddToCartListener(OnAddToCartListener listener) {
        this.addToCartListener = listener;
    }
    
    @FunctionalInterface
    public interface OnAddToCartListener {
        void onAddToCart(ItemDTO item, int quantity);
    }
}