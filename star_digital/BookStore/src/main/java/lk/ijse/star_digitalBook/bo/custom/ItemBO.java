package lk.ijse.star_digitalBook.bo.custom;

import lk.ijse.star_digitalBook.bo.SuperBO;
import lk.ijse.star_digitalBook.dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ItemBO extends SuperBO {
    int getNextItemId() throws SQLException,ClassNotFoundException  ;


     boolean saveItem(ItemDTO itemdto) throws SQLException,ClassNotFoundException  ;


     boolean updateItem(ItemDTO itemdto) throws SQLException,ClassNotFoundException  ;


     boolean deleteItem(String id) throws SQLException,ClassNotFoundException  ;


     ItemDTO searchItem(int id) throws SQLException,ClassNotFoundException  ;


     List<ItemDTO> getItem() throws SQLException,ClassNotFoundException  ;


     void printitemreport() throws Exception  ;


     boolean isItemNameExists(String itemName) throws SQLException,ClassNotFoundException  ;


     boolean isItemNameExistsForOther(String itemName, int excludeItemId) throws SQLException,ClassNotFoundException  ;


     ArrayList<ItemDTO> getItemsByCategory(String category) throws SQLException,ClassNotFoundException  ;


     ArrayList<ItemDTO> getAllCategories() throws SQLException,ClassNotFoundException  ;


}
