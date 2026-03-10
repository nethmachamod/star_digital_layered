/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.star_digitalBook.bo;

import lk.ijse.star_digitalBook.bo.custom.Impl.EmployeeBOImpl;
import lk.ijse.star_digitalBook.bo.custom.Impl.ItemBOImpl;


/**
 *
 * @author Dell
 */
public class BOFactory {
    private static BOFactory instance;
    private BOFactory() {}
    public static BOFactory getInstance(){
        return instance==null? instance=new BOFactory():instance;
    }
    public enum BOType{
        EMPLOYEE,
        ITEM

    }
    public SuperBO getBO(BOType botype){
        switch (botype){
            case EMPLOYEE:
                return new EmployeeBOImpl();
                case ITEM:
                    return new ItemBOImpl();

            default:
                return null;
        }
    }



}
