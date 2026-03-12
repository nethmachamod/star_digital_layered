package lk.ijse.star_digitalBook.dao.custom.Impl;

import lk.ijse.star_digitalBook.dao.CrudUtil;
import lk.ijse.star_digitalBook.dao.custom.UserDAO;
import lk.ijse.star_digitalBook.dto.UserDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {
    @Override
    public boolean userDetails(UserDto userDto) throws SQLException {
        ResultSet rs =
                CrudUtil.execute(
                        "SELECT * FROM users WHERE user_name=? AND user_password=?",
                        userDto.getUserName(),
                        userDto.getPassword()
                );


        return rs.next();
    }
}
