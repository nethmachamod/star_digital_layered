//package lk.ijse.star_digitalBook.controller.models;
//
//import java.io.InputStream;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import lk.ijse.star_digitalBook.db.DbConnection;
//import lk.ijse.star_digitalBook.dto.ItemDTO;
//import lk.ijse.star_digitalBook.dao.CrudUtil;
//import net.sf.jasperreports.engine.JasperCompileManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.view.JasperViewer;
//
//public class ItemModel {
//
//
//    public static int getNextItemId() throws SQLException {
//        String sql = "SELECT item_id FROM item ORDER BY item_id DESC LIMIT 1";
//        ResultSet rs = CrudUtil.execute(sql);
//
//        if (rs.next()) {
//            return rs.getInt(1) + 1;
//        } else {
//            return 1;
//        }
//    }
//
//
//    public boolean saveItem(ItemDTO itemdto) throws SQLException {
//        boolean result = CrudUtil.execute(
//                "INSERT INTO item(item_name, category, unit_price, image_path) VALUES (?, ?, ?, ?)",
//                itemdto.getName(),
//                itemdto.getCategory(),
//                itemdto.getPrice(),
//                itemdto.getImagePath()
//        );
//        return result;
//    }
//
//
//    public boolean updateItem(ItemDTO itemdto) throws SQLException {
//        boolean result = CrudUtil.execute(
//                "UPDATE item SET item_name=?, category=?, unit_price=?, image_path=? WHERE item_id=?",
//                itemdto.getName(),
//                itemdto.getCategory(),
//                itemdto.getPrice(),
//                itemdto.getImagePath(),
//                itemdto.getId()
//        );
//        return result;
//    }
//
//
//    public boolean deleteItem(String id) throws SQLException {
//        boolean result = CrudUtil.execute(
//                "DELETE FROM item WHERE item_id=?",
//                id
//        );
//        return result;
//    }
//
//
//    public ItemDTO searchItem(int id) throws SQLException {
//        ResultSet rs = CrudUtil.execute(
//                "SELECT * FROM item WHERE item_id=?",
//                id
//        );
//
//        if (rs.next()) {
//            return new ItemDTO(
//                    rs.getInt("item_id"),
//                    rs.getString("item_name"),
//                    rs.getString("category"),
//                    rs.getDouble("unit_price"),
//                    rs.getString("image_path")
//            );
//        }
//        return null;
//    }
//
//
//    public List<ItemDTO> getItem() throws SQLException {
//        ResultSet rs = CrudUtil.execute("SELECT * FROM item");
//        List<ItemDTO> itemlist = new ArrayList<>();
//
//        while (rs.next()) {
//            ItemDTO itemDTO = new ItemDTO(
//                    rs.getInt("item_id"),
//                    rs.getString("item_name"),
//                    rs.getString("category"),
//                    rs.getDouble("unit_price"),
//                    rs.getString("image_path")
//            );
//            itemlist.add(itemDTO);
//        }
//        return itemlist;
//    }
//
//
//    public void printitemreport() throws Exception {
//        Connection conn = DbConnection.getInstance().getConnection();
//
//
//        InputStream inputstream = getClass().getResourceAsStream(
//                "/lk/ijse/star_digitalBook/reports/bill.jrxml"
//        );
//
//        if (inputstream == null) {
//            throw new Exception("Report template not found. Please ensure bill.jrxml exists in /resources/lk/ijse/star_digitalBook/reports/");
//        }
//
//        JasperReport jr = JasperCompileManager.compileReport(inputstream);
//        JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
//        JasperViewer.viewReport(jp, false);
//    }
//
//
//    public static boolean isItemNameExists(String itemName) throws SQLException {
//        ResultSet rs = CrudUtil.execute(
//                "SELECT COUNT(*) FROM item WHERE item_name = ?",
//                itemName
//        );
//        return rs.next() && rs.getInt(1) > 0;
//    }
//
//
//    public static boolean isItemNameExistsForOther(String itemName, int excludeItemId) throws SQLException {
//        ResultSet rs = CrudUtil.execute(
//                "SELECT COUNT(*) FROM item WHERE item_name = ? AND item_id != ?",
//                itemName,
//                excludeItemId
//        );
//        return rs.next() && rs.getInt(1) > 0;
//    }
//
//
//    public List<ItemDTO> getItemsByCategory(String category) throws SQLException {
//        ResultSet rs = CrudUtil.execute(
//                "SELECT * FROM item WHERE category = ?",
//                category
//        );
//
//        List<ItemDTO> itemlist = new ArrayList<>();
//
//        while (rs.next()) {
//            ItemDTO itemDTO = new ItemDTO(
//                    rs.getInt("item_id"),
//                    rs.getString("item_name"),
//                    rs.getString("category"),
//                    rs.getDouble("unit_price"),
//                    rs.getString("image_path")
//            );
//            itemlist.add(itemDTO);
//        }
//        return itemlist;
//    }
//
//
//    public static List<String> getAllCategories() throws SQLException {
//        ResultSet rs = CrudUtil.execute(
//                "SELECT DISTINCT category FROM item ORDER BY category"
//        );
//
//        List<String> categories = new ArrayList<>();
//        while (rs.next()) {
//            categories.add(rs.getString("category"));
//        }
//        return categories;
//    }
//}