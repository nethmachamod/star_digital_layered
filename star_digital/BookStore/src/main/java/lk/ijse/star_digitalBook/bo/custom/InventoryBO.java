package lk.ijse.star_digitalBook.bo.custom;

import lk.ijse.star_digitalBook.bo.SuperBO;
import lk.ijse.star_digitalBook.dao.CrudUtil;
import lk.ijse.star_digitalBook.db.DbConnection;
import lk.ijse.star_digitalBook.dto.inventoryDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface InventoryBO extends SuperBO {


     boolean saveInventory(inventoryDTO dto) throws SQLException, ClassNotFoundException  ;

     inventoryDTO searchInventory(String id) throws SQLException, ClassNotFoundException  ;


     boolean updateInventory(inventoryDTO dto) throws SQLException, ClassNotFoundException  ;

     boolean deleteInventory(String id) throws SQLException, ClassNotFoundException ;



     List<inventoryDTO> getAllInventory() throws SQLException, ClassNotFoundException ;

    int getnexteinventoryid() throws SQLException, ClassNotFoundException;
}
