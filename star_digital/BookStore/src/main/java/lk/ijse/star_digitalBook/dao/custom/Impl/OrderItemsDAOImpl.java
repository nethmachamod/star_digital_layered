package lk.ijse.star_digitalBook.dao.custom.Impl;

import lk.ijse.star_digitalBook.dao.CrudUtil;
import lk.ijse.star_digitalBook.dao.custom.OrderItemsDAO;
import lk.ijse.star_digitalBook.dto.OrderItemDTO;
import lk.ijse.star_digitalBook.entity.OrderItementity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemsDAOImpl implements OrderItemsDAO {
    @Override
    public ArrayList<OrderItementity> getOrderItemsByOrderId(String orderId) throws SQLException, ClassNotFoundException {
        ResultSet rs =CrudUtil.execute( "SELECT oi.order_item_id, oi.order_id, oi.item_id, fi.item_name, oi.quantity, oi.unit_price, oi.line_total " +
                "FROM order_items oi " +
                "LEFT JOIN item fi ON oi.item_id = fi.item_id " +
                "WHERE oi.order_id = ?");
//        ResultSet rs = CrudUtil.execute(sql, orderId);
        ArrayList<OrderItementity> itemsList = new ArrayList<>();

        if (rs != null) {
            while (rs.next()) {
                itemsList.add(mapResultSetToOrderItem(rs));
            }
        }
        return itemsList;
    }

    @Override
    public OrderItementity mapResultSetToOrderItem(ResultSet rs) throws SQLException, ClassNotFoundException {
        return new OrderItementity(
                rs.getInt("order_item_id"),
                rs.getString("order_id"),
                rs.getInt("item_id"),
                rs.getString("item_name") != null ? rs.getString("item_name") : "",
                rs.getInt("quantity"),
                rs.getDouble("unit_price"),
                rs.getDouble("line_total")
        );
    }


    @Override
    public ArrayList<OrderItementity> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(OrderItementity entity) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO order_items (order_id, item_id, quantity, unit_price, line_total) " +
                "VALUES (?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql,
                entity.getOrderId(),
                entity.getItemId(),
                entity.getQuantity(),
                entity.getUnitPrice(),
                entity.getLineTotal()
        );
    }

    @Override
    public boolean update(OrderItementity entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute( "DELETE FROM order_items WHERE order_id = ?");

    }

    @Override
    public OrderItementity search(int id) throws SQLException, ClassNotFoundException {

        ResultSet rs = CrudUtil.execute("SELECT * FROM order_items WHERE order_item_id = ?");

        if (rs != null && rs.next()) {
            return mapResultSetToOrderItem(rs);
        }
        return null;
    }

    @Override
    public int getnext() throws SQLException, ClassNotFoundException {
        return 0;
    }
}
