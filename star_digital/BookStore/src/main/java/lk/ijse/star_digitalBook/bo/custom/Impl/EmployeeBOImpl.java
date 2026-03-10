package lk.ijse.star_digitalBook.bo.custom.Impl;

import lk.ijse.star_digitalBook.bo.custom.EmployeeBO;
import lk.ijse.star_digitalBook.dao.DAOFactory;
import lk.ijse.star_digitalBook.dao.custom.EmployeeDAO;
import lk.ijse.star_digitalBook.dto.employeeDTO;
import lk.ijse.star_digitalBook.entity.employeeentity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO empdao=(EmployeeDAO) DAOFactory.getInstance().getDAOType(DAOFactory.DAOType.EMPLOYEE);
    @Override
    public int getNextEmployeeId() throws SQLException,ClassNotFoundException {
        return empdao.getnext();
    }

    @Override
    public boolean saveemp(employeeDTO emp) throws SQLException,ClassNotFoundException {
        return empdao.save(new employeeentity(emp.getId(),emp.getFullName(),emp.getNic(),emp.getContact(),emp.getJobTitle(),emp.getSalary(),emp.getHireDate(),emp.getStatus(),emp.getImagePath()));

    }

    @Override
    public employeeDTO searchemp(int id) throws SQLException,ClassNotFoundException {
        employeeentity emp = empdao.search(id);
        return new employeeDTO(emp.getId(),emp.getFullName(),emp.getNic(),emp.getContact(),emp.getJobTitle(),emp.getSalary(),emp.getHireDate(),emp.getStatus(),emp.getImagePath());

    }

    @Override
    public boolean updateemp(employeeDTO emp) throws SQLException,ClassNotFoundException {
        return empdao.update(new employeeentity(emp.getId(),emp.getFullName(),emp.getNic(),emp.getContact(),emp.getJobTitle(),emp.getSalary(),emp.getHireDate(),emp.getStatus(),emp.getImagePath()));

    }

    @Override
    public boolean deleteemp(String id) throws SQLException,ClassNotFoundException {
        return empdao.delete(id);
    }

    @Override
    public ArrayList<employeeDTO> getEmployees() throws SQLException,ClassNotFoundException {
        ArrayList<employeeentity> entity = empdao.getAll();
        ArrayList<employeeDTO> emps = new ArrayList<>();
        for (employeeentity e : entity) {
            emps.add(new employeeDTO(e.getId(), e.getFullName(), e.getNic(), e.getContact(), e.getJobTitle(), e.getSalary(), e.getHireDate(), e.getStatus(), e.getImagePath()));
        }
        return emps;
    }

    @Override
    public boolean isNicExists(String nic) throws SQLException,ClassNotFoundException {
        return empdao.isNicExists(nic);
    }

    @Override
    public boolean isNicExistsForOther(String nic, int excludeEmpId) throws SQLException,ClassNotFoundException {
        return empdao.isNicExistsForOther(nic,excludeEmpId);
    }

    @Override
    public ArrayList<employeeDTO> getEmployeesByStatus(String status) throws SQLException,ClassNotFoundException {
        ArrayList<employeeentity> entity = empdao.getAll();
        ArrayList<employeeDTO> emps = new ArrayList<>();
        for (employeeentity e : entity) {
            emps.add(new employeeDTO(e.getId(), e.getFullName(), e.getNic(), e.getContact(), e.getJobTitle(), e.getSalary(), e.getHireDate(), e.getStatus(), e.getImagePath()));
        }
        return emps;
    }

    @Override
    public ArrayList<employeeDTO> getEmployeesByJobTitle(String jobTitle) throws SQLException,ClassNotFoundException {
        ArrayList<employeeentity> entity = empdao.getAll();
        ArrayList<employeeDTO> emps = new ArrayList<>();
        for (employeeentity e : entity) {
            emps.add(new employeeDTO(e.getId(), e.getFullName(), e.getNic(), e.getContact(), e.getJobTitle(), e.getSalary(), e.getHireDate(), e.getStatus(), e.getImagePath()));
        }
        return emps;
    }

    @Override
    public int getTotalEmployeeCount() throws SQLException,ClassNotFoundException {
        return empdao.getTotalEmployeeCount();
    }

    @Override
    public int getActiveEmployeeCount() throws SQLException,ClassNotFoundException {
        return empdao.getActiveEmployeeCount();
    }
}
