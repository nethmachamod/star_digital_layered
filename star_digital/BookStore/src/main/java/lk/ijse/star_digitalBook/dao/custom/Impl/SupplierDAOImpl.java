package lk.ijse.star_digitalBook.dao.custom.Impl;

import lk.ijse.star_digitalBook.dao.CrudUtil;
import lk.ijse.star_digitalBook.dao.custom.SupplierDAO;
import lk.ijse.star_digitalBook.dto.supplierDTO;
import lk.ijse.star_digitalBook.entity.supplierentity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {
    @Override
    public ArrayList<supplierentity> getSuppliersByStatus(String status) throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM supplier WHERE status = ? ORDER BY supplier_id DESC",
                status
        );
        ArrayList<supplierentity> supplierlist = new ArrayList<>();

        while (rs.next()) {
            supplierentity suptity = new supplierentity(
                    rs.getInt("supplier_id"),
                    rs.getString("supplier_name"),
                    rs.getString("contact_number"),
                    rs.getDate("date").toLocalDate(),
                    rs.getString("Agency"),
                    rs.getString("company"),
                    rs.getString("status"),
                    rs.getString("image_path")
            );
            supplierlist.add(suptity);
        }
        return supplierlist;
    }

    @Override
    public ArrayList<supplierentity> getSuppliersByCompany(String companyName) throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM supplier WHERE company = ? ORDER BY supplier_id DESC",
                companyName
        );
        ArrayList<supplierentity> supplierlist = new ArrayList<>();

        while (rs.next()) {
            supplierentity suptity = new supplierentity(
                    rs.getInt("supplier_id"),
                    rs.getString("supplier_name"),
                    rs.getString("contact_number"),
                    rs.getDate("date").toLocalDate(),
                    rs.getString("Agency"),
                    rs.getString("company"),
                    rs.getString("status"),
                    rs.getString("image_path")
            );
            supplierlist.add(suptity);
        }
        return supplierlist;
    }

    @Override
    public ArrayList<String> getAllCompanies() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute(
                "SELECT DISTINCT company FROM supplier ORDER BY company"
        );
        ArrayList<String> companies = new ArrayList<>();

        while (rs.next()) {
            companies.add(rs.getString("company"));
        }
        return companies;
    }

    @Override
    public ArrayList<String> getAllAgencies() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute(
                "SELECT DISTINCT Agency FROM supplier ORDER BY Agency"
        );
        ArrayList<String> agencies = new ArrayList<>();

        while (rs.next()) {
            agencies.add(rs.getString("Agency"));
        }
        return agencies;
    }

    @Override
    public int getTotalSupplierCount() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT COUNT(*) FROM supplier");
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    @Override
    public int getActiveSupplierCount() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute(
                "SELECT COUNT(*) FROM supplier WHERE status = 'Active'"
        );
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    @Override
    public ArrayList<supplierentity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM supplier ORDER BY supplier_id DESC");
        ArrayList<supplierentity> supplierlist = new ArrayList<>();

        while (rs.next()) {
            supplierentity suptity = new supplierentity(
                    rs.getInt("supplier_id"),
                    rs.getString("supplier_name"),
                    rs.getString("contact_number"),
                    rs.getDate("date").toLocalDate(),
                    rs.getString("Agency"),
                    rs.getString("company"),
                    rs.getString("status"),
                    rs.getString("image_path")
            );
            supplierlist.add(suptity);
        }
        return supplierlist;
    }

    @Override
    public boolean save(supplierentity entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO supplier(supplier_name, contact_number, date, Agency, company, status, image_path) VALUES (?,?,?,?,?,?,?)",
                entity.getSupname(),
                entity.getSupcontact(),
                java.sql.Date.valueOf(entity.getSupdate()),
                entity.getSupagency(),
                entity.getCompanyname(),
                entity.getSupstatus(),
                entity.getImagepath()
        );
    }

    @Override
    public boolean update(supplierentity entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE supplier SET supplier_name=?, contact_number=?, Agency=?, company=?, status=?, date=?, image_path=? WHERE supplier_id=?",
                entity.getSupname(),
                entity.getSupcontact(),
                entity.getSupagency(),
                entity.getCompanyname(),
                entity.getSupstatus(),
                java.sql.Date.valueOf(entity.getSupdate()),
                entity.getImagepath(),
                entity.getSupid()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM supplier WHERE supplier_id=?", id);

    }

    @Override
    public supplierentity search(int id) throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM supplier WHERE supplier_id=?", id);

        if (rs.next()) {
            return new supplierentity(
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

    @Override
    public int getnext() throws SQLException, ClassNotFoundException {
        String sql = "SELECT supplier_id FROM supplier ORDER BY supplier_id DESC LIMIT 1";
        ResultSet rs = CrudUtil.execute(sql);

        if (rs.next()) {
            return rs.getInt(1) + 1;
        } else {
            return 1;
        }
    }
}
