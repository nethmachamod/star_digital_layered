package lk.ijse.star_digitalBook.dao.custom;

import lk.ijse.star_digitalBook.dao.CrudDAO;
import lk.ijse.star_digitalBook.dao.CrudUtil;
import lk.ijse.star_digitalBook.db.DbConnection;
import lk.ijse.star_digitalBook.dto.paymentDTO;
import lk.ijse.star_digitalBook.entity.paymententity;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PaymentDAO extends CrudDAO<paymententity> {


     ArrayList<paymententity> getPaymentsByOrderId(String orderId) throws SQLException,ClassNotFoundException ;

     ArrayList<paymententity> getPaymentsByCustomerId(String customerId) throws SQLException,ClassNotFoundException ;


     paymententity mapResultSetToPayment(ResultSet rs) throws SQLException,ClassNotFoundException ;

     void printBill(String order_id ) throws SQLException, JRException,ClassNotFoundException ;
}
