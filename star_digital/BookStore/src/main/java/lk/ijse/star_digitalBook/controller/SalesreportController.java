package lk.ijse.star_digitalBook.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import lk.ijse.star_digitalBook.controller.models.SalesReportModel;
import lk.ijse.star_digitalBook.dto.DailyChartDTO;

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
            for (DailyChartDTO dto : SalesReportModel.getDailyOrders()) {
                series.getData().add(
                    new XYChart.Data<>(dto.getDate(), dto.getValue())
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
            for (DailyChartDTO dto : SalesReportModel.getDailyCustomers()) {
                series.getData().add(
                    new XYChart.Data<>(dto.getDate(), dto.getValue())
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
            String.format("%.2f", SalesReportModel.getTodayIncome())
        );
            lblTodayOrders.setText(
            String.valueOf(SalesReportModel.getTodayOrders())
        );
            lblTotalIncome.setText(
            String.format("%.2f", SalesReportModel.getTotalIncome())
        );
            lblTodayCustomers.setText(
            String.valueOf(SalesReportModel.getTodayCustomers())
        );
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
