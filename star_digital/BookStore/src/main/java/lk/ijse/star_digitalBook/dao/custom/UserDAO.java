package lk.ijse.star_digitalBook.dao.custom;

import lk.ijse.star_digitalBook.dao.CrudDAO;
import lk.ijse.star_digitalBook.dao.CrudUtil;
import lk.ijse.star_digitalBook.dao.SuperDAO;
import lk.ijse.star_digitalBook.dto.UserDto;
import lk.ijse.star_digitalBook.entity.Userentity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserDAO extends SuperDAO {
     boolean userDetails(UserDto userDto) throws SQLException ;


}
