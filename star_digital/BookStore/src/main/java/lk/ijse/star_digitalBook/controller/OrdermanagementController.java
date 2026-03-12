package lk.ijse.star_digitalBook.controller;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.star_digitalBook.bo.BOFactory;
import lk.ijse.star_digitalBook.bo.custom.InventoryBO;
import lk.ijse.star_digitalBook.bo.custom.OrderBO;

import lk.ijse.star_digitalBook.bo.custom.OrderItemsBO;
import lk.ijse.star_digitalBook.dto.CartItemDTO;
import lk.ijse.star_digitalBook.dto.OrderItemDTO;
import lk.ijse.star_digitalBook.dto.orderDTO;
import lk.ijse.star_digitalBook.dao.CrudUtil;

public class OrdermanagementController implements Initializable {

    @FXML
    private TextField searchField, orderId, customerId, orderContact, amount, discount, total;
    @FXML
    private ComboBox<String> orderType;
    @FXML
    private TableView<orderDTO> tblOrders;
    @FXML
    private TableColumn<orderDTO, String> colOrderId, colCustomerId, colOrderType, colOrderContact, colDiscount, colTotal;
    @FXML
    private TableColumn<orderDTO, String> colOrderDate;
    @FXML
    private TableColumn<orderDTO, Double> colAmount;
    @FXML
    private TableView<CartItemDTO> tblOrderItems;
    @FXML
    private TableColumn<CartItemDTO, String> colItemName;
    @FXML
    private TableColumn<CartItemDTO, Integer> colItemQty;
    @FXML
    private TableColumn<CartItemDTO, Double> colItemPrice;
    @FXML
    private Label lblCartTotal;


    OrderBO orderBO=(OrderBO) BOFactory.getInstance().getBO(BOFactory.BOType.ORDER);
    OrderItemsBO orderItemsBO=(OrderItemsBO) BOFactory.getInstance().getBO(BOFactory.BOType.ORDER_ITEM);

    private static List<CartItemDTO> pendingCartItems = new ArrayList<>();
    private static double pendingCartTotal = 0.0;

    public static void setCartItems(List<CartItemDTO> items) {
        pendingCartItems = new ArrayList<>(items);
    }

    public static void setCartTotal(double total) {
        pendingCartTotal = total;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTableColumns();
        setupOrderTypeCombo();
        setupCartItemsTable();
        loadAllOrders();
        loadNextOrderId();
        loadNextCustomerId();

       
        if (!pendingCartItems.isEmpty()) {
            displayPendingCartItems();
        }

        discount.textProperty().addListener((obs, oldVal, newVal) -> calculateTotal());
    }

    private void setupTableColumns() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderid"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerid"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colOrderType.setCellValueFactory(new PropertyValueFactory<>("ordertype"));
        colOrderContact.setCellValueFactory(new PropertyValueFactory<>("ordercontact"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colItemQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colItemPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        
        tblOrders.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                orderDTO selected = tblOrders.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    populateOrderFields(selected);
                    try {
                        loadOrderItems(selected.getOrderid());
                    } catch (Exception e) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Failed to load order items: " + e.getMessage());
                    }
                }
            }
        });
    }

    private void setupOrderTypeCombo() {
        orderType.getItems().addAll("RETAIL","WHOLE SALE");
    }

    private void setupCartItemsTable() {
        tblOrderItems.setItems(FXCollections.observableArrayList(pendingCartItems));
    }

    private void displayPendingCartItems() {
        tblOrderItems.setItems(FXCollections.observableArrayList(pendingCartItems));
        lblCartTotal.setText("Rs. " + String.format("%.2f", pendingCartTotal));
        amount.setText(String.format("%.2f", pendingCartTotal));
        calculateTotal();
    }

    @FXML
    public void searchOrder() {
        String searchInput = searchField.getText().trim();

        if (searchInput.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter order ID");
            return;
        }

        try {
            orderDTO order = orderBO.getOrder(searchInput);
            if (order != null) {
                populateOrderFields(order);
                loadOrderItems(searchInput);
            } else {
                showAlert(Alert.AlertType.ERROR, "Not Found", "Order not found");
                clearFields();
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error searching order: " + e.getMessage());
        }
    }

    private void populateOrderFields(orderDTO order) {
        orderId.setText(order.getOrderid());
        customerId.setText(order.getCustomerid());
        orderType.setValue(order.getOrdertype());
        orderContact.setText(order.getOrdercontact());
        amount.setText(String.valueOf(order.getAmount()));
        discount.setText(order.getDiscount());
        total.setText(order.getTotal());
    }

    private void loadOrderItems(String orderId) throws Exception {
        List<OrderItemDTO> orderItems = orderItemsBO.getOrderItemsByOrderId(orderId);
        List<CartItemDTO> cartItems = new ArrayList<>();
        double cartTotal = 0;

        for (OrderItemDTO item : orderItems) {
            CartItemDTO cartItem = new CartItemDTO(
                    item.getItemId(),
                    item.getItemName(),
                    item.getQuantity(),
                    item.getUnitPrice(),
                    item.getLineTotal()
            );
            cartItems.add(cartItem);
            cartTotal += item.getLineTotal();
        }

        tblOrderItems.setItems(FXCollections.observableArrayList(cartItems));
        lblCartTotal.setText("Rs. " + String.format("%.2f", cartTotal));
    }

    @FXML
    public void updateOrder() {
        if (orderId.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please select order to update");
            return;
        }

        try {
            double orderAmount = parseDouble(amount.getText());
            double orderDiscount = discount.getText().isEmpty() ? 0 : parseDouble(discount.getText());
            double orderTotal = orderAmount - orderDiscount;

            orderDTO order = new orderDTO(
                    orderId.getText(),
                    null,
                    orderType.getValue(),
                    orderContact.getText(),
                    customerId.getText(),
                    orderAmount,
                    String.format("%.2f", orderDiscount),
                    String.format("%.2f", orderTotal),
                    new ArrayList<>()
            );

            boolean updated = orderBO.updateOrder(order);
            if (updated) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Order updated successfully");
                clearAllData();
                loadAllOrders();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update order");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error updating order: " + e.getMessage());
        }
    }

    @FXML
    public void deleteOrder() {
        if (orderId.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please select order to delete");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this order?");
        if (confirm.showAndWait().get() == ButtonType.OK) {
            try {
                String orderIdValue = orderId.getText();
                orderItemsBO.deleteOrderItems(orderIdValue);

                boolean deleted = orderBO.deleteOrder(orderIdValue);
                if (deleted) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Order deleted successfully");
                    clearAllData();
                    loadAllOrders();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete order");
                }
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Error deleting order: " + e.getMessage());
            }
        }
    }

    @FXML
    public void paybutton() {
        
        if (orderType.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Invalid Input", "Please select order type (TAKE_AWAY, DELIVERY, or DINING)");
            return;
        }

        
        if ("DELIVERY".equals(orderType.getValue())) {
            String contact = orderContact.getText().trim();
            if (contact.isEmpty() || !contact.matches("^07[0-9]{8}$")) {
                showAlert(Alert.AlertType.WARNING, "Invalid Input", "Please enter valid contact number (07XXXXXXXX) for delivery");
                return;
            }
        }

        if (pendingCartItems.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Empty Cart", "Please add items to cart");
            return;
        }

        try {
            String newOrderId = orderBO.getNextOrderId();
            String contactNumber = orderContact.getText().trim().isEmpty() ? "N/A" : orderContact.getText().trim();

            
            String newCustomerId = createOrGetCustomer(contactNumber);

            
            orderId.setText(newOrderId);
            customerId.setText(newCustomerId);

            double orderAmount = pendingCartTotal;
            double orderDiscount = discount.getText().isEmpty() ? 0 : Double.parseDouble(discount.getText());
            double orderTotal = orderAmount - orderDiscount;

            orderDTO order = new orderDTO(
                    newOrderId,
                    LocalDateTime.now(),
                    orderType.getValue(),
                    contactNumber,
                    newCustomerId,
                    orderAmount,
                    String.format("%.2f", orderDiscount),
                    String.format("%.2f", orderTotal),
                    new ArrayList<>()
            );

            if (orderBO.saveOrder(order)) {
                saveOrderItems(newOrderId);

                showAlert(Alert.AlertType.INFORMATION, "Success", "Order saved successfully with ID: " + newOrderId);

                
                PaymentManageController.setOrderData(newOrderId, newCustomerId, orderTotal);

                
                Platform.runLater(() -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(
                                getClass().getResource("/lk/ijse/star_digitalBook/paymentmanage.fxml")
                        );
                        Parent root = loader.load();

                        Stage stage = new Stage();
                        stage.setTitle("Payment Management");
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        showAlert(Alert.AlertType.ERROR, "Error", "Failed to open payment window: " + e.getMessage());
                    }
                });

                clearAllData();
                loadAllOrders();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to save order");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Something went wrong: " + e.getMessage());
        }
    }

    // 
    private String createOrGetCustomer(String contactNumber) throws Exception {
        
        ResultSet rs = CrudUtil.execute(
                "SELECT customer_id FROM customer WHERE contact_number = ?",
                contactNumber
        );

        if (rs.next()) {
            return rs.getString("customer_id");
        }

        
        String newCustomerId = getNextCustomerId();
        CrudUtil.execute(
                "INSERT INTO customer(customer_id, contact_number) VALUES(?, ?)",
                newCustomerId,
                contactNumber
        );
        return newCustomerId;
    }

   private void saveOrderItems(String orderId) throws Exception {
    
    for (CartItemDTO c : pendingCartItems) {
        double lineTotal = c.getQuantity() * c.getUnitPrice();
        OrderItemDTO item = new OrderItemDTO(
                0, 
                orderId,  
                c.getItemId(), 
                c.getItemName(),
                c.getQuantity(), 
                c.getUnitPrice(), 
                lineTotal
        );
        orderItemsBO.saveOrderItem(item);
    }
}

    @FXML
    public void refreshOrders() {
        clearFields();
        loadAllOrders();
        showAlert(Alert.AlertType.INFORMATION, "Success", "Orders refreshed successfully");
    }

    @FXML
    public void clearFields() {
        searchField.clear();
        orderId.clear();
        customerId.clear();
        orderContact.clear();
        amount.clear();
        discount.clear();
        total.clear();
        orderType.setValue(null);
        tblOrderItems.setItems(FXCollections.observableArrayList());
        lblCartTotal.setText("Rs. 0.00");

        loadNextOrderId();
        loadNextCustomerId();
    }

    private void loadAllOrders() {
        try {
            List<orderDTO> orders = orderBO.getAllOrders();
            ObservableList<orderDTO> oblist = FXCollections.observableArrayList(orders);
            tblOrders.setItems(oblist);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load orders: " + e.getMessage());
        }
    }

    private void clearAllData() {
        pendingCartItems = new ArrayList<>();
        pendingCartTotal = 0;
        clearFields();
    }

    private void loadNextOrderId() {
        try {
            orderId.setText(orderBO.getNextOrderId());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load next order ID");
        }
    }

    private void loadNextCustomerId() {
        try {
            customerId.setText(getNextCustomerId());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load next customer ID");
        }
    }

    private String getNextCustomerId() throws Exception {
        ResultSet rs = CrudUtil.execute(
                "SELECT MAX(CAST(SUBSTRING(customer_id, 4) AS UNSIGNED)) as max_id FROM customer"
        );
        int nextNum = 1;
        if (rs != null && rs.next()) {
            int maxId = rs.getInt("max_id");
            if (maxId > 0) nextNum = maxId + 1;
        }
        return "CUS" + String.format("%03d", nextNum);
    }

    private void calculateTotal() {
        try {
            double amountVal = parseDouble(amount.getText());
            double discountVal = parseDouble(discount.getText());
            double totalVal = amountVal - discountVal;
            total.setText(String.format("%.2f", totalVal));
        } catch (Exception e) {
            
        }
    }

    private double parseDouble(String value) {
        try {
            return value == null || value.isEmpty() ? 0 : Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}