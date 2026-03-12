package lk.ijse.star_digitalBook.dao.custom;

import lk.ijse.star_digitalBook.dao.CrudDAO;
import lk.ijse.star_digitalBook.dao.CrudUtil;
import lk.ijse.star_digitalBook.dao.SuperDAO;
import lk.ijse.star_digitalBook.dto.DailyChartDTO;
import lk.ijse.star_digitalBook.entity.DailyChartentyti;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface SalesDAO extends SuperDAO {

     ArrayList<DailyChartentyti> getDailyOrders() throws Exception ;


     ArrayList<DailyChartentyti> getDailyCustomers() throws Exception ;

     double getTodayIncome() throws SQLException ;

     int getTodayOrders() throws SQLException ;

     double getTotalIncome() throws SQLException ;

     int getTodayCustomers() throws SQLException ;


}
