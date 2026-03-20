package lk.ijse.star_digitalBook.dao.custom.Impl;

import lk.ijse.star_digitalBook.dao.CrudUtil;
import lk.ijse.star_digitalBook.dao.custom.OrderDAO;
import lk.ijse.star_digitalBook.dto.orderDTO;
import lk.ijse.star_digitalBook.entity.orderentity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public ArrayList<orderentity> getAll() throws SQLException, ClassNotFoundException {
        try {
            ResultSet rs = CrudUtil.execute("SELECT * FROM orders ORDER BY order_date DESC");
            ArrayList<orderentity> ordersList = new ArrayList<>();

            if (rs != null) {
                while (rs.next()) {
                    ordersList.add(mapResultSetToOrder(rs));
                }
            }
            return ordersList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to get all orders: " + e.getMessage());
        }
    }

    @Override
    public boolean save(orderentity entity) throws SQLException, ClassNotFoundException {
        try {
            // Check if customer exists
            String checkCustomerSQL = "SELECT customer_id FROM customer WHERE customer_id = ?";
            ResultSet checkRS = CrudUtil.execute(checkCustomerSQL, entity.getCustomerid());

            // If customer doesn't exist, insert them
            if (checkRS == null || !checkRS.next()) {
                String insertCustomerSQL = "INSERT INTO customer(customer_id, contact_number) VALUES(?, ?)";
                CrudUtil.execute(insertCustomerSQL, entity.getCustomerid(), entity.getOrdercontact());
            }

            // Insert the order
            String sql = "INSERT INTO orders (order_id, customer_id, order_date, order_type, order_contact, amount, discount, total) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            return CrudUtil.execute(sql,
                    entity.getOrderid(),
                    entity.getCustomerid(),
                    entity.getOrderDate(),
                    entity.getOrdertype(),
                    entity.getOrdercontact(),
                    entity.getAmount(),
                    entity.getDiscount(),
                    entity.getTotal()
            );
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to save order: " + e.getMessage());
        }
    }

    @Override
    public boolean update(orderentity entity) throws SQLException, ClassNotFoundException {
        try {
            String sql = "UPDATE orders SET customer_id = ?, order_type = ?, order_contact = ?, " +
                    "amount = ?, discount = ?, total = ? WHERE order_id = ?";
            return CrudUtil.execute(sql,
                    entity.getCustomerid(),
                    entity.getOrdertype(),
                    entity.getOrdercontact(),
                    entity.getAmount(),
                    entity.getDiscount(),
                    entity.getTotal(),
                    entity.getOrderid()
            );
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to update order: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        try {
            String sql = "DELETE FROM orders WHERE order_id = ?";
            return CrudUtil.execute(sql, id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to delete order: " + e.getMessage());
        }
    }

    @Override
    public orderentity search(int id) throws SQLException, ClassNotFoundException {
        try {
            String sql = "SELECT * FROM orders WHERE order_id = ?";
            ResultSet rs = CrudUtil.execute(sql, id);

            if (rs != null && rs.next()) {
                return mapResultSetToOrder(rs);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to get order: " + e.getMessage());
        }
    }

    @Override
    public int getnext() throws SQLException, ClassNotFoundException {
        try {
            String sql = "SELECT MAX(CAST(SUBSTRING(order_id, 4) AS UNSIGNED)) as max_id FROM orders";
            ResultSet rs = CrudUtil.execute(sql);

            int nextNum = 1;
            if (rs != null && rs.next()) {
                int maxId = rs.getInt("max_id");
                if (maxId > 0) {
                    nextNum = maxId + 1;
                }
            }
            return nextNum;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to get next order ID: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<orderentity> getOrdersByCustomerId(String customerId) throws SQLException {
        try {
            String sql = "SELECT * FROM orders WHERE customer_id = ? ORDER BY order_date DESC";
            ResultSet rs = CrudUtil.execute(sql, customerId);
            ArrayList<orderentity> ordersList = new ArrayList<>();

            if (rs != null) {
                while (rs.next()) {
                    ordersList.add(mapResultSetToOrder(rs));
                }
            }
            return ordersList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to get orders by customer: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<orderentity> getOrdersByType(String orderType) throws SQLException {
        try {
            String sql = "SELECT * FROM orders WHERE order_type = ? ORDER BY order_date DESC";
            ResultSet rs = CrudUtil.execute(sql, orderType);
            ArrayList<orderentity> ordersList = new ArrayList<>();

            if (rs != null) {
                while (rs.next()) {
                    ordersList.add(mapResultSetToOrder(rs));
                }
            }
            return ordersList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to get orders by type: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<orderentity> getOrdersByDateRange(String startDate, String endDate) throws SQLException {
        try {
            String sql = "SELECT * FROM orders WHERE DATE(order_date) BETWEEN ? AND ? ORDER BY order_date DESC";
            ResultSet rs = CrudUtil.execute(sql, startDate, endDate);
            ArrayList<orderentity> ordersList = new ArrayList<>();

            if (rs != null) {
                while (rs.next()) {
                    ordersList.add(mapResultSetToOrder(rs));
                }
            }
            return ordersList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to get orders by date range: " + e.getMessage());
        }
    }

    @Override
    public int getTotalOrderCount() throws SQLException {
        try {
            String sql = "SELECT COUNT(*) FROM orders";
            ResultSet rs = CrudUtil.execute(sql);
            if (rs != null && rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to get total order count: " + e.getMessage());
        }
    }

    @Override
    public double getTotalRevenue() throws SQLException {
        try {
            String sql = "SELECT SUM(CAST(total AS DECIMAL(10,2))) FROM orders";
            ResultSet rs = CrudUtil.execute(sql);
            if (rs != null && rs.next()) {
                return rs.getDouble(1);
            }
            return 0.0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to get total revenue: " + e.getMessage());
        }
    }

    @Override
    public orderentity mapResultSetToOrder(ResultSet rs) throws SQLException {
        try {
            return new orderentity(
                    rs.getString("order_id"),
                    rs.getTimestamp("order_date").toLocalDateTime(),
                    rs.getString("order_type"),
                    rs.getString("order_contact"),
                    rs.getString("customer_id"),
                    rs.getDouble("amount"),
                    String.valueOf(rs.getDouble("discount")),
                    String.valueOf(rs.getDouble("total")),
                    new ArrayList<>()
            );
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to map order: " + e.getMessage());
        }
    }
}
