package lk.ijse.star_digitalBook.dao.custom.Impl;

import lk.ijse.star_digitalBook.dao.CrudUtil;
import lk.ijse.star_digitalBook.dao.custom.SalesDAO;
import lk.ijse.star_digitalBook.dto.DailyChartDTO;
import lk.ijse.star_digitalBook.entity.DailyChartentyti;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalesDAOImpl implements SalesDAO {
    @Override
    public ArrayList<DailyChartentyti> getDailyOrders() throws Exception {
        ArrayList<DailyChartentyti> list = new ArrayList<>();

        ResultSet rs = CrudUtil.execute(
                "SELECT DATE(order_date) AS day, COUNT(order_id) AS total " +
                        "FROM orders GROUP BY DATE(order_date)"
        );

        while (rs.next()) {
            list.add(
                    new DailyChartentyti(
                            rs.getString("day"),
                            rs.getInt("total")
                    )
            );
        }
        return list;
    }

    @Override
    public ArrayList<DailyChartentyti> getDailyCustomers() throws Exception {
        ArrayList<DailyChartentyti> list = new ArrayList<>();

        ResultSet rs = CrudUtil.execute(
                "SELECT DATE(order_date) AS day, COUNT(DISTINCT customer_id) AS total " +
                        "FROM orders GROUP BY DATE(order_date)"
        );

        while (rs.next()) {
            list.add(
                    new DailyChartentyti(
                            rs.getString("day"),
                            rs.getInt("total")
                    )
            );
        }
        return list;
    }

    @Override
    public double getTodayIncome() throws SQLException {
        ResultSet rs = CrudUtil.execute(
                "SELECT SUM(total) FROM orders WHERE DATE(order_date) = CURDATE()"
        );
        return rs.next() ? rs.getDouble(1) : 0.0;

    }

    @Override
    public int getTodayOrders() throws SQLException {
        ResultSet rs = CrudUtil.execute(
                "SELECT COUNT(*) FROM orders WHERE DATE(order_date) = CURDATE()"
        );
        return rs.next() ? rs.getInt(1) : 0;

    }

    @Override
    public double getTotalIncome() throws SQLException {
        ResultSet rs = CrudUtil.execute(
                "SELECT SUM(total) FROM orders"
        );
        return rs.next() ? rs.getDouble(1) : 0.0;

    }

    @Override
    public int getTodayCustomers() throws SQLException {
        ResultSet rs = CrudUtil.execute(
                "SELECT COUNT(DISTINCT customer_id) FROM orders WHERE DATE(order_date) = CURDATE()"
        );
        return rs.next() ? rs.getInt(1) : 0;

    }
}
