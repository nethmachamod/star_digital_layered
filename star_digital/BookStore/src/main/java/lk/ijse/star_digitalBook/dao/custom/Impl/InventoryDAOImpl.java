package lk.ijse.star_digitalBook.dao.custom.Impl;

import lk.ijse.star_digitalBook.dao.CrudUtil;
import lk.ijse.star_digitalBook.dao.custom.InventoryDAO;
import lk.ijse.star_digitalBook.db.DbConnection;
import lk.ijse.star_digitalBook.dto.inventoryDTO;
import lk.ijse.star_digitalBook.entity.inventoryentity;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAOImpl implements InventoryDAO {
    @Override
    public ArrayList<inventoryentity> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<inventoryentity> list = new ArrayList<inventoryentity>();
        String sql = "SELECT * FROM inventory";
        Statement st = DbConnection.getInstance().getConnection().createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {

                    String id=rs.getString("inventory_id");
                    String name=rs.getString("inventory_name");
                    String hand=rs.getString("qty_on_hand");
                    String type=rs.getString("inventory_type");
                   LocalDate date= rs.getDate("stock_in_date") != null ? rs.getDate("stock_in_date").toLocalDate() : null;
                inventoryentity entity = new inventoryentity(id,name,hand,type,date);
                list.add(entity);
        }
        return list;
    }

    @Override
    public boolean save(inventoryentity entity) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO inventory VALUES (?,?,?,?,?)";
        PreparedStatement ptsm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ptsm.setString(1, entity.getInventoryId());
        ptsm.setString(2, entity.getInventoryName());
        ptsm.setString(3, entity.getQtyOnHand());
        ptsm.setString(4, entity.getInventoryType());
        ptsm.setDate(5, entity.getStockInDate() != null ? Date.valueOf(entity.getStockInDate()) : null);

        return ptsm.executeUpdate() > 0;
    }

    @Override
    public boolean update(inventoryentity entity) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE inventory SET inventory_name=?, qty_on_hand=?, inventory_type=?, stock_in_date=? WHERE inventory_id=?";
        PreparedStatement ptsm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ptsm.setString(1, entity.getInventoryName());
        ptsm.setString(2, entity.getQtyOnHand());
        ptsm.setString(3, entity.getInventoryType());
        ptsm.setDate(4, entity.getStockInDate() != null ? Date.valueOf(entity.getStockInDate()) : null);
        ptsm.setString(5, entity.getInventoryId());

        return ptsm.executeUpdate() > 0;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM inventory WHERE inventory_id=?";
        PreparedStatement ptsm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ptsm.setString(1, id);
        return ptsm.executeUpdate() > 0;
    }

    @Override
    public inventoryentity search(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM inventory WHERE inventory_id = ?";

        ResultSet rs = CrudUtil.execute(sql, id);

        if (rs.next()) {
            return new inventoryentity(
                    rs.getString("inventory_id"),
                    rs.getString("inventory_name"),
                    rs.getString("qty_on_hand"),
                    rs.getString("inventory_type"),
                    rs.getDate("stock_in_date").toLocalDate()
            );
        }
        return null;
    }

    @Override
    public int getnext() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute(
                "SELECT inventory_id FROM inventory ORDER BY inventory_id DESC LIMIT 1"
        );

        if (rs.next()) {
            String lastId = rs.getString(1);

            int num = Integer.parseInt(lastId.replace("INV", ""));
            return num + 1;
        }

        return 1;
    }
}
