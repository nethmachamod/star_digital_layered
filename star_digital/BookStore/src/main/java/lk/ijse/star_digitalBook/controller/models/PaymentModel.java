package lk.ijse.star_digitalBook.controller.models;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lk.ijse.star_digitalBook.db.DbConnection;
import lk.ijse.star_digitalBook.dto.paymentDTO;
import lk.ijse.star_digitalBook.dao.CrudUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class PaymentModel {

    public static boolean savePayment(paymentDTO payment) throws SQLException {
        String sql = "INSERT INTO payment (payment_id, order_id, customer_id, payment_method, total, paid_amount, balance) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql,
                payment.getPaymentid(),
                payment.getOrderid(),
                payment.getCustomer_id(),
                payment.getPaymentmethod(),
                payment.getTotal(),
                payment.getPaidamount(),
                payment.getBalanace()
        );
    }

    public static paymentDTO getPayment(String paymentId) throws SQLException {
        String sql = "SELECT * FROM payment WHERE payment_id = ?";
        ResultSet rs = CrudUtil.execute(sql, paymentId);
        
        if (rs != null && rs.next()) {
            return mapResultSetToPayment(rs);
        }
        return null;
    }

    public static List<paymentDTO> getAllPayments() throws SQLException {
        String sql = "SELECT * FROM payment ORDER BY payment_date DESC";
        ResultSet rs = CrudUtil.execute(sql);
        List<paymentDTO> paymentsList = new ArrayList<>();
        
        if (rs != null) {
            while (rs.next()) {
                paymentsList.add(mapResultSetToPayment(rs));
            }
        }
        return paymentsList;
    }

    public static List<paymentDTO> getPaymentsByOrderId(String orderId) throws SQLException {
        String sql = "SELECT * FROM payment WHERE order_id = ? ORDER BY payment_date DESC";
        ResultSet rs = CrudUtil.execute(sql, orderId);
        List<paymentDTO> paymentsList = new ArrayList<>();
        
        if (rs != null) {
            while (rs.next()) {
                paymentsList.add(mapResultSetToPayment(rs));
            }
        }
        return paymentsList;
    }

    public static List<paymentDTO> getPaymentsByCustomerId(String customerId) throws SQLException {
        String sql = "SELECT * FROM payment WHERE customer_id = ? ORDER BY payment_date DESC";
        ResultSet rs = CrudUtil.execute(sql, customerId);
        List<paymentDTO> paymentsList = new ArrayList<>();
        
        if (rs != null) {
            while (rs.next()) {
                paymentsList.add(mapResultSetToPayment(rs));
            }
        }
        return paymentsList;
    }

    public static boolean updatePayment(paymentDTO payment) throws SQLException {
        String sql = "UPDATE payment SET order_id = ?, customer_id = ?, payment_method = ?, " +
                     "total = ?, paid_amount = ?, balance = ? WHERE payment_id = ?";
        return CrudUtil.execute(sql,
                payment.getOrderid(),
                payment.getCustomer_id(),
                payment.getPaymentmethod(),
                payment.getTotal(),
                payment.getPaidamount(),
                payment.getBalanace(),
                payment.getPaymentid()
        );
    }

    public static boolean deletePayment(String paymentId) throws SQLException {
        String sql = "DELETE FROM payment WHERE payment_id = ?";
        return CrudUtil.execute(sql, paymentId);
    }

    public static String getNextPaymentId() throws SQLException {
        String sql = "SELECT MAX(CAST(SUBSTRING(payment_id, 4) AS UNSIGNED)) as max_id FROM payment";
        ResultSet rs = CrudUtil.execute(sql);
        
        int nextNum = 1;
        if (rs != null && rs.next()) {
            int maxId = rs.getInt("max_id");
            if (maxId > 0) {
                nextNum = maxId + 1;
            }
        }
        return "PAY" + String.format("%04d", nextNum);
    }

    private static paymentDTO mapResultSetToPayment(ResultSet rs) throws SQLException {
        return new paymentDTO(
                rs.getString("payment_id"),
                rs.getString("order_id"),
                rs.getString("customer_id"),
                rs.getString("payment_method"),
                String.valueOf(rs.getDouble("total")),
                String.valueOf(rs.getDouble("paid_amount")),
                String.valueOf(rs.getDouble("balance"))
        );
    }
    
        public void printBill(String order_id ) throws SQLException, JRException {
        Connection conn = DbConnection.getInstance().getConnection();
        InputStream inputStream = getClass().getResourceAsStream("/resources/MyReports/bill.jrxml");
        JasperReport jr = JasperCompileManager.compileReport(inputStream);

        Map<String,Object>params=new HashMap<>();
        params.put("order_id",order_id);
        JasperPrint jp = JasperFillManager.fillReport(jr,params,conn);

        JasperViewer.viewReport(jp,false);
    }
}
