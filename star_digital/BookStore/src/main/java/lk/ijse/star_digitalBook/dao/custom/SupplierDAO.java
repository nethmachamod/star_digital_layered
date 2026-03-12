package lk.ijse.star_digitalBook.dao.custom;

import lk.ijse.star_digitalBook.dao.CrudDAO;
import lk.ijse.star_digitalBook.dao.CrudUtil;
import lk.ijse.star_digitalBook.dto.supplierDTO;
import lk.ijse.star_digitalBook.entity.supplierentity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface SupplierDAO extends CrudDAO<supplierentity> {

    ArrayList<supplierentity> getSuppliersByStatus(String status) throws SQLException,ClassNotFoundException  ;


     ArrayList<supplierentity> getSuppliersByCompany(String companyName) throws SQLException,ClassNotFoundException  ;


     ArrayList<String> getAllCompanies() throws SQLException,ClassNotFoundException  ;


     ArrayList<String> getAllAgencies() throws SQLException,ClassNotFoundException  ;


     int getTotalSupplierCount() throws SQLException,ClassNotFoundException  ;


     int getActiveSupplierCount() throws SQLException,ClassNotFoundException  ;

}
