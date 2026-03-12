package lk.ijse.star_digitalBook.dao.custom;

import lk.ijse.star_digitalBook.dao.CrudDAO;
import lk.ijse.star_digitalBook.dto.orderDTO;
import lk.ijse.star_digitalBook.entity.orderentity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDAO extends CrudDAO<orderentity> {


    ArrayList<orderentity> getOrdersByCustomerId(String customerId) throws SQLException ;


    ArrayList<orderentity> getOrdersByType(String orderType) throws SQLException ;

    ArrayList<orderentity> getOrdersByDateRange(String startDate, String endDate) throws SQLException ;

    int getTotalOrderCount() throws SQLException ;

    double getTotalRevenue() throws SQLException ;

    orderentity mapResultSetToOrder(ResultSet rs) throws SQLException ;

}
