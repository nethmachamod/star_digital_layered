package lk.ijse.star_digitalBook.bo.custom.Impl;

import lk.ijse.star_digitalBook.bo.custom.SupplierBO;
import lk.ijse.star_digitalBook.dao.DAOFactory;
import lk.ijse.star_digitalBook.dao.custom.OrderItemsDAO;
import lk.ijse.star_digitalBook.dao.custom.SupplierDAO;
import lk.ijse.star_digitalBook.dto.supplierDTO;
import lk.ijse.star_digitalBook.entity.supplierentity;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {

    SupplierDAO supplierDAO =(SupplierDAO) DAOFactory.getInstance().getDAOType(DAOFactory.DAOType.SUPPLIER);


    @Override
    public int getNextSuppllierId() throws SQLException,ClassNotFoundException {
        return supplierDAO.getnext();
    }

    @Override
    public boolean savesup(supplierDTO supdto) throws SQLException, ClassNotFoundException {
        return supplierDAO.save(new supplierentity(supdto.getSupid(),supdto.getSupname(),supdto.getSupcontact(),supdto.getSupdate(),supdto.getSupagency(),supdto.getCompanyname(),supdto.getSupstatus(),supdto.getImagepath()));

    }

    @Override
    public supplierentity searchsup(int id) throws SQLException, ClassNotFoundException {
        return supplierDAO.search(id);
    }

    @Override
    public boolean updatesup(supplierDTO supdto) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(new supplierentity(supdto.getSupid(),supdto.getSupname(),supdto.getSupcontact(),supdto.getSupdate(),supdto.getSupagency(),supdto.getCompanyname(),supdto.getSupstatus(),supdto.getImagepath()));

    }

    @Override
    public boolean deletesup(String id) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(id);
    }

    @Override
    public ArrayList<supplierDTO> getsuppliers() throws SQLException, ClassNotFoundException {
        ArrayList<supplierentity> entity=supplierDAO.getAll();
        ArrayList<supplierDTO> list=new ArrayList<>();
        for (supplierentity sup:entity){
            supplierDTO dto=new supplierDTO(sup.getSupid(),sup.getSupname(),sup.getSupcontact(),sup.getSupdate(),sup.getSupagency(),sup.getCompanyname(),sup.getSupstatus(),sup.getImagepath());
        list.add(dto);
        }
        return list;
    }

    @Override
    public ArrayList<supplierentity> getSuppliersByStatus(String status) throws SQLException, ClassNotFoundException {
        ArrayList<supplierentity> entity=supplierDAO.getSuppliersByStatus(status);
        ArrayList<supplierentity> list=new ArrayList<>();
        for (supplierentity sup:entity){
            list.add(new supplierentity(sup.getSupid(),sup.getSupname(),sup.getSupcontact(),sup.getSupdate(),sup.getSupagency(),sup.getCompanyname(),sup.getSupstatus(),sup.getImagepath()));
        }
        return list;
    }

    @Override
    public ArrayList<supplierentity> getSuppliersByCompany(String companyName) throws SQLException, ClassNotFoundException {
        ArrayList<supplierentity> entity=supplierDAO.getSuppliersByCompany(companyName);
        ArrayList<supplierentity> list=new ArrayList<>();
        for (supplierentity sup:entity){
            list.add(new supplierentity(sup.getSupid(),sup.getSupname(),sup.getSupcontact(),sup.getSupdate(),sup.getSupagency(),sup.getCompanyname(),sup.getSupstatus(),sup.getImagepath()));
        }
        return list;
    }

    @Override
    public ArrayList<String> getAllCompanies() throws SQLException, ClassNotFoundException {
        return supplierDAO.getAllCompanies();
    }

    @Override
    public ArrayList<String> getAllAgencies() throws SQLException, ClassNotFoundException {
        return supplierDAO.getAllAgencies();
    }

    @Override
    public int getTotalSupplierCount() throws SQLException, ClassNotFoundException {
        return supplierDAO.getTotalSupplierCount();
    }

    @Override
    public int getActiveSupplierCount() throws SQLException, ClassNotFoundException {
        return supplierDAO.getActiveSupplierCount();
    }
}
