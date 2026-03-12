package lk.ijse.star_digitalBook.bo.custom;

import lk.ijse.star_digitalBook.bo.SuperBO;
import lk.ijse.star_digitalBook.dto.supplierDTO;
import lk.ijse.star_digitalBook.entity.supplierentity;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO extends SuperBO {

    int getNextSuppllierId() throws SQLException,ClassNotFoundException;


    boolean savesup(supplierDTO supdto) throws SQLException,ClassNotFoundException ;


    supplierentity searchsup(int id) throws SQLException,ClassNotFoundException  ;


    boolean updatesup(supplierDTO supdto) throws SQLException,ClassNotFoundException  ;


    boolean deletesup(String id) throws SQLException,ClassNotFoundException  ;


    ArrayList<supplierDTO> getsuppliers() throws SQLException,ClassNotFoundException  ;


    ArrayList<supplierentity> getSuppliersByStatus(String status) throws SQLException,ClassNotFoundException  ;


    ArrayList<supplierentity> getSuppliersByCompany(String companyName) throws SQLException,ClassNotFoundException  ;


    ArrayList<String> getAllCompanies() throws SQLException,ClassNotFoundException  ;


    ArrayList<String> getAllAgencies() throws SQLException,ClassNotFoundException  ;


    int getTotalSupplierCount() throws SQLException,ClassNotFoundException  ;


    int getActiveSupplierCount() throws SQLException,ClassNotFoundException  ;


}
