package lk.ijse.star_digitalBook.dao.custom;

import lk.ijse.star_digitalBook.dao.CrudDAO;
import lk.ijse.star_digitalBook.dto.employeeDTO;
import lk.ijse.star_digitalBook.entity.employeeentity;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeDAO extends CrudDAO<employeeentity> {
    boolean isNicExists(String nic) throws SQLException,ClassNotFoundException ;

    boolean isNicExistsForOther(String nic, int excludeEmpId) throws SQLException,ClassNotFoundException ;

    ArrayList<employeeentity> getEmployeesByStatus(String status) throws SQLException,ClassNotFoundException ;


    ArrayList<employeeentity> getEmployeesByJobTitle(String jobTitle) throws SQLException,ClassNotFoundException ;


    int getTotalEmployeeCount() throws SQLException,ClassNotFoundException ;


    int getActiveEmployeeCount() throws SQLException,ClassNotFoundException ;


}
