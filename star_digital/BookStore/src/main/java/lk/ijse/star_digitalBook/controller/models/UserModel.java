/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.star_digitalBook.controller.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import lk.ijse.star_digitalBook.dto.UserDto;
import lk.ijse.star_digitalBook.dao.CrudUtil;


public class UserModel{
    public  static boolean userDetails(UserDto userDto) throws SQLException {
        ResultSet rs =
                CrudUtil.execute(
                        "SELECT * FROM users WHERE user_name=? AND user_password=?",
                        userDto.getUserName(),
                        userDto.getPassword()
                );


        return rs.next();
    }
}
