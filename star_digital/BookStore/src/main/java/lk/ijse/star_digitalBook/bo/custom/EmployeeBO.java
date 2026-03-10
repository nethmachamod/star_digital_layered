package lk.ijse.star_digitalBook.bo.custom;

import lk.ijse.star_digitalBook.bo.SuperBO;
import lk.ijse.star_digitalBook.dao.CrudUtil;
import lk.ijse.star_digitalBook.dao.custom.EmployeeDAO;
import lk.ijse.star_digitalBook.dto.employeeDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface EmployeeBO extends SuperBO {
     int getNextEmployeeId() throws SQLException,ClassNotFoundException ;


     boolean saveemp(employeeDTO emp) throws SQLException,ClassNotFoundException ;


     employeeDTO searchemp(int id) throws SQLException,ClassNotFoundException ;


     boolean updateemp(employeeDTO emp) throws SQLException,ClassNotFoundException ;


     boolean deleteemp(String id) throws SQLException,ClassNotFoundException ;


     ArrayList<employeeDTO> getEmployees() throws SQLException,ClassNotFoundException ;


     boolean isNicExists(String nic) throws SQLException,ClassNotFoundException ;

     boolean isNicExistsForOther(String nic, int excludeEmpId) throws SQLException,ClassNotFoundException ;

     ArrayList<employeeDTO> getEmployeesByStatus(String status) throws SQLException,ClassNotFoundException ;


     ArrayList<employeeDTO> getEmployeesByJobTitle(String jobTitle) throws SQLException,ClassNotFoundException ;


     int getTotalEmployeeCount() throws SQLException,ClassNotFoundException ;


     int getActiveEmployeeCount() throws SQLException,ClassNotFoundException ;

}
