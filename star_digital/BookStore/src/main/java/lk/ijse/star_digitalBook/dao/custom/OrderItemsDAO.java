package lk.ijse.star_digitalBook.dao.custom;

import lk.ijse.star_digitalBook.dao.CrudDAO;
import lk.ijse.star_digitalBook.dto.OrderItemDTO;
import lk.ijse.star_digitalBook.entity.OrderItementity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderItemsDAO extends CrudDAO<OrderItementity> {
    ArrayList<OrderItementity> getOrderItemsByOrderId(String orderId) throws SQLException,ClassNotFoundException ;

    OrderItementity mapResultSetToOrderItem(ResultSet rs) throws SQLException,ClassNotFoundException ;
}
