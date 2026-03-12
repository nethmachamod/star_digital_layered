package lk.ijse.star_digitalBook.bo.custom.Impl;

import lk.ijse.star_digitalBook.bo.custom.PaymentBO;
import lk.ijse.star_digitalBook.bo.custom.SalesBO;
import lk.ijse.star_digitalBook.dao.DAOFactory;
import lk.ijse.star_digitalBook.dao.custom.OrderItemsDAO;
import lk.ijse.star_digitalBook.dao.custom.SalesDAO;
import lk.ijse.star_digitalBook.entity.DailyChartentyti;

import java.sql.SQLException;
import java.util.ArrayList;

public class SalesBOImpl implements SalesBO {

    SalesDAO salesDAO =(SalesDAO) DAOFactory.getInstance().getDAOType(DAOFactory.DAOType.SALES_REPORT);

    @Override
    public ArrayList<DailyChartentyti> getDailyOrders() throws Exception {

        ArrayList<DailyChartentyti> entity=salesDAO.getDailyOrders();
        ArrayList<DailyChartentyti> List=new ArrayList<>();
        for(DailyChartentyti dailyChartentyti:entity){
            List.add(new DailyChartentyti(dailyChartentyti.getDate(),dailyChartentyti.getValue()));
        }
        return List;
    }

    @Override
    public ArrayList<DailyChartentyti> getDailyCustomers() throws Exception {
        ArrayList<DailyChartentyti> entity=salesDAO.getDailyCustomers();
        ArrayList<DailyChartentyti> List=new ArrayList<>();
        for(DailyChartentyti dailyChartentyti:entity){
            List.add(new DailyChartentyti(dailyChartentyti.getDate(),dailyChartentyti.getValue()));
        }
        return List;
    }

    @Override
    public double getTodayIncome() throws SQLException {
        return salesDAO.getTodayIncome();
    }

    @Override
    public int getTodayOrders() throws SQLException {
        return salesDAO.getTodayOrders();
    }

    @Override
    public double getTotalIncome() throws SQLException {
        return salesDAO.getTotalIncome();
    }

    @Override
    public int getTodayCustomers() throws SQLException {
        return salesDAO.getTodayCustomers();
    }
}
