package lk.ijse.star_digitalBook.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import lk.ijse.star_digitalBook.bo.BOFactory;
import lk.ijse.star_digitalBook.bo.custom.InventoryBO;
import lk.ijse.star_digitalBook.bo.custom.SalesBO;
import lk.ijse.star_digitalBook.dto.DailyChartDTO;
import lk.ijse.star_digitalBook.entity.DailyChartentyti;

public class SalesreportController implements Initializable {

    @FXML
    private LineChart<String, Number> dailyCustomerChart;

    @FXML
    private BarChart<String, Number> dailyOrdersChart;
    
    @FXML
    private Label lblTodayIncome;
    
     @FXML
    private Label lblTodayOrders;
     
     @FXML
    private Label lblTotalIncome;
     
     @FXML
    private Label lblTodayCustomers;

    SalesBO salesBO=(SalesBO) BOFactory.getInstance().getBO(BOFactory.BOType.SALES_REPORT);
     

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadOrdersChart();
        loadCustomerChart();
        loadSummaryCards();
    }
    @FXML
    private void loadOrdersChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Daily Orders");

        try {
            for (DailyChartentyti entity : salesBO.getDailyOrders()) {
                series.getData().add(
                    new XYChart.Data<>(entity.getDate(), entity.getValue())
                );
            }
            dailyOrdersChart.getData().clear();
            dailyOrdersChart.getData().add(series);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void loadCustomerChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Daily Customers");

        try {
            for (DailyChartentyti entity : salesBO.getDailyCustomers()) {
                series.getData().add(
                    new XYChart.Data<>(entity.getDate(), entity.getValue())
                );
            }
            dailyCustomerChart.getData().clear();
            dailyCustomerChart.getData().add(series);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void loadSummaryCards(){
        
        try{
            lblTodayIncome.setText(
            String.format("%.2f", salesBO.getTodayIncome())
        );
            lblTodayOrders.setText(
            String.valueOf(salesBO.getTodayOrders())
        );
            lblTotalIncome.setText(
            String.format("%.2f", salesBO.getTotalIncome())
        );
            lblTodayCustomers.setText(
            String.valueOf(salesBO.getTodayCustomers())
        );
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
