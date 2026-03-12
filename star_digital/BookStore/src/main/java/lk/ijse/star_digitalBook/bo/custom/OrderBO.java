package lk.ijse.star_digitalBook.bo.custom;

import lk.ijse.star_digitalBook.bo.SuperBO;
import lk.ijse.star_digitalBook.dto.orderDTO;
import lk.ijse.star_digitalBook.entity.orderentity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderBO extends SuperBO {

    boolean saveOrder(orderDTO order) throws SQLException,ClassNotFoundException;

    orderDTO getOrder(String orderId) throws SQLException,ClassNotFoundException ;

    ArrayList<orderDTO> getAllOrders() throws SQLException,ClassNotFoundException ;

    ArrayList<orderDTO> getOrdersByCustomerId(String customerId) throws SQLException,ClassNotFoundException ;

    boolean updateOrder(orderDTO order) throws SQLException,ClassNotFoundException ;


    boolean deleteOrder(String orderId) throws SQLException,ClassNotFoundException ;

    String getNextOrderId() throws SQLException,ClassNotFoundException ;

    ArrayList<orderDTO> getOrdersByType(String orderType) throws SQLException,ClassNotFoundException ;

    ArrayList<orderDTO> getOrdersByDateRange(String startDate, String endDate) throws SQLException,ClassNotFoundException ;

    int getTotalOrderCount() throws SQLException,ClassNotFoundException ;

    double getTotalRevenue() throws SQLException,ClassNotFoundException ;

    orderentity mapResultSetToOrder(ResultSet rs) throws SQLException,ClassNotFoundException ;
}
