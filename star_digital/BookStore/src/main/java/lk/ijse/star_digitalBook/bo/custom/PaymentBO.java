package lk.ijse.star_digitalBook.bo.custom;

import lk.ijse.star_digitalBook.bo.SuperBO;
import lk.ijse.star_digitalBook.dao.CrudUtil;
import lk.ijse.star_digitalBook.dto.orderDTO;
import lk.ijse.star_digitalBook.dto.paymentDTO;
import lk.ijse.star_digitalBook.entity.paymententity;
import net.sf.jasperreports.engine.JRException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PaymentBO extends SuperBO {

    boolean savePayment(paymentDTO payment) throws SQLException,ClassNotFoundException ;

    paymentDTO getPayment(String paymentId) throws SQLException,ClassNotFoundException ;

    ArrayList<paymententity> getAllPayments() throws SQLException,ClassNotFoundException ;

    ArrayList<paymentDTO> getPaymentsByOrderId(String orderId) throws SQLException,ClassNotFoundException ;

    ArrayList<paymentDTO> getPaymentsByCustomerId(String customerId) throws SQLException,ClassNotFoundException ;

    boolean updatePayment(paymentDTO payment) throws SQLException,ClassNotFoundException ;

    boolean deletePayment(String paymentId) throws SQLException,ClassNotFoundException ;

    String getNextPaymentId() throws SQLException,ClassNotFoundException ;

    paymententity mapResultSetToPayment(ResultSet rs) throws SQLException,ClassNotFoundException ;

    void printBill(String order_id ) throws SQLException, JRException,ClassNotFoundException ;


}
