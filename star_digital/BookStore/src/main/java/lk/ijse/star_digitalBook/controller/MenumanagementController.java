package lk.ijse.star_digitalBook.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import lk.ijse.star_digitalBook.App;

import lk.ijse.star_digitalBook.dto.CartItemDTO;
import lk.ijse.star_digitalBook.dto.ItemDTO;
import lk.ijse.star_digitalBook.util.MenuRefresher;

public class MenumanagementController implements Initializable {

    @FXML
    private FlowPane flowMenu;

    @FXML
    private TableView<CartItemDTO> tblCart;

    @FXML
    private TableColumn<CartItemDTO, String> colItemName;

    @FXML
    private TableColumn<CartItemDTO, Integer> colQuantity;

    @FXML
    private TableColumn<CartItemDTO, Double> colPrice;

    @FXML
    private Label lblTotal;

    @FXML
    private Button btnProceedToOrder;

    private final ItemModel itemModel = new ItemModel();
    private ObservableList<CartItemDTO> cartItems = FXCollections.observableArrayList();
    private double cartTotal = 0.0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTableColumns();
        setupCartTable();


        MenuRefresher.refreshMenu = this::loadMenuItems;

        loadMenuItems();

        if (btnProceedToOrder != null) {
            btnProceedToOrder.setOnAction(event -> proceedToOrder());
        }


        tblCart.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void setupTableColumns() {
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));


        TableColumn<CartItemDTO, Void> colAction = new TableColumn<>("Action");
        colAction.setPrefWidth(80);
        colAction.setCellFactory(param -> new TableCell<CartItemDTO, Void>() {
            private final Button btnRemove = new Button("Remove");

            {
                btnRemove.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-padding: 5px;");
                btnRemove.setOnAction(event -> {
                    CartItemDTO item = getTableView().getItems().get(getIndex());
                    removeFromCart(item);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnRemove);
            }
        });
        tblCart.getColumns().add(colAction);
    }

    private void setupCartTable() {
        tblCart.setItems(cartItems);
        updateCartTotal();
    }

    private void loadMenuItems() {
        flowMenu.getChildren().clear();
        try {
            List<ItemDTO> itemList = itemModel.getItem();

            if (itemList.isEmpty()) {
                Label noItemsLabel = new Label("No menu items available");
                noItemsLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #7f8c8d;");
                flowMenu.getChildren().add(noItemsLabel);
                return;
            }

            for (ItemDTO dto : itemList) {

                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/lk/ijse/star_digitalBook/item_card.fxml")
                );

                Parent card = loader.load();
                Item_cardController controller = loader.getController();
                controller.setData(dto);
                controller.setOnAddToCartListener(this::addToCart);
                flowMenu.getChildren().add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load menu items: " + e.getMessage());
        }
    }

    private void addToCart(ItemDTO item, int quantity) {
        if (item == null || quantity <= 0) {
            showAlert(Alert.AlertType.WARNING, "Invalid Input", "Invalid item or quantity");
            return;
        }


        CartItemDTO existingItem = cartItems.stream()
                .filter(ci -> ci.getItemId() == item.getId())
                .findFirst()
                .orElse(null);

        if (existingItem != null) {

            int newQty = existingItem.getQuantity() + quantity;
            existingItem.setQuantity(newQty);
            existingItem.setTotalPrice(existingItem.getUnitPrice() * newQty);


            tblCart.refresh();
        } else {

            CartItemDTO cartItem = new CartItemDTO(
                    item.getId(),
                    item.getName(),
                    quantity,
                    item.getPrice(),
                    item.getPrice() * quantity
            );
            cartItems.add(cartItem);
        }

        updateCartTotal();


        showAlert(Alert.AlertType.INFORMATION, "Success",
                quantity + "x " + item.getName() + " added to cart");
    }

    private void removeFromCart(CartItemDTO item) {
        if (item == null) return;

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Remove Item");
        confirmAlert.setHeaderText("Remove from Cart");
        confirmAlert.setContentText("Remove " + item.getItemName() + " from cart?");

        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                cartItems.remove(item);
                updateCartTotal();
            }
        });
    }

    private void updateCartTotal() {
        cartTotal = cartItems.stream()
                .mapToDouble(CartItemDTO::getTotalPrice)
                .sum();
        lblTotal.setText("Rs. " + String.format("%.2f", cartTotal));
    }

    @FXML
    private void proceedToOrder() {
        if (cartItems.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Empty Cart", "Cart is empty. Please add items first.");
            return;
        }

        try {

            OrdermanagementController.setCartItems(new ArrayList<>(cartItems));
            OrdermanagementController.setCartTotal(cartTotal);

            Parent menuFXML = App.loadFXML("ordermanagement");
            AnchorPane mainContent = (AnchorPane) btnProceedToOrder.getScene().getRoot().lookup("#maincontent");

            if (mainContent != null) {
                mainContent.getChildren().setAll(menuFXML);


                cartItems.clear();
                updateCartTotal();
            } else {
                showAlert(Alert.AlertType.WARNING, "Navigation Error", "Could not find main content area");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Error loading order screen: " + e.getMessage());
        }
    }

    @FXML
    private void clearCart() {
        if (cartItems.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Cart Empty", "Cart is already empty");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Clear Cart");
        confirmAlert.setHeaderText("Clear All Items");
        confirmAlert.setContentText("Are you sure you want to clear all items from cart?");

        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                cartItems.clear();
                updateCartTotal();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Cart cleared");
            }
        });
    }

    @FXML
    private void refreshMenu() {
        loadMenuItems();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}