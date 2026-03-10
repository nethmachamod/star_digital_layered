package lk.ijse.star_digitalBook.controller.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import lk.ijse.star_digitalBook.db.DbConnection;
import lk.ijse.star_digitalBook.dto.inventoryDTO;
import lk.ijse.star_digitalBook.dao.CrudUtil;

public class InventoryModel {
       

    public static String getNextInventoryId() throws SQLException {

        ResultSet rs = CrudUtil.execute(
            "SELECT inventory_id FROM inventory ORDER BY inventory_id DESC LIMIT 1"
        );

        if (rs.next()) {
            String lastId = rs.getString(1); 

            int num = Integer.parseInt(lastId.replace("INV", ""));
            num++;

            return String.format("INV%03d", num);
        }

        return "INV001";
    }



    public static boolean saveInventory(inventoryDTO dto) throws SQLException {
        String sql = "INSERT INTO inventory VALUES (?,?,?,?,?)";
        PreparedStatement ptsm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ptsm.setString(1, dto.getInventoryId());
        ptsm.setString(2, dto.getInventoryName());
        ptsm.setString(3, dto.getQtyOnHand());
        ptsm.setString(4, dto.getInventoryType());
        ptsm.setDate(5, dto.getStockInDate() != null ? Date.valueOf(dto.getStockInDate()) : null);

        return ptsm.executeUpdate() > 0;
    }
    
    public static inventoryDTO searchInventory(String id) throws SQLException {

    String sql = "SELECT * FROM inventory WHERE inventory_id = ?";

    ResultSet rs = CrudUtil.execute(sql, id);

    if (rs.next()) {
        return new inventoryDTO(
            rs.getString("inventory_id"),
            rs.getString("inventory_name"),
            rs.getString("qty_on_hand"),
            rs.getString("inventory_type"),
            rs.getDate("stock_in_date").toLocalDate()
        );
    }
    return null;
}


    public static boolean updateInventory(inventoryDTO dto) throws SQLException {
        String sql = "UPDATE inventory SET inventory_name=?, qty_on_hand=?, inventory_type=?, stock_in_date=? WHERE inventory_id=?";
        PreparedStatement ptsm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ptsm.setString(1, dto.getInventoryName());
        ptsm.setString(2, dto.getQtyOnHand());
        ptsm.setString(3, dto.getInventoryType());
        ptsm.setDate(4, dto.getStockInDate() != null ? Date.valueOf(dto.getStockInDate()) : null);
        ptsm.setString(5, dto.getInventoryId());

        return ptsm.executeUpdate() > 0;
    }

    public static boolean deleteInventory(String id) throws SQLException {
        String sql = "DELETE FROM inventory WHERE inventory_id=?";
        PreparedStatement ptsm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ptsm.setString(1, id);
        return ptsm.executeUpdate() > 0;
    }

    

    public List<inventoryDTO> getAllInventory() throws SQLException {
        List<inventoryDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM inventory";
        Statement st = DbConnection.getInstance().getConnection().createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            list.add(new inventoryDTO(
                rs.getString("inventory_id"),
                rs.getString("inventory_name"),
                rs.getString("qty_on_hand"),
                rs.getString("inventory_type"),
                rs.getDate("stock_in_date") != null ? rs.getDate("stock_in_date").toLocalDate() : null
            ));
        }
        return list;
    }
}
