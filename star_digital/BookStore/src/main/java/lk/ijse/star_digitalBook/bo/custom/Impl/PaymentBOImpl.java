package lk.ijse.star_digitalBook.bo.custom.Impl;

import lk.ijse.star_digitalBook.bo.custom.PaymentBO;
import lk.ijse.star_digitalBook.dao.DAOFactory;
import lk.ijse.star_digitalBook.dao.custom.OrderItemsDAO;
import lk.ijse.star_digitalBook.dao.custom.PaymentDAO;
import lk.ijse.star_digitalBook.dto.OrderItemDTO;
import lk.ijse.star_digitalBook.dto.orderDTO;
import lk.ijse.star_digitalBook.dto.paymentDTO;
import lk.ijse.star_digitalBook.entity.OrderItementity;
import lk.ijse.star_digitalBook.entity.orderentity;
import lk.ijse.star_digitalBook.entity.paymententity;
import net.sf.jasperreports.engine.JRException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PaymentBOImpl implements PaymentBO {

    PaymentDAO paymentDAO =(PaymentDAO) DAOFactory.getInstance().getDAOType(DAOFactory.DAOType.PAYMENT);

    @Override
    public boolean savePayment(paymentDTO payment) throws SQLException, ClassNotFoundException {
        return paymentDAO.save(new paymententity(payment.getPaymentid(),payment.getOrderid(),payment.getCustomer_id(),payment.getPaymentmethod(),payment.getTotal(),payment.getPaidamount(),payment.getBalanace()));


    }

    @Override
    public paymentDTO getPayment(String paymentId) throws SQLException, ClassNotFoundException {
        paymententity payment =paymentDAO.search(Integer.parseInt(paymentId));
        return new paymentDTO(payment.getPaymentid(),payment.getOrderid(),payment.getCustomer_id(),payment.getPaymentmethod(),payment.getTotal(),payment.getPaidamount(),payment.getBalanace());


    }

    @Override
    public ArrayList<paymententity> getAllPayments() throws SQLException, ClassNotFoundException {
        ArrayList<paymententity> entity=paymentDAO.getAll();
        ArrayList<paymententity> list=new ArrayList<>();
        for (paymententity payment:entity){
            list.add(new paymententity(payment.getPaymentid(),payment.getOrderid(),payment.getCustomer_id(),payment.getPaymentmethod(),payment.getTotal(),payment.getPaidamount(),payment.getBalanace()));
        }
        return list;
    }

    @Override
    public ArrayList<paymentDTO> getPaymentsByOrderId(String orderId) throws SQLException, ClassNotFoundException {
        ArrayList<paymententity> entity=paymentDAO.getPaymentsByOrderId(orderId);
        ArrayList<paymentDTO> list=new ArrayList<>();
        for (paymententity payment:entity){
            list.add(new paymentDTO(payment.getPaymentid(),payment.getOrderid(),payment.getCustomer_id(),payment.getPaymentmethod(),payment.getTotal(),payment.getPaidamount(),payment.getBalanace()));

        }

        return list;
    }

    @Override
    public ArrayList<paymentDTO> getPaymentsByCustomerId(String customerId) throws SQLException, ClassNotFoundException {
        ArrayList<paymententity> entity=paymentDAO.getPaymentsByCustomerId(customerId);
        ArrayList<paymentDTO> list=new ArrayList<>();
        for (paymententity payment:entity){
            list.add(new paymentDTO(payment.getPaymentid(),payment.getOrderid(),payment.getCustomer_id(),payment.getPaymentmethod(),payment.getTotal(),payment.getPaidamount(),payment.getBalanace()));

        }

        return list;
    }

    @Override
    public boolean updatePayment(paymentDTO payment) throws SQLException, ClassNotFoundException {
        return paymentDAO.update(new paymententity(payment.getPaymentid(),payment.getOrderid(),payment.getCustomer_id(),payment.getPaymentmethod(),payment.getTotal(),payment.getPaidamount(),payment.getBalanace()));

    }

    @Override
    public boolean deletePayment(String paymentId) throws SQLException, ClassNotFoundException {
        return paymentDAO.delete(paymentId);
    }

    @Override
    public String getNextPaymentId() throws SQLException, ClassNotFoundException {
        return String.valueOf(paymentDAO.getnext());
    }

    @Override
    public paymententity mapResultSetToPayment(ResultSet rs) throws SQLException, ClassNotFoundException {
        return paymentDAO.mapResultSetToPayment(rs);
    }

    @Override
    public void printBill(String order_id) throws SQLException, JRException, ClassNotFoundException {
        paymentDAO.printBill(order_id);
    }
}
