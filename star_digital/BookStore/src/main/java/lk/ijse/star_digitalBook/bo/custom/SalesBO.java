package lk.ijse.star_digitalBook.bo.custom;

import lk.ijse.star_digitalBook.bo.SuperBO;
import lk.ijse.star_digitalBook.entity.DailyChartentyti;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SalesBO extends SuperBO {
    ArrayList<DailyChartentyti> getDailyOrders() throws Exception ;


    ArrayList<DailyChartentyti> getDailyCustomers() throws Exception ;

    double getTodayIncome() throws SQLException;

    int getTodayOrders() throws SQLException ;

    double getTotalIncome() throws SQLException ;

    int getTodayCustomers() throws SQLException ;

}
