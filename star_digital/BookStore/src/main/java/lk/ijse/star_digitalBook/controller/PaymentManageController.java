package lk.ijse.star_digitalBook.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.ijse.star_digitalBook.controller.models.PaymentModel;
import lk.ijse.star_digitalBook.dto.paymentDTO;

public class PaymentManageController implements Initializable {

    @FXML
    private TextField paymentIdTxt, orderIdTxt, customerIdTxt, netAmountTxt, paidAmountTxt, balanceTxt;
    @FXML
    private ComboBox<String> paymentTypeCombo;
    @FXML
    private Label balanceLabel;

    
    private static String selectedOrderId;
    private static String selectedCustomerId;
    private static double selectedTotal;

    
    private double netAmount = 0;
    private double paidAmount = 0;
    private double balance = 0;

    public static void setOrderData(String orderId, String customerId, double total) {
        selectedOrderId = orderId;
        selectedCustomerId = customerId;
        selectedTotal = total;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        setupPaymentTypeCombo();
        
        
        loadNextPaymentId();
        
       
        if (selectedOrderId != null && !selectedOrderId.isEmpty()) {
            orderIdTxt.setText(selectedOrderId);
            customerIdTxt.setText(selectedCustomerId);
            netAmount = selectedTotal;
            netAmountTxt.setText(String.format("%.2f", netAmount));
        }
        
        
        paidAmountTxt.textProperty().addListener((obs, oldVal, newVal) -> calculateBalance());
    }

    private void setupPaymentTypeCombo() {
        paymentTypeCombo.getItems().addAll("CASH", "CARD", "CHECK", "MOBILE_PAYMENT", "ONLINE");
    }

    private void loadNextPaymentId() {
        try {
            String nextId = PaymentModel.getNextPaymentId();
            paymentIdTxt.setText(nextId);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load payment ID");
        }
    }

    private void calculateBalance() {
        try {
            String paidText = paidAmountTxt.getText().trim();
            
            if (paidText.isEmpty()) {
                balance = netAmount;
                balanceTxt.setText("");
                balanceLabel.setText("Balance: Rs. 0.00");
                return;
            }

            paidAmount = Double.parseDouble(paidText);
            
            if (paidAmount < 0) {
                showAlert(Alert.AlertType.WARNING, "Invalid Input", "Paid amount cannot be negative");
                paidAmountTxt.clear();
                return;
            }

            balance = netAmount - paidAmount;
            balanceTxt.setText(String.format("%.2f", Math.abs(balance)));
            
            if (balance > 0) {
                balanceLabel.setText("Balance Due: Rs. " + String.format("%.2f", balance));
            } else if (balance < 0) {
                balanceLabel.setText("Change: Rs. " + String.format("%.2f", Math.abs(balance)));
            } else {
                balanceLabel.setText("No Balance");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid amount");
            paidAmountTxt.clear();
        }
    }

    @FXML
    public void processPayment() {
        
        if (paymentTypeCombo.getValue() == null || paymentTypeCombo.getValue().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please select a payment type");
            return;
        }

        if (paidAmountTxt.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter paid amount");
            return;
        }

        // Step 3: Check if payment is sufficient
        if (paidAmount < netAmount) {
            showAlert(Alert.AlertType.WARNING, "Insufficient Payment", 
                    "Paid amount is less than net amount.\nBalance Due: Rs. " + String.format("%.2f", balance));
            return;
        }

        try {
           
            String paymentId = paymentIdTxt.getText();
            String orderId = orderIdTxt.getText();
            String customerId = customerIdTxt.getText();
            String paymentMethod = paymentTypeCombo.getValue();
            String total = String.format("%.2f", netAmount);
            String paidAmount = String.format("%.2f", this.paidAmount);
            String balance = String.format("%.2f", this.balance);

            paymentDTO payment = new paymentDTO(
                paymentId,
                orderId,
                customerId,
                paymentMethod,
                total,
                paidAmount,
                balance
            );

            
            boolean saved = PaymentModel.savePayment(payment);
            
            if (saved) {
                showAlert(Alert.AlertType.INFORMATION, "Success", 
                        "Payment processed successfully!\n\nPayment ID: " + paymentId);
                clearFields();
                loadNextPaymentId();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to save payment");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Something went wrong: " + e.getMessage());
        }
    }

    @FXML
    public void clearFields() {
        paidAmountTxt.clear();
        balanceTxt.clear();
        paymentTypeCombo.setValue(null);
        balanceLabel.setText("Balance: Rs. 0.00");
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @FXML
    private void handlePrintBill() {
        try {
            String orderId = orderIdTxt.getText().trim();
            if (orderId.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Order ID is empty!").show();
                return;
            }

            PaymentModel paymentModel = new PaymentModel();
            paymentModel.printBill(orderId);  
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Bill printing failed: " + e.getMessage()).show();
        }
}
}