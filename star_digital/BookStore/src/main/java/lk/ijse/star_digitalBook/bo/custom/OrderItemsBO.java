package lk.ijse.star_digitalBook.bo.custom;

import lk.ijse.star_digitalBook.bo.SuperBO;
import lk.ijse.star_digitalBook.dao.CrudUtil;
import lk.ijse.star_digitalBook.dto.OrderItemDTO;
import lk.ijse.star_digitalBook.entity.OrderItementity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OrderItemsBO extends SuperBO {

     boolean saveOrderItem(OrderItemDTO orderItem) throws SQLException,ClassNotFoundException ;

     ArrayList<OrderItemDTO> getOrderItemsByOrderId(String orderId) throws SQLException,ClassNotFoundException ;

     OrderItemDTO getOrderItem(int orderItemId) throws SQLException,ClassNotFoundException ;

     boolean deleteOrderItems(String orderId) throws SQLException,ClassNotFoundException ;

     OrderItementity mapResultSetToOrderItem(ResultSet rs) throws SQLException,ClassNotFoundException ;

}
