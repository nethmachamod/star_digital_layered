package lk.ijse.star_digitalBook.controller.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lk.ijse.star_digitalBook.dto.supplierDTO;
import lk.ijse.star_digitalBook.dao.CrudUtil;

public class SupplierModel {

   
    public static int getNextSuppllierId() throws SQLException {
        String sql = "SELECT supplier_id FROM supplier ORDER BY supplier_id DESC LIMIT 1";
        ResultSet rs = CrudUtil.execute(sql);

        if (rs.next()) {
            return rs.getInt(1) + 1;
        } else {
            return 1; 
        }
    }

   
    public static boolean savesup(supplierDTO supdto) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO supplier(supplier_name, contact_number, date, Agency, company, status, image_path) VALUES (?,?,?,?,?,?,?)",
                supdto.getSupname(),
                supdto.getSupcontact(),
                java.sql.Date.valueOf(supdto.getSupdate()),
                supdto.getSupagency(),
                supdto.getCompanyname(),
                supdto.getSupstatus(),
                supdto.getImagepath()
        );
    }

    
    public static supplierDTO searchsup(int id) throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM supplier WHERE supplier_id=?", id);

        if (rs.next()) {
            return new supplierDTO(
                    rs.getInt("supplier_id"),
                    rs.getString("supplier_name"),
                    rs.getString("contact_number"),
                    rs.getDate("date").toLocalDate(),
                    rs.getString("Agency"),
                    rs.getString("company"),
                    rs.getString("status"),
                    rs.getString("image_path")
            );
        }
        return null;
    }

    
    public static boolean updatesup(supplierDTO supdto) throws SQLException {
        return CrudUtil.execute(
                "UPDATE supplier SET supplier_name=?, contact_number=?, Agency=?, company=?, status=?, date=?, image_path=? WHERE supplier_id=?",
                supdto.getSupname(),
                supdto.getSupcontact(),
                supdto.getSupagency(),
                supdto.getCompanyname(),
                supdto.getSupstatus(),
                java.sql.Date.valueOf(supdto.getSupdate()),
                supdto.getImagepath(),
                supdto.getSupid()
        );
    }

    
    public static boolean deletesup(String id) throws SQLException {
        return CrudUtil.execute("DELETE FROM supplier WHERE supplier_id=?", id);
    }

    
    public List<supplierDTO> getsuppliers() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM supplier ORDER BY supplier_id DESC");
        List<supplierDTO> supplierlist = new ArrayList<>();

        while (rs.next()) {
            supplierDTO supdto = new supplierDTO(
                    rs.getInt("supplier_id"),
                    rs.getString("supplier_name"),
                    rs.getString("contact_number"),
                    rs.getDate("date").toLocalDate(),
                    rs.getString("Agency"),
                    rs.getString("company"),
                    rs.getString("status"),
                    rs.getString("image_path")
            );
            supplierlist.add(supdto);
        }
        return supplierlist;
    }

   
    public static List<supplierDTO> getSuppliersByStatus(String status) throws SQLException {
        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM supplier WHERE status = ? ORDER BY supplier_id DESC",
                status
        );
        List<supplierDTO> supplierlist = new ArrayList<>();

        while (rs.next()) {
            supplierDTO supdto = new supplierDTO(
                    rs.getInt("supplier_id"),
                    rs.getString("supplier_name"),
                    rs.getString("contact_number"),
                    rs.getDate("date").toLocalDate(),
                    rs.getString("Agency"),
                    rs.getString("company"),
                    rs.getString("status"),
                    rs.getString("image_path")
            );
            supplierlist.add(supdto);
        }
        return supplierlist;
    }

    
    public static List<supplierDTO> getSuppliersByCompany(String companyName) throws SQLException {
        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM supplier WHERE company = ? ORDER BY supplier_id DESC",
                companyName
        );
        List<supplierDTO> supplierlist = new ArrayList<>();

        while (rs.next()) {
            supplierDTO supdto = new supplierDTO(
                    rs.getInt("supplier_id"),
                    rs.getString("supplier_name"),
                    rs.getString("contact_number"),
                    rs.getDate("date").toLocalDate(),
                    rs.getString("Agency"),
                    rs.getString("company"),
                    rs.getString("status"),
                    rs.getString("image_path")
            );
            supplierlist.add(supdto);
        }
        return supplierlist;
    }

    
    public static List<String> getAllCompanies() throws SQLException {
        ResultSet rs = CrudUtil.execute(
                "SELECT DISTINCT company FROM supplier ORDER BY company"
        );
        List<String> companies = new ArrayList<>();

        while (rs.next()) {
            companies.add(rs.getString("company"));
        }
        return companies;
    }

    
    public static List<String> getAllAgencies() throws SQLException {
        ResultSet rs = CrudUtil.execute(
                "SELECT DISTINCT Agency FROM supplier ORDER BY Agency"
        );
        List<String> agencies = new ArrayList<>();

        while (rs.next()) {
            agencies.add(rs.getString("Agency"));
        }
        return agencies;
    }

    
    public static int getTotalSupplierCount() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT COUNT(*) FROM supplier");
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    
    public static int getActiveSupplierCount() throws SQLException {
        ResultSet rs = CrudUtil.execute(
                "SELECT COUNT(*) FROM supplier WHERE status = 'Active'"
        );
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }
}