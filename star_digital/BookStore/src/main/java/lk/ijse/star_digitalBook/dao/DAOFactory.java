/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.star_digitalBook.dao;

import lk.ijse.star_digitalBook.dao.custom.Impl.EmployeeDAOImpl;
import lk.ijse.star_digitalBook.dao.custom.Impl.ItemDAOImpl;


public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {}
    public static DAOFactory getInstance(){
        return daoFactory==null?daoFactory=new DAOFactory():daoFactory;
    }
    public enum DAOType{
        EMPLOYEE,
        ITEM

    }
    public SuperDAO getDAOType(DAOType daotype){
        switch (daotype){
            case EMPLOYEE:
                return new EmployeeDAOImpl();
                case ITEM:
                    return new ItemDAOImpl();

            default:
                return null;
        }
    }

}
