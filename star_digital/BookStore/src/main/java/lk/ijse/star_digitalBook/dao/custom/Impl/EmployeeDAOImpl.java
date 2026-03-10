package lk.ijse.star_digitalBook.dao.custom.Impl;

import lk.ijse.star_digitalBook.dao.CrudUtil;
import lk.ijse.star_digitalBook.dao.custom.EmployeeDAO;
import lk.ijse.star_digitalBook.entity.employeeentity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public ArrayList<employeeentity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM employee ORDER BY emp_id DESC");
        ArrayList<employeeentity> employees = new ArrayList<>();

        while (rs.next()) {

            int id=rs.getInt("emp_id");
            String name= rs.getString("full_name");
            String nic=rs.getString("nic");
            String contact=rs.getString("contact_no");
            String job_title=rs.getString("job_title");
            double salary=rs.getDouble("salary");
            LocalDate hire_date=rs.getDate("hire_date").toLocalDate();
            String status =rs.getString("status");
            String image_path =rs.getString("image_path");
            employeeentity entity =new employeeentity(id,name,nic,contact,job_title,salary,hire_date,status,image_path);
            employees.add(entity);
        }
        return employees;
    }

    @Override
    public boolean save(employeeentity entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO employee(full_name, nic, contact_no, job_title, salary, hire_date, status, image_path) VALUES (?,?,?,?,?,?,?,?)",
                entity.getFullName(),
                entity.getNic(),
                entity.getContact(),
                entity.getJobTitle(),
                entity.getSalary(),
                java.sql.Date.valueOf(entity.getHireDate()),
                entity.getStatus(),
                entity.getImagePath()
        );
    }

    @Override
    public boolean update(employeeentity entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE employee SET full_name=?, nic=?, contact_no=?, job_title=?, salary=?, hire_date=?, status=?, image_path=? WHERE emp_id=?",
                entity.getFullName(),
                entity.getNic(),
                entity.getContact(),
                entity.getJobTitle(),
                entity.getSalary(),
                java.sql.Date.valueOf(entity.getHireDate()),
                entity.getStatus(),
                entity.getImagePath(),
                entity.getId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM employee WHERE emp_id=?", id);

    }

    @Override
    public employeeentity search(int id) throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM employee WHERE emp_id=?", id);

        if (rs.next()) {
            return new employeeentity(
                    rs.getInt("emp_id"),
                    rs.getString("full_name"),
                    rs.getString("nic"),
                    rs.getString("contact_no"),
                    rs.getString("job_title"),
                    rs.getDouble("salary"),
                    rs.getDate("hire_date").toLocalDate(),
                    rs.getString("status"),
                    rs.getString("image_path")
            );
        }

        return null;
    }

    @Override
    public int getnext() throws SQLException, ClassNotFoundException {
        String sql = "SELECT emp_id FROM employee ORDER BY emp_id DESC LIMIT 1";
        ResultSet rs = CrudUtil.execute(sql);

        if (rs.next()) {
            return rs.getInt(1) + 1;
        } else {
            return 1;
        }
    }

    @Override
    public boolean isNicExists(String nic) throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute(
                "SELECT COUNT(*) FROM employee WHERE nic = ?", nic
        );
        return rs.next() && rs.getInt(1) > 0;
    }

    @Override
    public boolean isNicExistsForOther(String nic, int excludeEmpId) throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute(
                "SELECT COUNT(*) FROM employee WHERE nic = ? AND emp_id != ?",
                nic, excludeEmpId
        );
        return rs.next() && rs.getInt(1) > 0;
    }

    @Override
    public ArrayList<employeeentity> getEmployeesByStatus(String status) throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM employee WHERE status = ? ORDER BY emp_id DESC",
                status
        );
        ArrayList<employeeentity> employees = new ArrayList<>();

        while (rs.next()) {

            int id=rs.getInt("emp_id");
            String name= rs.getString("full_name");
            String nic=rs.getString("nic");
            String contact=rs.getString("contact_no");
            String job_title=rs.getString("job_title");
            double salary=rs.getDouble("salary");
            LocalDate hire_date=rs.getDate("hire_date").toLocalDate();
            String stats =rs.getString("status");
            String image_path =rs.getString("image_path");
            employeeentity entity =new employeeentity(id,name,nic,contact,job_title,salary,hire_date,status,image_path);
            employees.add(entity);
        }
        return employees;
    }

    @Override
    public ArrayList<employeeentity> getEmployeesByJobTitle(String jobTitle) throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM employee WHERE job_title = ? ORDER BY emp_id DESC",
                jobTitle
        );
        ArrayList<employeeentity> employees = new ArrayList<>();

        while (rs.next()) {
            int id=rs.getInt("emp_id");
            String name= rs.getString("full_name");
            String nic=rs.getString("nic");
            String contact=rs.getString("contact_no");
            String job_title=rs.getString("job_title");
            double salary=rs.getDouble("salary");
            LocalDate hire_date=rs.getDate("hire_date").toLocalDate();
            String status =rs.getString("status");
            String image_path =rs.getString("image_path");
            employeeentity entity =new employeeentity(id,name,nic,contact,job_title,salary,hire_date,status,image_path);
            employees.add(entity);
        }
        return employees;
    }

    @Override
    public int getTotalEmployeeCount() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT COUNT(*) FROM employee");
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    @Override
    public int getActiveEmployeeCount() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute(
                "SELECT COUNT(*) FROM employee WHERE status = 'Active'"
        );
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }
}
