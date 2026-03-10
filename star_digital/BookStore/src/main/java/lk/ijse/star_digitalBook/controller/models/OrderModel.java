package lk.ijse.star_digitalBook.controller.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lk.ijse.star_digitalBook.dto.orderDTO;
import lk.ijse.star_digitalBook.dao.CrudUtil;

public class OrderModel {

    /**
     * Save a new order to the database
     * FIXED: Proper customer handling
     * @param order Order data transfer object
     * @return true if save successful
     * @throws SQLException if database error occurs
     */
    public static boolean saveOrder(orderDTO order) throws SQLException {
        try {
            // Check if customer exists
            String checkCustomerSQL = "SELECT customer_id FROM customer WHERE customer_id = ?";
            ResultSet checkRS = CrudUtil.execute(checkCustomerSQL, order.getCustomerid());

            // If customer doesn't exist, insert them
            if (checkRS == null || !checkRS.next()) {
                String insertCustomerSQL = "INSERT INTO customer(customer_id, contact_number) VALUES(?, ?)";
                CrudUtil.execute(insertCustomerSQL, order.getCustomerid(), order.getOrdercontact());
            }

            // Insert the order
            String sql = "INSERT INTO orders (order_id, customer_id, order_date, order_type, order_contact, amount, discount, total) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            return CrudUtil.execute(sql,
                    order.getOrderid(),
                    order.getCustomerid(),
                    order.getOrderDate(),
                    order.getOrdertype(),
                    order.getOrdercontact(),
                    order.getAmount(),
                    order.getDiscount(),
                    order.getTotal()
            );
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to save order: " + e.getMessage());
        }
    }

    /**
     * Get a single order by Order ID
     * @param orderId Order ID to search
     * @return Order DTO if found, null otherwise
     * @throws SQLException if database error occurs
     */
    public static orderDTO getOrder(String orderId) throws SQLException {
        try {
            String sql = "SELECT * FROM orders WHERE order_id = ?";
            ResultSet rs = CrudUtil.execute(sql, orderId);

            if (rs != null && rs.next()) {
                return mapResultSetToOrder(rs);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to get order: " + e.getMessage());
        }
    }

    /**
     * Get all orders from the database, ordered by date descending
     * @return List of all orders
     * @throws SQLException if database error occurs
     */
    public static List<orderDTO> getAllOrders() throws SQLException {
        try {
            String sql = "SELECT * FROM orders ORDER BY order_date DESC";
            ResultSet rs = CrudUtil.execute(sql);
            List<orderDTO> ordersList = new ArrayList<>();

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

    /**
     * Get all orders for a specific customer
     * @param customerId Customer ID to filter by
     * @return List of orders for the customer
     * @throws SQLException if database error occurs
     */
    public static List<orderDTO> getOrdersByCustomerId(String customerId) throws SQLException {
        try {
            String sql = "SELECT * FROM orders WHERE customer_id = ? ORDER BY order_date DESC";
            ResultSet rs = CrudUtil.execute(sql, customerId);
            List<orderDTO> ordersList = new ArrayList<>();

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

    /**
     * Update an existing order
     * @param order Order data transfer object with ID
     * @return true if update successful
     * @throws SQLException if database error occurs
     */
    public static boolean updateOrder(orderDTO order) throws SQLException {
        try {
            String sql = "UPDATE orders SET customer_id = ?, order_type = ?, order_contact = ?, " +
                    "amount = ?, discount = ?, total = ? WHERE order_id = ?";
            return CrudUtil.execute(sql,
                    order.getCustomerid(),
                    order.getOrdertype(),
                    order.getOrdercontact(),
                    order.getAmount(),
                    order.getDiscount(),
                    order.getTotal(),
                    order.getOrderid()
            );
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to update order: " + e.getMessage());
        }
    }

    /**
     * Delete an order by Order ID
     * @param orderId Order ID to delete
     * @return true if delete successful
     * @throws SQLException if database error occurs
     */
    public static boolean deleteOrder(String orderId) throws SQLException {
        try {
            String sql = "DELETE FROM orders WHERE order_id = ?";
            return CrudUtil.execute(sql, orderId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to delete order: " + e.getMessage());
        }
    }

    /**
     * Get the next order ID in sequence (ORD0001, ORD0002, etc.)
     * @return Next order ID
     * @throws SQLException if database error occurs
     */
    public static String getNextOrderId() throws SQLException {
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
            return "ORD" + String.format("%04d", nextNum);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to get next order ID: " + e.getMessage());
        }
    }

    /**
     * ADDED: Get orders by type
     * @param orderType Order type to filter by
     * @return List of orders with specified type
     * @throws SQLException if database error occurs
     */
    public static List<orderDTO> getOrdersByType(String orderType) throws SQLException {
        try {
            String sql = "SELECT * FROM orders WHERE order_type = ? ORDER BY order_date DESC";
            ResultSet rs = CrudUtil.execute(sql, orderType);
            List<orderDTO> ordersList = new ArrayList<>();

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

    /**
     * ADDED: Get orders by date range
     * @param startDate Start date
     * @param endDate End date
     * @return List of orders within date range
     * @throws SQLException if database error occurs
     */
    public static List<orderDTO> getOrdersByDateRange(String startDate, String endDate) throws SQLException {
        try {
            String sql = "SELECT * FROM orders WHERE DATE(order_date) BETWEEN ? AND ? ORDER BY order_date DESC";
            ResultSet rs = CrudUtil.execute(sql, startDate, endDate);
            List<orderDTO> ordersList = new ArrayList<>();

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

    /**
     * ADDED: Get total order count
     * @return Total number of orders
     * @throws SQLException if database error occurs
     */
    public static int getTotalOrderCount() throws SQLException {
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

    /**
     * ADDED: Get total revenue
     * @return Total revenue from all orders
     * @throws SQLException if database error occurs
     */
    public static double getTotalRevenue() throws SQLException {
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

    /**
     * Helper method to map ResultSet to orderDTO
     * @param rs ResultSet containing order data
     * @return Order DTO
     * @throws SQLException if database error occurs
     */
    private static orderDTO mapResultSetToOrder(ResultSet rs) throws SQLException {
        try {
            return new orderDTO(
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