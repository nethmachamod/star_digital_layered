package lk.ijse.star_digitalBook.bo.custom;

import lk.ijse.star_digitalBook.bo.SuperBO;
import lk.ijse.star_digitalBook.dto.UserDto;

import java.sql.SQLException;

public interface UserBO extends SuperBO {

    boolean userDetails(UserDto userDto) throws SQLException;
}
