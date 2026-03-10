package lk.ijse.star_digitalBook.controller.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lk.ijse.star_digitalBook.dto.DailyChartDTO;
import lk.ijse.star_digitalBook.dao.CrudUtil;

public class SalesReportModel {


    public static List<DailyChartDTO> getDailyOrders() throws Exception {

        List<DailyChartDTO> list = new ArrayList<>();

        ResultSet rs = CrudUtil.execute(
            "SELECT DATE(order_date) AS day, COUNT(order_id) AS total " +
            "FROM orders GROUP BY DATE(order_date)"
        );

        while (rs.next()) {
            list.add(
                new DailyChartDTO(
                    rs.getString("day"),
                    rs.getInt("total")
                )
            );
        }
        return list;
    }

    
    public static List<DailyChartDTO> getDailyCustomers() throws Exception {

        List<DailyChartDTO> list = new ArrayList<>();

        ResultSet rs = CrudUtil.execute(
            "SELECT DATE(order_date) AS day, COUNT(DISTINCT customer_id) AS total " +
            "FROM orders GROUP BY DATE(order_date)"
        );

        while (rs.next()) {
            list.add(
                new DailyChartDTO(
                    rs.getString("day"),
                    rs.getInt("total")
                )
            );
        }
        return list;
    }
    
    public static double getTodayIncome() throws SQLException {
        ResultSet rs = CrudUtil.execute(
            "SELECT SUM(total) FROM orders WHERE DATE(order_date) = CURDATE()"
        );
        return rs.next() ? rs.getDouble(1) : 0.0;
    }

    public static int getTodayOrders() throws SQLException {
        ResultSet rs = CrudUtil.execute(
            "SELECT COUNT(*) FROM orders WHERE DATE(order_date) = CURDATE()"
        );
        return rs.next() ? rs.getInt(1) : 0;
    }

    public static double getTotalIncome() throws SQLException {
        ResultSet rs = CrudUtil.execute(
            "SELECT SUM(total) FROM orders"
        );
        return rs.next() ? rs.getDouble(1) : 0.0;
    }

    public static int getTodayCustomers() throws SQLException {
        ResultSet rs = CrudUtil.execute(
            "SELECT COUNT(DISTINCT customer_id) FROM orders WHERE DATE(order_date) = CURDATE()"
        );
        return rs.next() ? rs.getInt(1) : 0;
    }
}
