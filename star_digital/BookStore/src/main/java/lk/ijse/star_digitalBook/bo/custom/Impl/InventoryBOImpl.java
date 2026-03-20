package lk.ijse.star_digitalBook.bo.custom.Impl;

import lk.ijse.star_digitalBook.bo.custom.InventoryBO;
import lk.ijse.star_digitalBook.dao.DAOFactory;
import lk.ijse.star_digitalBook.dao.custom.Impl.InventoryDAOImpl;
import lk.ijse.star_digitalBook.dao.custom.InventoryDAO;
import lk.ijse.star_digitalBook.dto.employeeDTO;
import lk.ijse.star_digitalBook.dto.inventoryDTO;
import lk.ijse.star_digitalBook.entity.employeeentity;
import lk.ijse.star_digitalBook.entity.inventoryentity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryBOImpl implements InventoryBO {

    InventoryDAO inventoryDAO=(InventoryDAO) DAOFactory.getInstance().getDAOType(DAOFactory.DAOType.INVENTORY);




    @Override
    public boolean saveInventory(inventoryDTO dto) throws SQLException, ClassNotFoundException {
        return inventoryDAO.save(new inventoryentity(dto.getInventoryId(),dto.getInventoryName(),dto.getQtyOnHand(),dto.getInventoryType(),dto.getStockInDate()));
    }

    @Override
    public inventoryDTO searchInventory(String id) throws SQLException, ClassNotFoundException {
        inventoryentity intity =inventoryDAO.search(Integer.parseInt(id));
        return new inventoryDTO(intity.getInventoryId(),intity.getInventoryName(),intity.getQtyOnHand(),intity.getInventoryType(),intity.getStockInDate());
    }

    @Override
    public boolean updateInventory(inventoryDTO dto) throws SQLException, ClassNotFoundException {
        return inventoryDAO.update(new inventoryentity(dto.getInventoryId(),dto.getInventoryName(),dto.getQtyOnHand(),dto.getInventoryType(),dto.getStockInDate()));
    }

    @Override
    public boolean deleteInventory(String id) throws SQLException, ClassNotFoundException {
        return inventoryDAO.delete(id);
    }

    @Override
    public ArrayList<inventoryDTO> getAllInventory() throws SQLException, ClassNotFoundException {
        ArrayList<inventoryentity> entity = inventoryDAO.getAll();
        ArrayList<inventoryDTO> dto = new ArrayList<>();
        for (inventoryentity entity1 : entity) {
            dto.add(new inventoryDTO(entity1.getInventoryId(), entity1.getInventoryName(), entity1.getQtyOnHand(), entity1.getInventoryType(), entity1.getStockInDate()));
        }
        return dto;
    }

    @Override
    public int getnexteinventoryid()throws SQLException, ClassNotFoundException {
        return inventoryDAO.getnext();
    }


}
