//package lk.ijse.star_digitalBook.controller.models;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import lk.ijse.star_digitalBook.dto.employeeDTO;
//import lk.ijse.star_digitalBook.dao.CrudUtil;
//
//public class EmployeeModel {
//
//
//    public static int getNextEmployeeId() throws SQLException {
//        String sql = "SELECT emp_id FROM employee ORDER BY emp_id DESC LIMIT 1";
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
//    public static boolean saveemp(employeeDTO emp) throws SQLException {
//        return CrudUtil.execute(
//                "INSERT INTO employee(full_name, nic, contact_no, job_title, salary, hire_date, status, image_path) VALUES (?,?,?,?,?,?,?,?)",
//                emp.getFullName(),
//                emp.getNic(),
//                emp.getContact(),
//                emp.getJobTitle(),
//                emp.getSalary(),
//                java.sql.Date.valueOf(emp.getHireDate()),
//                emp.getStatus(),
//                emp.getImagePath()
//        );
//    }
//
//
//    public static employeeDTO searchemp(int id) throws SQLException {
//        ResultSet rs = CrudUtil.execute("SELECT * FROM employee WHERE emp_id=?", id);
//
//        if (rs.next()) {
//            return new employeeDTO(
//                    rs.getInt("emp_id"),
//                    rs.getString("full_name"),
//                    rs.getString("nic"),
//                    rs.getString("contact_no"),
//                    rs.getString("job_title"),
//                    rs.getDouble("salary"),
//                    rs.getDate("hire_date").toLocalDate(),
//                    rs.getString("status"),
//                    rs.getString("image_path")
//            );
//        }
//
//        return null;
//    }
//
//
//    public static boolean updateemp(employeeDTO emp) throws SQLException {
//        return CrudUtil.execute(
//                "UPDATE employee SET full_name=?, nic=?, contact_no=?, job_title=?, salary=?, hire_date=?, status=?, image_path=? WHERE emp_id=?",
//                emp.getFullName(),
//                emp.getNic(),
//                emp.getContact(),
//                emp.getJobTitle(),
//                emp.getSalary(),
//                java.sql.Date.valueOf(emp.getHireDate()),
//                emp.getStatus(),
//                emp.getImagePath(),
//                emp.getId()
//        );
//    }
//
//
//    public static boolean deleteemp(String id) throws SQLException {
//        return CrudUtil.execute("DELETE FROM employee WHERE emp_id=?", id);
//    }
//
//
//    public List<employeeDTO> getEmployees() throws SQLException {
//        ResultSet rs = CrudUtil.execute("SELECT * FROM employee ORDER BY emp_id DESC");
//        List<employeeDTO> employeelist = new ArrayList<>();
//
//        while (rs.next()) {
//            employeeDTO empdto = new employeeDTO(
//                    rs.getInt("emp_id"),
//                    rs.getString("full_name"),
//                    rs.getString("nic"),
//                    rs.getString("contact_no"),
//                    rs.getString("job_title"),
//                    rs.getDouble("salary"),
//                    rs.getDate("hire_date").toLocalDate(),
//                    rs.getString("status"),
//                    rs.getString("image_path")
//            );
//            employeelist.add(empdto);
//        }
//        return employeelist;
//    }
//
//
//    public static boolean isNicExists(String nic) throws SQLException {
//        ResultSet rs = CrudUtil.execute(
//                "SELECT COUNT(*) FROM employee WHERE nic = ?", nic
//        );
//        return rs.next() && rs.getInt(1) > 0;
//    }
//
//
//    public static boolean isNicExistsForOther(String nic, int excludeEmpId) throws SQLException {
//        ResultSet rs = CrudUtil.execute(
//                "SELECT COUNT(*) FROM employee WHERE nic = ? AND emp_id != ?",
//                nic, excludeEmpId
//        );
//        return rs.next() && rs.getInt(1) > 0;
//    }
//
//    public static List<employeeDTO> getEmployeesByStatus(String status) throws SQLException {
//        ResultSet rs = CrudUtil.execute(
//                "SELECT * FROM employee WHERE status = ? ORDER BY emp_id DESC",
//                status
//        );
//        List<employeeDTO> employeelist = new ArrayList<>();
//
//        while (rs.next()) {
//            employeeDTO empdto = new employeeDTO(
//                    rs.getInt("emp_id"),
//                    rs.getString("full_name"),
//                    rs.getString("nic"),
//                    rs.getString("contact_no"),
//                    rs.getString("job_title"),
//                    rs.getDouble("salary"),
//                    rs.getDate("hire_date").toLocalDate(),
//                    rs.getString("status"),
//                    rs.getString("image_path")
//            );
//            employeelist.add(empdto);
//        }
//        return employeelist;
//    }
//
//
//    public static List<employeeDTO> getEmployeesByJobTitle(String jobTitle) throws SQLException {
//        ResultSet rs = CrudUtil.execute(
//                "SELECT * FROM employee WHERE job_title = ? ORDER BY emp_id DESC",
//                jobTitle
//        );
//        List<employeeDTO> employeelist = new ArrayList<>();
//
//        while (rs.next()) {
//            employeeDTO empdto = new employeeDTO(
//                    rs.getInt("emp_id"),
//                    rs.getString("full_name"),
//                    rs.getString("nic"),
//                    rs.getString("contact_no"),
//                    rs.getString("job_title"),
//                    rs.getDouble("salary"),
//                    rs.getDate("hire_date").toLocalDate(),
//                    rs.getString("status"),
//                    rs.getString("image_path")
//            );
//            employeelist.add(empdto);
//        }
//        return employeelist;
//    }
//
//
//    public static int getTotalEmployeeCount() throws SQLException {
//        ResultSet rs = CrudUtil.execute("SELECT COUNT(*) FROM employee");
//        if (rs.next()) {
//            return rs.getInt(1);
//        }
//        return 0;
//    }
//
//
//    public static int getActiveEmployeeCount() throws SQLException {
//        ResultSet rs = CrudUtil.execute(
//                "SELECT COUNT(*) FROM employee WHERE status = 'Active'"
//        );
//        if (rs.next()) {
//            return rs.getInt(1);
//        }
//        return 0;
//    }
//}