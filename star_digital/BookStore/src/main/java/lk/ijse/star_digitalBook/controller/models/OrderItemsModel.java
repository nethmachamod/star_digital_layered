package lk.ijse.star_digitalBook.controller.models;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lk.ijse.star_digitalBook.dto.OrderItemDTO;
import lk.ijse.star_digitalBook.dao.CrudUtil;

public class OrderItemsModel {
  
    public static boolean saveOrderItem(OrderItemDTO orderItem) throws SQLException {
        String sql = "INSERT INTO order_items (order_id, item_id, quantity, unit_price, line_total) " +
                     "VALUES (?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql,
                orderItem.getOrderId(),
                orderItem.getItemId(),
                orderItem.getQuantity(),
                orderItem.getUnitPrice(),
                orderItem.getLineTotal()
        );
    }
    
    public static List<OrderItemDTO> getOrderItemsByOrderId(String orderId) throws SQLException {
        
        String sql = "SELECT oi.order_item_id, oi.order_id, oi.item_id, fi.item_name, oi.quantity, oi.unit_price, oi.line_total " +
                     "FROM order_items oi " +
                     "LEFT JOIN item fi ON oi.item_id = fi.item_id " +
                     "WHERE oi.order_id = ?";
        ResultSet rs = CrudUtil.execute(sql, orderId); 
        List<OrderItemDTO> itemsList = new ArrayList<>();
        
        if (rs != null) {
            while (rs.next()) {
                itemsList.add(mapResultSetToOrderItem(rs));
            }
        }
        return itemsList;
    }
    
    public static OrderItemDTO getOrderItem(int orderItemId) throws SQLException {
        String sql = "SELECT * FROM order_items WHERE order_item_id = ?";
        ResultSet rs = CrudUtil.execute(sql, orderItemId);
        
        if (rs != null && rs.next()) {
            return mapResultSetToOrderItem(rs);
        }
        return null;
    }
   
    public static boolean deleteOrderItems(String orderId) throws SQLException {
        
        String sql = "DELETE FROM order_items WHERE order_id = ?";
        return CrudUtil.execute(sql, orderId); 
    }
    
    private static OrderItemDTO mapResultSetToOrderItem(ResultSet rs) throws SQLException {
        return new OrderItemDTO(
                rs.getInt("order_item_id"),
                rs.getString("order_id"), 
                rs.getInt("item_id"),
                rs.getString("item_name") != null ? rs.getString("item_name") : "",
                rs.getInt("quantity"),
                rs.getDouble("unit_price"),
                rs.getDouble("line_total")
        );
    }
}