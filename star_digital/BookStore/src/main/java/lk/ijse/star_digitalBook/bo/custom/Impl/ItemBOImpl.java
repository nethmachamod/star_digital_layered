package lk.ijse.star_digitalBook.bo.custom.Impl;

import lk.ijse.star_digitalBook.bo.custom.ItemBO;
import lk.ijse.star_digitalBook.dao.DAOFactory;
import lk.ijse.star_digitalBook.dao.custom.ItemDAO;
import lk.ijse.star_digitalBook.dto.ItemDTO;
import lk.ijse.star_digitalBook.entity.Itementity;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {

    ItemDAO itmdao=(ItemDAO) DAOFactory.getInstance().getDAOType(DAOFactory.DAOType.ITEM);

    @Override
    public int getNextItemId() throws SQLException,ClassNotFoundException {
        return itmdao.getnext();
    }

    @Override
    public boolean saveItem(ItemDTO itemdto) throws SQLException,ClassNotFoundException  {
        return itmdao.save(new Itementity(itemdto.getId(),itemdto.getName(),itemdto.getCategory(),itemdto.getPrice(),itemdto.getImagePath()));
    }

    @Override
    public boolean updateItem(ItemDTO itemdto) throws SQLException,ClassNotFoundException  {
        return itmdao.update(new Itementity(itemdto.getId(),itemdto.getName(),itemdto.getCategory(),itemdto.getPrice(),itemdto.getImagePath()));
    }

    @Override
    public boolean deleteItem(String id) throws SQLException,ClassNotFoundException  {
        return itmdao.delete(id);
    }

    @Override
    public ItemDTO searchItem(int id) throws SQLException,ClassNotFoundException  {
        Itementity itementity=itmdao.search(id);
        return new ItemDTO(itementity.getId(),itementity.getName(),itementity.getCategory(),itementity.getPrice(),itementity.getImagePath());
    }

    @Override
    public ArrayList<ItemDTO> getItem() throws SQLException,ClassNotFoundException  {
        ArrayList<Itementity> entities=itmdao.getAll();
        ArrayList<ItemDTO> list=new ArrayList<>();
        for (Itementity itementity:entities){
            list.add(new ItemDTO(itementity.getId(),itementity.getName(),itementity.getCategory(),itementity.getPrice(),itementity.getImagePath()));
        }
        return list;
    }

    @Override
    public void printitemreport() throws Exception {
        itmdao.printitemreport();

    }

    @Override
    public boolean isItemNameExists(String itemName) throws SQLException,ClassNotFoundException  {
        return itmdao.isItemNameExists(itemName);
    }

    @Override
    public boolean isItemNameExistsForOther(String itemName, int excludeItemId) throws SQLException,ClassNotFoundException  {
        return itmdao.isItemNameExistsForOther(itemName,excludeItemId);
    }

    @Override
    public ArrayList<ItemDTO> getItemsByCategory(String category) throws SQLException,ClassNotFoundException  {
        ArrayList<Itementity> entities=itmdao.getAll();
        ArrayList<ItemDTO> list=new ArrayList<>();
        for (Itementity itementity:entities){
            list.add(new ItemDTO(itementity.getId(),itementity.getName(),itementity.getCategory(),itementity.getPrice(),itementity.getImagePath()));
        }
        return list;
    }

    @Override
    public ArrayList<ItemDTO> getAllCategories() throws SQLException,ClassNotFoundException  {
        ArrayList<Itementity> entities=itmdao.getAll();
        ArrayList<ItemDTO> list=new ArrayList<>();
        for (Itementity itementity:entities){
            list.add(new ItemDTO(itementity.getId(),itementity.getName(),itementity.getCategory(),itementity.getPrice(),itementity.getImagePath()));
        }
        return list;
    }
}
