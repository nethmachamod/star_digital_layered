package lk.ijse.star_digitalBook.dao.custom;

import lk.ijse.star_digitalBook.dao.CrudDAO;
import lk.ijse.star_digitalBook.dto.ItemDTO;
import lk.ijse.star_digitalBook.entity.Itementity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ItemDAO extends CrudDAO<Itementity> {

    void printitemreport() throws Exception ;


    boolean isItemNameExists(String itemName) throws SQLException,ClassNotFoundException ;


    boolean isItemNameExistsForOther(String itemName, int excludeItemId) throws SQLException,ClassNotFoundException  ;


    ArrayList<Itementity> getItemsByCategory(String category) throws SQLException,ClassNotFoundException  ;


    ArrayList<String> getAllCategories() throws SQLException,ClassNotFoundException  ;


}
