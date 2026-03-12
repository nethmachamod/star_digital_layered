package lk.ijse.star_digitalBook.dao.custom.Impl;

import lk.ijse.star_digitalBook.dao.CrudUtil;
import lk.ijse.star_digitalBook.dao.custom.PaymentDAO;
import lk.ijse.star_digitalBook.db.DbConnection;
import lk.ijse.star_digitalBook.dto.paymentDTO;
import lk.ijse.star_digitalBook.entity.paymententity;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public ArrayList<paymententity> getPaymentsByOrderId(String orderId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM payment WHERE order_id = ? ORDER BY payment_date DESC";
        ResultSet rs = CrudUtil.execute(sql, orderId);
        ArrayList<paymententity> paymentsList = new ArrayList<>();

        if (rs != null) {
            while (rs.next()) {
                paymentsList.add(mapResultSetToPayment(rs));
            }
        }
        return paymentsList;
    }

    @Override
    public ArrayList<paymententity> getPaymentsByCustomerId(String customerId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM payment WHERE customer_id = ? ORDER BY payment_date DESC";
        ResultSet rs = CrudUtil.execute(sql, customerId);
        ArrayList<paymententity> paymentsList = new ArrayList<>();

        if (rs != null) {
            while (rs.next()) {
                paymentsList.add(mapResultSetToPayment(rs));
            }
        }
        return paymentsList;
    }

    @Override
    public paymententity mapResultSetToPayment(ResultSet rs) throws SQLException, ClassNotFoundException {
        return new paymententity(
                rs.getString("payment_id"),
                rs.getString("order_id"),
                rs.getString("customer_id"),
                rs.getString("payment_method"),
                String.valueOf(rs.getDouble("total")),
                String.valueOf(rs.getDouble("paid_amount")),
                String.valueOf(rs.getDouble("balance"))
        );
    }

    @Override
    public void printBill(String order_id) throws SQLException, JRException, ClassNotFoundException {

        Connection conn = DbConnection.getInstance().getConnection();
        InputStream inputStream = getClass().getResourceAsStream("/resources/MyReports/bill.jrxml");
        JasperReport jr = JasperCompileManager.compileReport(inputStream);

        Map<String,Object>params=new HashMap<>();
        params.put("order_id",order_id);
        JasperPrint jp = JasperFillManager.fillReport(jr,params,conn);

        JasperViewer.viewReport(jp,false);
    }

    @Override
    public ArrayList<paymententity> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM payment ORDER BY payment_date DESC";
        ResultSet rs = CrudUtil.execute(sql);
        ArrayList<paymententity> paymentsList = new ArrayList<>();

        if (rs != null) {
            while (rs.next()) {
                paymentsList.add(mapResultSetToPayment(rs));
            }
        }
        return paymentsList;
    }

    @Override
    public boolean save(paymententity entity) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO payment (payment_id, order_id, customer_id, payment_method, total, paid_amount, balance) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql,
                entity.getPaymentid(),
                entity.getOrderid(),
                entity.getCustomer_id(),
                entity.getPaymentmethod(),
                entity.getTotal(),
                entity.getPaidamount(),
                entity.getBalanace()
        );
    }

    @Override
    public boolean update(paymententity entity) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE payment SET order_id = ?, customer_id = ?, payment_method = ?, " +
                "total = ?, paid_amount = ?, balance = ? WHERE payment_id = ?";
        return CrudUtil.execute(sql,
                entity.getOrderid(),
                entity.getCustomer_id(),
                entity.getPaymentmethod(),
                entity.getTotal(),
                entity.getPaidamount(),
                entity.getBalanace(),
                entity.getPaymentid()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM payment WHERE payment_id = ?";
        return CrudUtil.execute(sql, id);
    }

    @Override
    public paymententity search(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM payment WHERE payment_id = ?";
        ResultSet rs = CrudUtil.execute(sql, id);

        if (rs != null && rs.next()) {
            return mapResultSetToPayment(rs);
        }
        return null;
    }

    @Override
    public int getnext() throws SQLException, ClassNotFoundException {
        String sql = "SELECT MAX(CAST(SUBSTRING(payment_id, 4) AS UNSIGNED)) as max_id FROM payment";
        ResultSet rs = CrudUtil.execute(sql);

        int nextNum = 1;
        if (rs != null && rs.next()) {
            int maxId = rs.getInt("max_id");
            if (maxId > 0) {
                nextNum = maxId + 1;
            }
        }
        return Integer.parseInt("PAY" + String.format("%04d", nextNum));
    }
}
