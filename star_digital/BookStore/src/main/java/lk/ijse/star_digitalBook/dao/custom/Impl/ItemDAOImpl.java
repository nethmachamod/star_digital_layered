package lk.ijse.star_digitalBook.dao.custom.Impl;

import lk.ijse.star_digitalBook.dao.CrudUtil;
import lk.ijse.star_digitalBook.dao.custom.ItemDAO;
import lk.ijse.star_digitalBook.db.DbConnection;
import lk.ijse.star_digitalBook.dto.ItemDTO;
import lk.ijse.star_digitalBook.entity.Itementity;
import lk.ijse.star_digitalBook.entity.employeeentity;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public void printitemreport() throws Exception {
        Connection conn = DbConnection.getInstance().getConnection();


        InputStream inputstream = getClass().getResourceAsStream(
                "/lk/ijse/star_digitalBook/reports/bill.jrxml"
        );

        if (inputstream == null) {
            throw new Exception("Report template not found. Please ensure bill.jrxml exists in /resources/lk/ijse/star_digitalBook/reports/");
        }

        JasperReport jr = JasperCompileManager.compileReport(inputstream);
        JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
        JasperViewer.viewReport(jp, false);
    }


    @Override
    public boolean isItemNameExists(String itemName) throws SQLException,ClassNotFoundException  {
        ResultSet rs = CrudUtil.execute(
                "SELECT COUNT(*) FROM item WHERE item_name = ?",
                itemName
        );
        return rs.next() && rs.getInt(1) > 0;
    }

    @Override
    public boolean isItemNameExistsForOther(String itemName, int excludeItemId) throws SQLException,ClassNotFoundException  {
        ResultSet rs = CrudUtil.execute(
                "SELECT COUNT(*) FROM item WHERE item_name = ? AND item_id != ?",
                itemName,
                excludeItemId
        );
        return rs.next() && rs.getInt(1) > 0;
    }

    @Override
    public ArrayList<Itementity> getItemsByCategory(String category) throws SQLException,ClassNotFoundException  {
        ResultSet rs = CrudUtil.execute("SELECT * FROM item WHERE category = ?", category);

        ArrayList<Itementity> itemlist = new ArrayList<>();

        while (rs.next()) {

                    int id=rs.getInt("item_id");
                    String name=rs.getString("item_name");
                    String catagory=rs.getString("category");
                    double price=rs.getDouble("unit_price");
                    String image_path=rs.getString("image_path");
                    Itementity entity=new Itementity(id,name,catagory,price,image_path);
            itemlist.add(entity);
        }
        return itemlist;
    }

    @Override
    public ArrayList<String> getAllCategories() throws SQLException,ClassNotFoundException  {
        ResultSet rs = CrudUtil.execute(
                "SELECT DISTINCT category FROM item ORDER BY category"
        );

        ArrayList<String> categories = new ArrayList<>();
        while (rs.next()) {
            categories.add(rs.getString("category"));
        }
        return categories;
    }

    @Override
    public ArrayList<Itementity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM item");
        ArrayList<Itementity> itemlist = new ArrayList<>();

        while (rs.next()) {

                    int id=rs.getInt("item_id");
                    String name =rs.getString("item_name");
                    String catagory=rs.getString("category");
                    double price=rs.getDouble("unit_price");
                    String image_path=rs.getString("image_path");

            Itementity itementity = new Itementity(id,name,catagory,price,image_path);
        }
        return itemlist;
    }

    @Override
    public boolean save(Itementity entity) throws SQLException, ClassNotFoundException {
        boolean result = CrudUtil.execute(
                "INSERT INTO item(item_name, category, unit_price, image_path) VALUES (?, ?, ?, ?)",
                entity.getName(),
                entity.getCategory(),
                entity.getPrice(),
                entity.getImagePath()
        );
        return result;
    }

    @Override
    public boolean update(Itementity entity) throws SQLException, ClassNotFoundException {
        boolean result = CrudUtil.execute(
                "UPDATE item SET item_name=?, category=?, unit_price=?, image_path=? WHERE item_id=?",
                entity.getName(),
                entity.getCategory(),
                entity.getPrice(),
                entity.getImagePath(),
                entity.getId()
        );
        return result;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        boolean result = CrudUtil.execute(
                "DELETE FROM item WHERE item_id=?",
                id
        );
        return result;
    }

    @Override
    public Itementity search(int id) throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM item WHERE item_id=?",
                id
        );

        if (rs.next()) {
            return new Itementity(
                    rs.getInt("item_id"),
                    rs.getString("item_name"),
                    rs.getString("category"),
                    rs.getDouble("unit_price"),
                    rs.getString("image_path")
            );
        }
        return null;
    }


    @Override
    public int getnext() throws SQLException, ClassNotFoundException {
        String sql = "SELECT item_id FROM item ORDER BY item_id DESC LIMIT 1";
        ResultSet rs = CrudUtil.execute(sql);

        if (rs.next()) {
            return rs.getInt(1) + 1;
        } else {
            return 1;
        }
    }

    }

