/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.star_digitalBook.dao;

import lk.ijse.star_digitalBook.bo.custom.Impl.*;
import lk.ijse.star_digitalBook.dao.custom.Impl.*;


public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {}
    public static DAOFactory getInstance(){
        return daoFactory==null?daoFactory=new DAOFactory():daoFactory;
    }
    public enum DAOType{
        EMPLOYEE,
        ITEM,
        INVENTORY,
        ORDER,
        SUPPLIER,
        ORDER_ITEM,
        PAYMENT,
        SALES_REPORT,
        USER

    }
    public SuperDAO getDAOType(DAOType daotype){
        switch (daotype){
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case INVENTORY:
                return new InventoryDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case ORDER_ITEM:
                return new OrderItemsDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case SALES_REPORT:
                return new SalesDAOImpl();
            case USER:
                return new UserDAOImpl();
            default:
                return null;
        }
    }

}
