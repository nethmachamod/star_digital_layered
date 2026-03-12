package lk.ijse.star_digitalBook.bo.custom.Impl;

import lk.ijse.star_digitalBook.bo.custom.OrderBO;
import lk.ijse.star_digitalBook.dao.DAOFactory;
import lk.ijse.star_digitalBook.dao.custom.OrderDAO;
import lk.ijse.star_digitalBook.dto.orderDTO;
import lk.ijse.star_digitalBook.entity.orderentity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBOImpl implements OrderBO {
    OrderDAO orderDAO =(OrderDAO) DAOFactory.getInstance().getDAOType(DAOFactory.DAOType.ORDER);

    @Override
    public boolean saveOrder(orderDTO order) throws SQLException,ClassNotFoundException {
        return orderDAO.save(new orderentity(order.getOrderid(),order.getOrderDate(),order.getOrdertype(),order.getOrdercontact(),order.getCustomerid(),order.getAmount(),order.getDiscount(),order.getTotal(),order.getOrderItems()));
    }

    @Override
    public orderDTO getOrder(String orderId) throws SQLException,ClassNotFoundException {
        orderentity order =orderDAO.search(Integer.parseInt(orderId));
        return new orderDTO(order.getOrderid(),order.getOrderDate(),order.getOrdertype(),order.getOrdercontact(),order.getCustomerid(),order.getAmount(),order.getDiscount(),order.getTotal(),order.getOrderItems());
    }

    @Override
    public ArrayList<orderDTO> getAllOrders() throws SQLException,ClassNotFoundException {
        ArrayList<orderentity> entity=orderDAO.getAll();
        ArrayList<orderDTO> list=new ArrayList<>();
        for (orderentity order:entity){
            list.add(new orderDTO(order.getOrderid(),order.getOrderDate(),order.getOrdertype(),order.getOrdercontact(),order.getCustomerid(),order.getAmount(),order.getDiscount(),order.getTotal(),order.getOrderItems()));
        }
        return list;
    }

    @Override
    public ArrayList<orderDTO> getOrdersByCustomerId(String customerId) throws SQLException,ClassNotFoundException {
        ArrayList<orderentity> entity=orderDAO.getOrdersByCustomerId(customerId);
        ArrayList<orderDTO> list=new ArrayList<>();
        for (orderentity order:entity){
            list.add(new orderDTO(order.getOrderid(),order.getOrderDate(),order.getOrdertype(),order.getOrdercontact(),order.getCustomerid(),order.getAmount(),order.getDiscount(),order.getTotal(),order.getOrderItems()));
        }
        return list;
    }

    @Override
    public boolean updateOrder(orderDTO order) throws SQLException,ClassNotFoundException {
        return orderDAO.update(new orderentity(order.getOrderid(),order.getOrderDate(),order.getOrdertype(),order.getOrdercontact(),order.getCustomerid(),order.getAmount(),order.getDiscount(),order.getTotal(),order.getOrderItems()));

    }

    @Override
    public boolean deleteOrder(String orderId) throws SQLException,ClassNotFoundException {
        return orderDAO.delete(orderId);
    }

    @Override
    public String getNextOrderId() throws SQLException,ClassNotFoundException {
        return String.valueOf(orderDAO.getnext());
    }

    @Override
    public ArrayList<orderDTO> getOrdersByType(String orderType) throws SQLException,ClassNotFoundException {
        ArrayList<orderentity> entity=orderDAO.getOrdersByType(orderType);
        ArrayList<orderDTO> list=new ArrayList<>();
        for (orderentity order:entity){
            list.add(new orderDTO(order.getOrderid(),order.getOrderDate(),order.getOrdertype(),order.getOrdercontact(),order.getCustomerid(),order.getAmount(),order.getDiscount(),order.getTotal(),order.getOrderItems()));
        }
        return list;
    }

    @Override
    public ArrayList<orderDTO> getOrdersByDateRange(String startDate, String endDate) throws SQLException,ClassNotFoundException {
        ArrayList<orderentity> entity=orderDAO.getOrdersByDateRange(startDate,endDate);
        ArrayList<orderDTO> list=new ArrayList<>();
        for (orderentity order:entity){
            list.add(new orderDTO(order.getOrderid(),order.getOrderDate(),order.getOrdertype(),order.getOrdercontact(),order.getCustomerid(),order.getAmount(),order.getDiscount(),order.getTotal(),order.getOrderItems()));
        }
        return list;
    }

    @Override
    public int getTotalOrderCount() throws SQLException,ClassNotFoundException {
        return orderDAO.getTotalOrderCount();
    }

    @Override
    public double getTotalRevenue() throws SQLException,ClassNotFoundException {
        return orderDAO.getTotalRevenue();
    }

    @Override
    public orderentity mapResultSetToOrder(ResultSet rs) throws SQLException {
        return orderDAO.mapResultSetToOrder(rs);
    }
}
