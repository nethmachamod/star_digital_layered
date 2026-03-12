package lk.ijse.star_digitalBook.bo.custom.Impl;

import lk.ijse.star_digitalBook.bo.custom.OrderItemsBO;
import lk.ijse.star_digitalBook.dao.DAOFactory;
import lk.ijse.star_digitalBook.dao.custom.OrderDAO;
import lk.ijse.star_digitalBook.dao.custom.OrderItemsDAO;
import lk.ijse.star_digitalBook.dto.OrderItemDTO;
import lk.ijse.star_digitalBook.dto.orderDTO;
import lk.ijse.star_digitalBook.entity.OrderItementity;
import lk.ijse.star_digitalBook.entity.orderentity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderItemsBOImpl implements OrderItemsBO {

    OrderItemsDAO orderItemsDAO =(OrderItemsDAO) DAOFactory.getInstance().getDAOType(DAOFactory.DAOType.ORDER_ITEM);

    @Override
    public boolean saveOrderItem(OrderItemDTO orderItem) throws SQLException,ClassNotFoundException {
        return orderItemsDAO.save(new OrderItementity(orderItem.getOrderItemId(),orderItem.getOrderId(),orderItem.getItemId(),orderItem.getItemName(),orderItem.getQuantity(),orderItem.getUnitPrice(),orderItem.getLineTotal()));
    }

    @Override
    public ArrayList<OrderItemDTO> getOrderItemsByOrderId(String orderId) throws SQLException,ClassNotFoundException {
        ArrayList<OrderItementity> entity=orderItemsDAO.getOrderItemsByOrderId(orderId);
        ArrayList<OrderItemDTO> list=new ArrayList<>();
        for (OrderItementity orderItem:entity){
            list.add(new OrderItemDTO(orderItem.getOrderItemId(),orderItem.getOrderId(),orderItem.getItemId(),orderItem.getItemName(),orderItem.getQuantity(),orderItem.getUnitPrice(),orderItem.getLineTotal()));
        }
        return list;
    }

    @Override
    public OrderItemDTO getOrderItem(int orderItemId) throws SQLException,ClassNotFoundException {
        OrderItementity orderItem =orderItemsDAO.search(orderItemId);
        return new OrderItemDTO(orderItem.getOrderItemId(),orderItem.getOrderId(),orderItem.getItemId(),orderItem.getItemName(),orderItem.getQuantity(),orderItem.getUnitPrice(),orderItem.getLineTotal());

    }

    @Override
    public boolean deleteOrderItems(String orderId) throws SQLException,ClassNotFoundException {
        return orderItemsDAO.delete(orderId);
    }

    @Override
    public OrderItementity mapResultSetToOrderItem(ResultSet rs) throws SQLException,ClassNotFoundException {
        return orderItemsDAO.mapResultSetToOrderItem(rs);
    }
}
