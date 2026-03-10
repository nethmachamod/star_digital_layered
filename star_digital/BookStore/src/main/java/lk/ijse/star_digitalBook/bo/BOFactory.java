/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.star_digitalBook.bo;

import lk.ijse.star_digitalBook.bo.custom.Impl.*;


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
        ITEM,
        INVENTORY,
        ORDER,
        SUPPLIER,
        ORDER_ITEM,
        PAYMENT,
        SALES_REPORT,
        USER

    }
    public SuperBO getBO(BOType botype){
        switch (botype){
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case ITEM:
                 return new ItemBOImpl();
            case INVENTORY:
                  return new InventoryBOImpl();
            case ORDER:
                   return new OrderBOImpl();
            case SUPPLIER:
                   return new SupplierBOImpl();
            case ORDER_ITEM:
                    return new OrderItemsBOImpl();
            case PAYMENT:
                     return new PaymentBOImpl();
            case SALES_REPORT:
                      return new SalesBOImpl();
            case USER:
                     return new UserBOImpl();

            default:
                return null;
        }
    }



}
