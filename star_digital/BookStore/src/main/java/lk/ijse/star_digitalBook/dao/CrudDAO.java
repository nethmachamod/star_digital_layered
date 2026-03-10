/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package lk.ijse.star_digitalBook.dao;

import lk.ijse.star_digitalBook.entity.employeeentity;

import java.sql.SQLException;
import java.util.ArrayList;


public interface CrudDAO<T> extends SuperDAO {
    ArrayList<T> getAll()throws SQLException,ClassNotFoundException;
    boolean save(T entity)throws SQLException,ClassNotFoundException;
    boolean update(T entity)throws SQLException,ClassNotFoundException;
    boolean delete(String id)throws SQLException,ClassNotFoundException;
    T search(int id)throws SQLException,ClassNotFoundException;
    int getnext()throws SQLException,ClassNotFoundException;

}
